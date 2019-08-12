package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.FundManagerDAO;
import cyz.ink.portfolio.pojo.FundManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ Author      : Zink
 * @ Date        : Created in 13:22 2019/8/12
 * @ Description :
 * @ Version     : 1.0
 **/
@Service
public class LoginService {
    @Autowired
    FundManagerDAO fundManagerDAO;

    public int fundManagerRegistor(String username, String password) {
        username = username.trim();
        //查询数据库中已经有这个用户了,返回-1
        FundManager f = fundManagerDAO.getByName(username.trim());
        //不存在用户
        if (f == null) return -1;
        //用户密码不正确
        if (!f.getPassword().equals(password)) return 0;
        return 1;
    }
}
