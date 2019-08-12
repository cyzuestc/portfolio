package cyz.ink.portfolio.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ Author      : Zink
 * @ Date        : Created in 17:45 2019/8/9
 * @ Description : Router
 * @ Version     : 1.0
 **/
@Controller
public class WebController {
    @GetMapping(value = "/index")
    public String index(){
        return "index/index";
    }
    @GetMapping(value = "/")
    public String defaultPage(){
        return "index/index";
    }
    @GetMapping(value = "/admin")
    public String admin(){
        return "admin/admin";
    }
    @GetMapping(value = "/login")
    public String registor(){
        return "login/login";
    }
}
