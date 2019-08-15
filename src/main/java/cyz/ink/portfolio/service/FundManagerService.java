package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.FundManagerDAO;
import cyz.ink.portfolio.pojo.FundManager;
import cyz.ink.portfolio.pojo.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author      : Zink
 * @ Date        : Created in 20:50 2019/8/13
 * @ Description :
 * @ Version     : 1.0
 **/
@Service
public class FundManagerService {
    @Autowired
    FundManagerDAO fundManagerDAO;

    public int save(FundManager fundManager) {
        fundManagerDAO.save(fundManager);
        return 1;
    }

    public Page<FundManager> list(int start, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "profit");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<FundManager> fundManagers = fundManagerDAO.findAll(pageable);
        return fundManagers;
    }

    public List<FundManager> listAll() {
        return fundManagerDAO.findAll();
    }
}
