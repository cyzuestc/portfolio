package cyz.ink.portfolio.service.excel;
import cyz.ink.portfolio.pojo.HistoryPrice;
import cyz.ink.portfolio.pojo.Instrument;
import java.util.Date;

import java.util.List;

public interface ExcelDao {
    public Instrument getAllInstrumentByExcel(String symbol,String name);

    public List<HistoryPrice> getAllHistoryPriceByExcel(String file,int instrumentId);


}
