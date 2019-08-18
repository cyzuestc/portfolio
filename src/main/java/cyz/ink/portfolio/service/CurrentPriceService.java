package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.CurrentPriceDAO;
import cyz.ink.portfolio.dao.InstrumentDAO;
import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.Instrument;
import cyz.ink.portfolio.pojo.InstrumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CurrentPriceService {
    @Autowired
    InstrumentService instrumentService;
    @Autowired
    CurrentPriceDAO currentPriceDAO;


    public float getPrice(int instrumentId) {
        return currentPriceDAO.getOne(instrumentId).getPrice();
    }

    public void add(CurrentPrice historyPrice) {
        currentPriceDAO.save(historyPrice);
    }
}
