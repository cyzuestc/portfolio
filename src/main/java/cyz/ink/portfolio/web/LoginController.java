package cyz.ink.portfolio.web;

import cyz.ink.portfolio.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "/fundManagerlogin")
    public int fundManagerlogin(@RequestParam(name = "username") String username,
                                @RequestParam(name = "password") String password) {
        int returnCode = loginService.fundManagerRegistor(username, password);
        return returnCode;
    }
}
