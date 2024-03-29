package cyz.ink.portfolio.dao;

import cyz.ink.portfolio.pojo.HistoryPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @ Author      : Zink
 * @ Date        : Created in 17:41 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/

public interface HistoryPriceDAO extends JpaRepository<HistoryPrice,Integer> {

//     Page<HistoryPrice> findHistoryPricesById(int id, Pageable pageable);

//     @Query(name = "select * from historyprice where instrument_id = ?1",nativeQuery = true)
//     List<HistoryPrice> findAllByInstrumentId(int id);

    List<HistoryPrice> findAllByInstrumentId(Integer instrumentId);

    HistoryPrice findHistoryPriceByInstrumentIdAndDate(int instrumentId, Date date);
}
