package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.CurrentPriceDAO;
import cyz.ink.portfolio.dao.InstrumentDAO;
import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.Instrument;
import cyz.ink.portfolio.pojo.InstrumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrentPriceService {
    @Autowired
    InstrumentDAO instrumentDAO;
    @Autowired
    CurrentPriceDAO currentPriceDAO;

    public Page<CurrentPrice> list(InstrumentType instrumentType,int start,int size){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Instrument> instrumentPage = instrumentDAO.findAllByType(instrumentType,pageable);
        List<CurrentPrice> currentPrices = new ArrayList<>();
        instrumentPage.forEach(instrument -> currentPrices.add(currentPriceDAO.getOne(instrument.getId())));
        Page<CurrentPrice> currentPricePage = new PageImpl<>(currentPrices,pageable,currentPrices.size());
        return currentPricePage;
    }


}
