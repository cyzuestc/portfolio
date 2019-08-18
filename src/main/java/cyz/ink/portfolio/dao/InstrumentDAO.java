package cyz.ink.portfolio.dao;

import cyz.ink.portfolio.pojo.Hold;
import cyz.ink.portfolio.pojo.Instrument;
import cyz.ink.portfolio.pojo.InstrumentType;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import cyz.ink.portfolio.pojo.CurrentPrice;
import java.util.List;

/**
 * @ Author      : Zink
 * @ Date        : Created in 17:41 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/

public interface InstrumentDAO extends JpaRepository<Instrument,Integer> {

}
