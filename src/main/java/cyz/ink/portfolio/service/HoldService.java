package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.HoldDAO;
import cyz.ink.portfolio.pojo.FundManager;
import cyz.ink.portfolio.pojo.Hold;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @ Author      : Zink
 * @ Date        : Created in 19:40 2019/8/13
 * @ Description :
 * @ Version     : 1.0
 **/
@Service
@Slf4j
public class HoldService {
    @Autowired
    HoldDAO holdDAO;
    @Autowired
    FundManagerService fundManagerService;
    @Autowired
    CurrentPriceService currentPriceService;
    @Autowired
    TradingHistoryService tradingHistoryService;

    public int sell(int instrumentId, int volume, FundManager fundManager) {
        Hold hold = holdDAO.getHoldByInstrumentIdAndFundManagerId(instrumentId, fundManager.getId());
        //没有买,就没法卖
        if (hold == null) return -1;
        //不能卖超
        if (hold.getVolume() < volume) return -2;


        log.info("-------卖出股票-------");

        //balance处理,卖出后增加balance
        log.info("售出前volume: " + hold.getVolume());
        hold.setVolume(hold.getVolume() - volume);
        log.info("售出后volume: " + hold.getVolume());
        holdDAO.save(hold);


        float price = currentPriceService.getPrice(instrumentId);
        log.info("price:", price);
        fundManager.setBalance(fundManager.getBalance() + price * volume);
        fundManager.setInstrumentsValue(fundManager.getInstrumentsValue() - price * volume);
        fundManager.setProfit(fundManager.getInstrumentsValue() + fundManager.getBalance());

        fundManagerService.save(fundManager);
        //卖光了,就删除hold记录
//        if (hold.getVolume() == 0) holdDAO.delete(hold);
        //更改hold
//        else {
//        }

        //tradingHistory处理
        tradingHistoryService.add(instrumentId, -1 * volume, price, fundManager);
        return 1;
    }

    //增加hold,购买
    public int add(int instrumentId, int volume, float price, FundManager fundManager) {
        Hold hold = holdDAO.getHoldByInstrumentIdAndFundManagerId(instrumentId, fundManager.getId());
        log.info("购买前数量: " + hold.getVolume());
        if (hold != null) {
            hold.setVolume(hold.getVolume() + volume);
        } else {
            hold = new Hold();
            hold.setVolume(volume);
            hold.setFundManagerId(fundManager.getId());
            hold.setInstrumentId(instrumentId);
        }
        holdDAO.save(hold);
        log.info("购买后数量: " + hold.getVolume());

        fundManager.setBalance(fundManager.getBalance() - volume * price);
        fundManager.setInstrumentsValue(fundManager.getInstrumentsValue() + volume * price);
        fundManagerService.save(fundManager);

        //增加操作历史
        tradingHistoryService.add(instrumentId, volume, price, fundManager);
        return 1;
    }

    //选出hold数据
    public Page<Hold> list(FundManager fundManager, int start, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        return holdDAO.findAllByFundManagerId(fundManager.getId(), pageable);
    }


}
