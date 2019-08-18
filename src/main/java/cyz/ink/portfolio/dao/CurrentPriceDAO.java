package cyz.ink.portfolio.dao;

import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.InstrumentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @ Author      : Zink
 * @ Date        : Created in 17:41 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/

public interface CurrentPriceDAO extends JpaRepository<CurrentPrice,Integer> {

}
