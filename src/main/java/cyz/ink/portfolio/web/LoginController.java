package cyz.ink.portfolio.web;

import cyz.ink.portfolio.dao.FundManagerDAO;
import cyz.ink.portfolio.pojo.FundManager;
import cyz.ink.portfolio.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

/**
 * @ Author      : Zink
 * @ Date        : Created in 13:20 2019/8/12
 * @ Description : 登录界面
 * @ Version     : 1.0
 **/
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    FundManagerDAO fundManagerDAO;

    @PostMapping(value = "/fundManagerLogin")
    public int fundManagerlogin(@RequestParam(name = "username") String username,
                                @RequestParam(name = "password") String password,
                                HttpSession session) {
        int returnCode = loginService.fundManagerRegistor(session, username, password);
        return returnCode;
    }

    @PostMapping("/fundManagerSignUp")
    public Object register(@RequestParam(name = "username") String username,
                           @RequestParam(name = "password") String password) {
        username = HtmlUtils.htmlEscape(username);
        FundManager fundManager = fundManagerDAO.getByName(username);

        boolean exist = fundManager != null;
        //用户名已经被使用,不能使用
        if (exist) {
            return "-1";
        }
        fundManager = new FundManager();
        fundManager.setName(username);
        fundManager.setPassword(password);
        fundManager.setBalance(0);
        fundManagerDAO.save(fundManager);
        return 1;
    }

    //Header.html获取用户信息,用来展示在header
    @GetMapping("/getHeaderInfo")
    public FundManager getHeaderInfo(HttpSession session) {
        FundManager fundManager = (FundManager) session.getAttribute("user");
        return fundManager;
    }

    //用户登出
    @GetMapping("/fundManagerLogout")
    public int fundManagerLogout(HttpSession session) {
        session.removeAttribute("user");
        return 1;
    }

}
