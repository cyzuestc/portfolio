package cyz.ink.portfolio.dao;

import cyz.ink.portfolio.pojo.TradingHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ Author      : Zink
 * @ Date        : Created in 17:41 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/

public interface TradingHistoryDAO extends JpaRepository<TradingHistory,Integer> {
    Page<TradingHistory> findTradingHistoriesByFundManagerId(int id, Pageable pageable);
}
