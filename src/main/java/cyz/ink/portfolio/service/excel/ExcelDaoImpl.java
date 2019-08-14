package cyz.ink.portfolio.service.excel;

import cyz.ink.portfolio.pojo.HistoryPrice;
import cyz.ink.portfolio.pojo.Instrument;

import java.text.SimpleDateFormat;

import cyz.ink.portfolio.pojo.InstrumentType;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelDaoImpl implements ExcelDao {
    @Override
    public Instrument getAllInstrumentByExcel(String symbol, String name, InstrumentType instrumentType) {
        Instrument instrument = new Instrument();
        instrument.setName(name);
        instrument.setSymbol(symbol);
        instrument.setType(instrumentType);
        return instrument;
    }


    @Override
    public List<HistoryPrice> getAllHistoryPriceByExcel(File file, int instrumentId) {
        List<HistoryPrice> list = new ArrayList<HistoryPrice>();
        try {
            Workbook rwb = Workbook.getWorkbook(file);
            Sheet rs = rwb.getSheet(0);//或者rwb.getSheet(0)

            for (int i = 1; i < rs.getRows(); i++) {
                Cell[] row = rs.getRow(i);
                HistoryPrice historyPrice = new HistoryPrice();
                historyPrice.setInstrumentId(instrumentId);
                historyPrice.setHigh(Float.parseFloat(row[2].getContents()));
                historyPrice.setLow(Float.parseFloat(row[3].getContents()));
                historyPrice.setOpen(Float.parseFloat(row[1].getContents()));
                historyPrice.setClose(Float.parseFloat(row[4].getContents()));
                historyPrice.setChanged(1.11f);
                historyPrice.setPrice(Float.parseFloat(row[5].getContents()));
                SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd");
                Date d = s.parse(row[0].getContents());
                java.sql.Date date = new java.sql.Date(d.getTime());
                historyPrice.setDate(date);
                list.add(historyPrice);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }


}
