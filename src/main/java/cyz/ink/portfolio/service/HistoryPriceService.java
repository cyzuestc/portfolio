package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.HistoryPriceDAO;
import cyz.ink.portfolio.dao.InstrumentDAO;
import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.HistoryPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HistoryPriceService {

    @Autowired
    InstrumentDAO instrumentDAO;
    @Autowired
    HistoryPriceDAO historyPriceDAO;

    public List<HistoryPrice> listByInstrumentId(int instrumentId, int start, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        List<HistoryPrice> instrumentPage = historyPriceDAO.findAllByInstrumentId(instrumentId);
        log.info("" + instrumentPage.size());
        return instrumentPage;
    }

    public List<HistoryPrice> list() {
        List<HistoryPrice> instrumentPage = historyPriceDAO.findAll();
        log.info("" + instrumentPage.size());
        return instrumentPage;
    }
}
