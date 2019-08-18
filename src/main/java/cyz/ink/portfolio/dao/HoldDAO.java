package cyz.ink.portfolio.dao;

import cyz.ink.portfolio.pojo.FundManager;
import cyz.ink.portfolio.pojo.Hold;
import cyz.ink.portfolio.pojo.Instrument;
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
    Hold getHoldByInstrumentAndFundManager(Instrument instrument, FundManager fundManager);

    Page<Hold> findHoldsByFundManager(FundManager fundManager, Pageable pageable);
}
