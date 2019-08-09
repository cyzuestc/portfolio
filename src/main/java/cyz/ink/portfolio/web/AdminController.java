package cyz.ink.portfolio.web;

import cyz.ink.portfolio.dao.CurrentPriceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    CurrentPriceDAO currentPriceDAO;

    @GetMapping(value = "/getData")
    public List getData(){
        return currentPriceDAO.findAll();
    }
}
