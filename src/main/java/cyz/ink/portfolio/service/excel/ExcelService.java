package cyz.ink.portfolio.service.excel;

import cyz.ink.portfolio.dao.HistoryPriceDAO;
import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.InstrumentType;
import cyz.ink.portfolio.service.CurrentPriceService;
import cyz.ink.portfolio.service.InstrumentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cyz.ink.portfolio.pojo.Instrument;
import cyz.ink.portfolio.pojo.HistoryPrice;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;
import java.util.Date;

@Service
@Slf4j
public class ExcelService {

    private static Logger logger = LoggerFactory.getLogger(ExcelService.class);
    @Autowired
    HistoryPriceDAO historyPriceDAO;
    @Autowired
    CurrentPriceService currentPriceService;
    @Autowired
    InstrumentService instrumentService;

    private static ExcelDao excelDao = new ExcelDaoImpl();

    public List addDataFromExcel(File file, InstrumentType instrumentType) {
        log.warn(file.isFile() + "");
        String fileName = file.getName();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        String prefix1 = fileName.substring(fileName.indexOf("-"));
        int num = prefix.length();
        int num1 = prefix1.length();
        String symbol = fileName.substring(0, fileName.length() - num1);
        int num2 = symbol.length();
        //获取到文件名
        String name = fileName.substring(num2 + 1, fileName.length() - num);
        //根据文件名,symbol,type找到instrument
        Instrument instrument = excelDao.newInstrument(symbol, name, instrumentType);

        logger.warn(symbol);
        logger.warn(name);

        //symbol是唯一判断依据
        if (instrumentService.findInstrumentBySymbol(symbol) == null) {
            instrumentService.save(instrument);
        }

        //获取在表中的Instrument Id
        instrument = instrumentService.findInstrumentBySymbol(symbol);

        //提取所有的HistoryPrice
        List<HistoryPrice> listExcel1 = excelDao.getAllHistoryPriceByExcel(file, instrument);


        for (int i = 0; i < listExcel1.size(); i++) {
            HistoryPrice historyPrice = listExcel1.get(i);
            Date date = historyPrice.getDate();
            if (historyPriceDAO.findHistoryPriceByInstrumentAndDate(instrument, date) == null) {
                historyPriceDAO.save(historyPrice);
            }
            //最新一天的价格
            if (i == listExcel1.size() - 1) {
                CurrentPrice currentPrice = historyPrice.getInstrument().getCurrentPrice();
                if (currentPrice == null) currentPrice = new CurrentPrice();

                currentPrice.setPrice(historyPrice.getPrice());
                currentPrice.setDate(historyPrice.getDate());
                currentPrice.setLow(historyPrice.getLow());
                currentPrice.setHigh(historyPrice.getHigh());
                currentPrice.setClose(historyPrice.getLow());
                currentPrice.setOpen(historyPrice.getOpen());
                currentPrice.setChanged(historyPrice.getChanged());
//                currentPrice.setInstrument(instrument);
                currentPriceService.add(currentPrice);

                instrument.setCurrentPrice(currentPrice);
                instrumentService.save(instrument);
            }
        }
        return listExcel1;
    }


}
