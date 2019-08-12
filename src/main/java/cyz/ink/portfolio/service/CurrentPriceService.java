package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.CurrentPriceDAO;
import cyz.ink.portfolio.dao.InstrumentDAO;
import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.Instrument;
import cyz.ink.portfolio.pojo.InstrumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrentPriceService {
    @Autowired
    InstrumentDAO instrumentDAO;
    @Autowired
    CurrentPriceDAO currentPriceDAO;

    public List<CurrentPrice> list(InstrumentType instrumentType){
        List<Instrument> list = instrumentDAO.findAllByType(instrumentType);
        List<CurrentPrice> currentPrices = new ArrayList<>();
        for (Instrument i : list){
            currentPrices.add(currentPriceDAO.getOne(i.getId()));
        }
        return currentPrices;
    }
}
