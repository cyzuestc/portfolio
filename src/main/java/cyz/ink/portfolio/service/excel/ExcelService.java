package cyz.ink.portfolio.service.excel;

import cyz.ink.portfolio.dao.InstrumentDAO;
import cyz.ink.portfolio.dao.HistoryPriceDAO;
import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.InstrumentType;
import cyz.ink.portfolio.service.CurrentPriceService;
import cyz.ink.portfolio.service.InstrumentsValueService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cyz.ink.portfolio.pojo.Instrument;
import cyz.ink.portfolio.pojo.HistoryPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Date;

@Service
@Slf4j
public class ExcelService {

    private static Logger logger = LoggerFactory.getLogger(ExcelService.class);
    @Autowired
    InstrumentDAO instrumentDAO;
    @Autowired
    HistoryPriceDAO historyPriceDAO;
    @Autowired
    CurrentPriceService currentPriceService;
    @Autowired
    InstrumentsValueService instrumentsValueService;

    private static ExcelDao excelDao = new ExcelDaoImpl();

    public int addDataFromExcel(File file, InstrumentType instrumentType) {
        log.warn(file.isFile() + "");
        String fileName = file.getName();
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        String prefix1 = fileName.substring(fileName.indexOf("-"));
        int num = prefix.length();
        int num1 = prefix1.length();
        String symbol = fileName.substring(0, fileName.length() - num1);
        int num2 = symbol.length();
        String name = fileName.substring(num2 + 1, fileName.length() - num);
        Instrument instrument = excelDao.getAllInstrumentByExcel(symbol, name, instrumentType);

        logger.warn(symbol);
        logger.warn(name);

        symbol = instrument.getSymbol();
        if (instrumentDAO.findInstrumentBySymbol(symbol) == null) {
            instrumentDAO.save(instrument);
        }
        int instrumentId = instrumentDAO.findInstrumentBySymbol(symbol).getId();
        List<HistoryPrice> listExcel1 = excelDao.getAllHistoryPriceByExcel(file, instrumentId);
        for (int i = 0; i < listExcel1.size(); i++) {
            HistoryPrice historyPrice = listExcel1.get(i);
            Date date = historyPrice.getDate();
            if (historyPriceDAO.findHistoryPriceByInstrumentIdAndDate(instrumentId, date) == null) {
                historyPriceDAO.save(historyPrice);
            }
            //最新一天的价格
            if (i == listExcel1.size() - 1) {
                CurrentPrice currentPrice = currentPriceService.getByInstrumentId(historyPrice.getInstrumentId());
                if (currentPrice == null) currentPrice = new CurrentPrice();

                currentPrice.setPrice(historyPrice.getPrice());
                currentPrice.setDate(historyPrice.getDate());
                currentPrice.setLow(historyPrice.getLow());
                currentPrice.setHigh(historyPrice.getHigh());
                currentPrice.setClose(historyPrice.getLow());
                currentPrice.setInstrumentId(historyPrice.getInstrumentId());
                currentPrice.setOpen(historyPrice.getOpen());
                currentPrice.setChanged(historyPrice.getChanged());

                currentPriceService.add(currentPrice);
            }
        }
        //update instrumentsValue
        instrumentsValueService.calculateInstrumentsValue();
        //上传成功
        return 1;
    }


}
