package cyz.ink.portfolio.service;

import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.FundManager;
import cyz.ink.portfolio.pojo.Hold;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @ Author      : Zink
 * @ Date        : Created in 11:29 2019/8/15
 * @ Description : Calculate all the fund managers' instrumentsValueService
 * @ Version     : 1.0
 **/
@Service
@Slf4j
public class InstrumentsValueService {
    @Autowired
    FundManagerService fundManagerService;
    @Autowired
    CurrentPriceService currentPriceService;
    @Autowired
    HoldService holdService;


    public void calculateInstrumentsValue() {
        List<FundManager> fundManagerList = fundManagerService.listAll();
        for (int i = 0; i < fundManagerList.size(); i++) {
            FundManager fundManager = fundManagerList.get(i);
            float totalInstrumentsValue = 0f;
//            Set<Hold> holdSet = fundManager.getHold();
            List<Hold> holdList = holdService.listAll(fundManager.getId());
            for (Hold hold : holdList) {
                //currentPrice 只有一条current price
                int instrumentId = hold.getInstrumentId();
                float currentPrice = currentPriceService.getPrice(instrumentId);
                //将股票所有价值加起来
                totalInstrumentsValue += currentPrice * hold.getVolume();
            }
            fundManager.setInstrumentsValue(totalInstrumentsValue);
            fundManager.setProfit(fundManager.getInstrumentsValue() + fundManager.getBalance());
            fundManagerService.save(fundManager);
        }
    }

    public void testCalculateInstrumentsValue(int value) {
        //每个currentPrice都加value
        List<CurrentPrice> currentPrices = currentPriceService.getAll();
        for (int i = 0; i < currentPrices.size(); i++) {
            CurrentPrice currentPrice = currentPrices.get(i);
            currentPrice.setPrice(currentPrice.getPrice() + value);
            currentPriceService.add(currentPrice);
        }

        //所有fundManager
        List<FundManager> fundManagerList = fundManagerService.listAll();

        log.info(fundManagerList.toString());
        //计算每一个fundManager的所有hold的总价值,并更新到instrumentsValue
        for (int i = 0; i < fundManagerList.size(); i++) {
            FundManager fundManager = fundManagerList.get(i);
            float totalInstrumentsValue = 0f;
            List<Hold> holdList = holdService.listAll(fundManager.getId());
            for (Hold hold : holdList) {
                //currentPrice 只有一条current price
                int instrumentId = hold.getInstrumentId();
                float currentPrice = currentPriceService.getPrice(instrumentId) + 100;
                //将股票所有价值加起来
                totalInstrumentsValue += currentPrice * hold.getVolume();
            }
            log.info("before: " + fundManager.getName() + fundManager.getInstrumentsValue() + " and it will become :" + totalInstrumentsValue);

            fundManager.setInstrumentsValue(totalInstrumentsValue);
            log.info("after: " + fundManager.getName() + fundManager.getInstrumentsValue());

            fundManager.setProfit(fundManager.getInstrumentsValue() + fundManager.getBalance());
            fundManagerService.save(fundManager);
        }
    }
}
