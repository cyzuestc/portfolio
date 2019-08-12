package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.InstrumentDAO;
import cyz.ink.portfolio.pojo.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Page<Instrument> getInstruments(int start, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Instrument> pageFromJPA = instrumentDAO.findAll(pageable);
        return pageFromJPA;
    }

}
