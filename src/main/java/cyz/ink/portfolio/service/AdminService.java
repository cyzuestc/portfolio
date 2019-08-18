package cyz.ink.portfolio.service;

import cyz.ink.portfolio.pojo.Admin;
import cyz.ink.portfolio.dao.AdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author      : Zink
 * @ Date        : Created in 17:43 2019/8/9
 * @ Description :
 * @ Version     : 1.0
 **/
@Service
public class AdminService {
    @Autowired
    AdminDAO adminDAO;

    public List<Admin> get() {
        return adminDAO.findAll();
    }
}
