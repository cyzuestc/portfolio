package cyz.ink.portfolio.service;

import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.FundManager;
import cyz.ink.portfolio.pojo.Hold;
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
public class InstrumentsValueService {
    @Autowired
    FundManagerService fundManagerService;

    public void calculateInstrumentsValue() {
        List<FundManager> fundManagerList = fundManagerService.listAll();
        for (int i = 0; i < fundManagerList.size(); i++) {
            FundManager fundManager = fundManagerList.get(i);
            float totalInstrumentsValue = 0f;
            Set<Hold> holdSet = fundManager.getHode();
            for (Hold hold : holdSet) {
                //currentPrice 只有一条current price
                CurrentPrice currentPrice = (CurrentPrice) hold.getInstrument().getCurrentPrice().toArray()[0];
                //将股票所有价值加起来
                totalInstrumentsValue += currentPrice.getPrice() * hold.getVolume();
            }
            fundManager.setInstrumentsValue(totalInstrumentsValue);
            fundManager.setProfit(fundManager.getInstrumentsValue() + fundManager.getBalance());
        }


    }
}
