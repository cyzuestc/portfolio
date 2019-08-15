package cyz.ink.portfolio.dao;

import cyz.ink.portfolio.pojo.Hold;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ Author      : Zink
 * @ Date        : Created in 17:41 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/

public interface HoldDAO extends JpaRepository<Hold,Integer> {
    Hold getHoldByInstrumentIdAndFundManagerId(int InstrumentId, int fundManagerId);

    Page<Hold> findAllByFundManagerId(int fundManagerId, Pageable pageable);

    List<Hold> findAllByFundManagerId(int fundManagerId);
}
