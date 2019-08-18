package cyz.ink.portfolio.pojo;

import cyz.ink.portfolio.dao.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * @ Author      : Zink
 * @ Date        : Created in 18:05 2019/8/9
 * @ Description : Add data for test
 * @ Version     : 1.0
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddData {

    private static final Logger logger= LoggerFactory.getLogger(AddData.class);

    @Autowired
    AdminDAO adminDAO;
    @Autowired
    FundManagerDAO fundManagerDAO;
    @Autowired
    InstrumentDAO instrumentDAO;
    @Autowired
    HoldDAO holdDAO;
    @Autowired
    CurrentPriceDAO currentPriceDAO;
    @Autowired
    HistoryPriceDAO historyPriceDAO;
    @Autowired
    TradingHistoryDAO tradingHistoryDAO;

    @Test
    public void addData(){
        /**
         * add admin
         */
        adminDAO.deleteAll();
        for (int i = 1; i < 100; i++) {
            Admin admin = new Admin();
            admin.setId(i);
            admin.setName(String.format("admin%d",i));
            admin.setPassword(String.format("Password%d",i));
            adminDAO.save(admin);
        }
        Assert.assertEquals(adminDAO.findAll().size(),99);

        /**
         * add fund manager
         */
        for (int i = 1; i < 100; i++) {
            FundManager fundManager = new FundManager();
            fundManager.setId(i);
            fundManager.setName(String.format("fundManagerName%d",i));
            fundManager.setPassword(String.format("password%d",i));
            fundManager.setBalance(10000.00f);
            fundManagerDAO.save(fundManager);
        }
        Assert.assertEquals(fundManagerDAO.findAll().size() , 99);

        /**
         * add instrument
         */

        for (int i = 1; i < 100; i++) {
            Instrument instrument = new Instrument();
            instrument.setId(i);
            instrument.setSymbol(String.format("Symbol%d",i));
            instrument.setName(String.format("name%d",i));
            if (i%4==1)instrument.setType(InstrumentType.Bonds);
            if (i%4==2)instrument.setType(InstrumentType.ETFs);
            if (i%4==3)instrument.setType(InstrumentType.Futures);
            if (i%4==0)instrument.setType(InstrumentType.Stocks);
            instrumentDAO.save(instrument);
        }
        Assert.assertEquals(instrumentDAO.findAll().size(),99);

        /**
         * add hold
         */
        for (int i = 1; i < 100; i++) {
            Hold hold = new Hold();
            hold.setId(i);
//            hold.setFundManagerId(i);
            hold.setVolume(i);
            holdDAO.save(hold);
        }

        Assert.assertEquals(holdDAO.findAll().size(),99);

        /**
         * add currentPrice
         */
        for (int i = 1; i < 100; i++) {
            CurrentPrice currentPrice = new CurrentPrice();

            float price = (float) (new Random().nextInt(5)*i);

            currentPrice.setId(i);
            currentPrice.setChanged(0.05f);
            currentPrice.setOpen(price);
            currentPrice.setClose(price);
            currentPrice.setHigh(price);
            currentPrice.setLow(price);
            currentPrice.setPrice(price);

            currentPrice.setDate(new Date());
            logger.warn(currentPrice.getDate().toString());
            currentPriceDAO.save(currentPrice);
        }
        Assert.assertEquals(currentPriceDAO.findAll().size(),99);

        /**
         * add History
         */
        for (int i = 1; i < 100; i++) {
            for (int j = 1; j < 100; j++) {
                HistoryPrice historyPrice = new HistoryPrice();

                float price = (float) (new Random().nextInt(200)*i);

                historyPrice.setChanged(0.05f);
                historyPrice.setOpen(price);
                historyPrice.setClose(price);
                historyPrice.setHigh(price);
                historyPrice.setLow(price);

                historyPrice.setDate(new Date());
                historyPriceDAO.save(historyPrice);
            }
        }

        Assert.assertEquals(historyPriceDAO.findAll().size(),99*99);

        /**
         * add tradingHistory
         */

        for (int i = 1; i < 100; i++) {
            TradingHistory tradingHistory = new TradingHistory();
            //从当前时间-1天
            tradingHistory.setDate(new Date(1565351427-i*3600));

            float price = (float) (new Random().nextInt(200)*i);
            tradingHistory.setPrice(price);
            tradingHistory.setVolume(i*10);
        }
        Assert.assertEquals(tradingHistoryDAO.findAll().size(),99);
    }
}
