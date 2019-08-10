package cyz.ink.portfolio.dao;

import cyz.ink.portfolio.pojo.Hold;
import cyz.ink.portfolio.pojo.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ Author      : Zink
 * @ Date        : Created in 17:41 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/

public interface InstrumentDAO extends JpaRepository<Instrument,Integer> {


}