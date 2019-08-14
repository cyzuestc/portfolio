package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.TradingHistoryDAO;
import cyz.ink.portfolio.pojo.FundManager;
import cyz.ink.portfolio.pojo.Instrument;
import cyz.ink.portfolio.pojo.TradingHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @ Author      : Zink
 * @ Date        : Created in 18:30 2019/8/13
 * @ Description :
 * @ Version     : 1.0
 **/
@Service
public class TradingHistoryService {
    @Autowired
    TradingHistoryDAO tradingHistoryDAO;

    public int add(int instrumentId, int volume, float price, FundManager fundManager) {
        TradingHistory tradingHistory = new TradingHistory();
        tradingHistory.setDate(new Date());
        tradingHistory.setVolume(volume);
        tradingHistory.setPrice(price);
        tradingHistory.setFundManagerId(fundManager.getId());
        tradingHistory.setInstrumentId(instrumentId);
        tradingHistoryDAO.save(tradingHistory);
        return 1;
    }

    public Page<TradingHistory> list(int fundManagerId, int start, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<TradingHistory> tradingHistories = tradingHistoryDAO.findTradingHistoriesByFundManagerId(fundManagerId, pageable);
        return tradingHistories;
    }
}
