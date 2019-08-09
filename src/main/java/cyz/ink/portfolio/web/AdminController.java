package cyz.ink.portfolio.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
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
