package cyz.ink.portfolio.service.excel;

import cyz.ink.portfolio.pojo.HistoryPrice;
import cyz.ink.portfolio.pojo.Instrument;
import cyz.ink.portfolio.pojo.InstrumentType;

import java.io.File;
import java.util.Date;

import java.util.List;

public interface ExcelDao {
    public Instrument getAllInstrumentByExcel(String symbol, String name, InstrumentType instrumentType);

    public List<HistoryPrice> getAllHistoryPriceByExcel(File file, int instrumentId);


}
