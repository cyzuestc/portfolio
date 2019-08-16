package cyz.ink.portfolio.service.excel;

import cyz.ink.portfolio.dao.InstrumentDAO;
import cyz.ink.portfolio.dao.HistoryPriceDAO;
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
public class ExcelService {

    private static Logger logger = LoggerFactory.getLogger(ExcelService.class);
    @Autowired
    InstrumentDAO instrumentDAO;
    @Autowired
    HistoryPriceDAO historyPriceDAO;

    private static ExcelDao excelDao = new ExcelDaoImpl();

    public List addDataFromExcel() {
       // String symbol = "QLD";
        // String name="aaa";
       String file = "/Users/Zhangxian/Desktop/project/Portfolio/src/main/resources/excel/Futures/CCIZX-Columbia Seligman Communications and Information Fund Institutional Class.xls";
       File f=new File(file);
        String fileName=f.getName();
        String prefix=fileName.substring(fileName.lastIndexOf("."));
        String prefix1=fileName.substring(fileName.indexOf("-"));
        int num=prefix.length();
        int num1=prefix1.length();
        String symbol=fileName.substring(0, fileName.length()-num1);
        int num2=symbol.length();
        String name=fileName.substring(num2+1,fileName.length()-num);
        Instrument instrument= excelDao.getAllInstrumentByExcel(symbol, name);

        logger.warn(symbol);
        logger.warn(name);

            symbol = instrument.getSymbol();
            if (instrumentDAO.findInstrumentBySymbol(symbol) == null) {
                instrumentDAO.save(instrument);
            }
            int instrumentId = instrumentDAO.findInstrumentBySymbol(symbol).getId();
            List<HistoryPrice> listExcel1 = excelDao.getAllHistoryPriceByExcel(file, instrumentId);
            for (HistoryPrice historyPrice : listExcel1) {
                Date date = historyPrice.getDate();
                if (historyPriceDAO.findHistoryPriceByInstrumentIdAndDate(instrumentId, date) == null) {
                    historyPriceDAO.save(historyPrice);
                }
            }
        return listExcel1;
    }





}
