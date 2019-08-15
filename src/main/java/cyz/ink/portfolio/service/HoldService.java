package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.HoldDAO;
import cyz.ink.portfolio.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    InstrumentService instrumentService;

    public int sell(int instrumentId, int volume, FundManager fundManager) {
        Hold hold = holdDAO.getHoldByInstrumentIdAndFundManagerId(instrumentId, fundManager.getId());
        //没有买,就没法卖
        if (hold == null) return -1;
        //不能卖超
        if (hold.getVolume() < volume) return -2;


        log.info("-------卖出股票-------" + "售出前volume: " + hold.getVolume());
        //balance处理,卖出后增加balance
        hold.setVolume(hold.getVolume() - volume);
        //卖光了,就删除hold记录
        if (hold.getVolume() == 0) {
            holdDAO.delete(hold);
//        更改hold
        } else {
            holdDAO.save(hold);
        }

        log.info("售出后volume: " + holdDAO.getHoldByInstrumentIdAndFundManagerId(instrumentId, fundManager.getId()));

        float price = currentPriceService.getPrice(instrumentId);
        log.info("instrumentId: " + "price: ", price);
        fundManager.setBalance(fundManager.getBalance() + price * volume);

        log.info("insValue before: " + fundManager.getInstrumentsValue());

        fundManager.setInstrumentsValue(fundManager.getInstrumentsValue() - price * volume);

        log.info("insValue after: " + fundManager.getInstrumentsValue());

        fundManager.setProfit(fundManager.getInstrumentsValue() + fundManager.getBalance());

        fundManagerService.save(fundManager);


        //tradingHistory处理
        tradingHistoryService.add(instrumentId, -1 * volume, price, fundManager);
        return 1;
    }

    //增加hold,购买
    public int add(int instrumentId, int volume, float price, FundManager fundManager) {
        Hold hold = holdDAO.getHoldByInstrumentIdAndFundManagerId(instrumentId, fundManager.getId());
        log.info("hold: " + hold);
        if (hold != null) {
            hold.setVolume(hold.getVolume() + volume);
        } else {
            hold = new Hold();
            hold.setVolume(volume);
            hold.setFundManagerId(fundManager.getId());
            hold.setInstrumentId(instrumentId);
        }
        holdDAO.save(hold);

        log.info("购买后hold: " + hold);

        fundManager.setBalance(fundManager.getBalance() - volume * price);
        fundManager.setInstrumentsValue(fundManager.getInstrumentsValue() + volume * price);
        fundManagerService.save(fundManager);

        //增加操作历史
        tradingHistoryService.add(instrumentId, volume, price, fundManager);
        return 1;
    }

    //选出hold数据,并选出对应的currentPrice和instrument,共同放进HoldAndInstrumentBean中
    public Page<HoldAndInstrumentBean> list(FundManager fundManager, int start, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Hold> holdPage = holdDAO.findAllByFundManagerId(fundManager.getId(), pageable);
        List<Hold> holds = holdPage.getContent();
        List<HoldAndInstrumentBean> holdAndInstrumentBeans = new ArrayList<>();
        for (Hold hold : holds) {
            //获取相应的属性
            int instrumentId = hold.getInstrumentId();
            CurrentPrice currentPrice = currentPriceService.getByInstrumentId(instrumentId);
            Instrument instrument = instrumentService.getInstrumentById(instrumentId);
//            创建对象,并添加到list中
            HoldAndInstrumentBean holdAndInstrumentBean = new HoldAndInstrumentBean();
            holdAndInstrumentBean.setCurrentPrice(currentPrice);
            holdAndInstrumentBean.setHold(hold);
            holdAndInstrumentBean.setInstrument(instrument);
            holdAndInstrumentBean.setTotalPages(holdPage.getTotalPages());
            holdAndInstrumentBeans.add(holdAndInstrumentBean);
        }
        Page<HoldAndInstrumentBean> holdAndInstrumentBeanPage = new PageImpl<>(holdAndInstrumentBeans, pageable, holdAndInstrumentBeans.size());
        return holdAndInstrumentBeanPage;
    }

    public List<Hold> listAll(int fundManagerId) {
        return holdDAO.findAllByFundManagerId(fundManagerId);
    }


}
