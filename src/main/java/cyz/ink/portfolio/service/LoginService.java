package cyz.ink.portfolio.service;

import cyz.ink.portfolio.dao.FundManagerDAO;
import cyz.ink.portfolio.pojo.FundManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

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

    public int fundManagerRegistor(HttpSession session, String username, String password) {
        //已经有用户登陆着
        if (session.getAttribute("user") != null) {
            return 2;
        }
        username = username.trim();
        //查询数据库中的这个用户
        FundManager f = fundManagerDAO.getByName(username.trim());
        //不存在用户
        if (f == null) return -1;
        //用户密码不正确
        if (!f.getPassword().equals(password)) return 0;
        //用户存在
        session.setAttribute("user", fundManagerDAO.getByName(username));
        return 1;
    }
}
