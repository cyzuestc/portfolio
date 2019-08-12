package cyz.ink.portfolio.web;

import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.InstrumentType;
import cyz.ink.portfolio.service.CurrentPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Author      : Zink
 * @ Date        : Created in 16:09 2019/8/12
 * @ Description :
 * @ Version     : 1.0
 **/
@RestController
public class CurrentPriceController {
    @Autowired
    CurrentPriceService currentPriceService;

    @GetMapping(value = "/getCurrentPriceByType")
    public Page<CurrentPrice> getCurrentPriceByType(@RequestParam(name = "type", defaultValue = "Stocks") InstrumentType type,
                                                    @RequestParam(name = "start", defaultValue = "start") int start,
                                                    @RequestParam(name = "size", defaultValue = "size") int size) {
        System.out.println(">>>>>>>>>>");
        return currentPriceService.list(type,start,size);
    }

}