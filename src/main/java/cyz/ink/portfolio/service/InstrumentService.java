package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.InstrumentDAO;
import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.Instrument;
import cyz.ink.portfolio.pojo.InstrumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author      : Zink
 * @ Date        : Created in 10:24 2019/8/12
 * @ Description :
 * @ Version     : 1.0
 **/
@Service
public class InstrumentService {
    @Autowired
    InstrumentDAO instrumentDAO;

    /**
     * getInstrumentByType(Commodities、Equities,etc)
     * @param type
     * @return
     */
    public Page<Instrument> getInstrumentByType(InstrumentType type,int start, int size){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
        Page<Instrument> pageFromJPA = instrumentDAO.findAllByType(type,pageable);
        return pageFromJPA;
    }
    /**
     * Paging
     * @param start
     * @param size
     * @return
     */
    public Page<Instrument> getInstruments(int start, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Instrument> pageFromJPA = instrumentDAO.findAll(pageable);
        return pageFromJPA;
    }

    public Page<CurrentPrice> findAllByType(InstrumentType type,int start, int size){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(start,size,sort);
//        Page<CurrentPrice> curpage = instrumentDAO.findAllByType(type,pageable);
//        return curpage;
        return null;
    }

    public Instrument getInstrumentById(int id) {
        return instrumentDAO.getOne(id);
    }
}
