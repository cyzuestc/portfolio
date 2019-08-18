package cyz.ink.portfolio.web;

import cyz.ink.portfolio.dao.*;
import cyz.ink.portfolio.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author      : Zink
 * @ Date        : Created in 18:56 2019/8/18
 * @ Description :
 * @ Version     : 1.0
 **/
@RestController
public class DataTestController {
    @Autowired
    CurrentPriceDAO currentPriceDAO;
    @Autowired
    FundManagerDAO fundManagerDAO;
    @Autowired
    HistoryPriceDAO historyPriceDAO;
    @Autowired
    HoldDAO holdDAO;
    @Autowired
    InstrumentDAO instrumentDAO;
    @Autowired
    TradingHistoryDAO tradingHistoryDAO;

    @GetMapping("/add1")
    public void add1() {
        for (int i = 0; i < 10; i++) {
//        增加Instrument
            Instrument instrument = new Instrument();
            instrument.setName("ins" + i);
//        增加CurrentPrice
            CurrentPrice currentPrice = new CurrentPrice();
            currentPrice.setPrice(0f);
            instrument.setCurrentPrice(currentPrice);
            instrumentDAO.save(instrument);
//        增加Fundmanager
            FundManager fundManager = new FundManager();
            fundManager.setName("fun" + i);
//        增加Hold
            Hold hold = new Hold();
            hold.setInstrument(instrument);
            hold.setFundManager(fundManager);

            holdDAO.save(hold);

//        增加HistoryPrice
            HistoryPrice historyPrice = new HistoryPrice();
            historyPrice.setInstrument(instrument);
            historyPriceDAO.save(historyPrice);
//        增加TradingHistory
            TradingHistory tradingHistory = new TradingHistory();
            tradingHistory.setFundManager(fundManager);
            tradingHistory.setInstrument(instrument);
            tradingHistoryDAO.save(tradingHistory);
        }
    }

    @GetMapping("/getth")
    public TradingHistory getth() {
        return tradingHistoryDAO.getOne(1);
    }

    @GetMapping("/getHold")
    public Hold getHold() {
        return holdDAO.getOne(1);
    }

    @GetMapping("/changeCurrentPrice")
    public CurrentPrice changeCurrentPrice() {
        CurrentPrice currentPrice = currentPriceDAO.getOne(1);
        currentPrice.setPrice(100);
        currentPriceDAO.save(currentPrice);
        return currentPriceDAO.getOne(1);
    }

    @GetMapping("/deleteHold")
    public void deleteHold(@RequestParam("id") int id) {
        Hold hold = holdDAO.getOne(id);
        holdDAO.delete(hold);
    }
}
