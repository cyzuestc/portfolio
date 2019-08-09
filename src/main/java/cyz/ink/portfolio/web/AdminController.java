package cyz.ink.portfolio.web;

import cyz.ink.portfolio.dao.CurrentPriceDAO;
import cyz.ink.portfolio.pojo.Admin;
import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    CurrentPriceDAO currentPriceDAO;

    @GetMapping(name = "/getAdmin")
    @ResponseBody
    public String getAdmin(){
        for (int i = 1; i < 100; i++) {
            CurrentPrice currentPrice = new CurrentPrice();
            float price = 1.12f;
            currentPrice.setId(i);
            currentPrice.setInstrumentId(i);
            currentPrice.setHigh(price);
            currentPrice.setLow(price);
            currentPrice.setOpen(price);
            currentPrice.setClose(price);
            currentPrice.setChanged(1.3f);
            currentPrice.setPrice(price);

            currentPrice.setDate(new Date());
            currentPriceDAO.save(currentPrice);
        }
        return adminService.get().size()+"";
    }

}
