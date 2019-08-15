package cyz.ink.portfolio.web;

import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.InstrumentType;
import cyz.ink.portfolio.service.CurrentPriceService;
import cyz.ink.portfolio.service.InstrumentService;
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

    @Autowired
    InstrumentService instrumentService;

    @GetMapping(value = "/getCurrentPriceByType")
    public Page<CurrentPrice> getCurrentPriceByType(@RequestParam(name = "type", defaultValue = "Stocks") InstrumentType type,
                                                    @RequestParam(name = "start", defaultValue = "0") int start,
                                                    @RequestParam(name = "size", defaultValue = "20") int size) {

        return currentPriceService.getCurrentPriceByType(type, start, size);
    }

    @GetMapping(value = "/getCurrentPricesByInstrumentId")
    public Page<CurrentPrice> getCurrentPriceByType(@RequestParam(name = "id", defaultValue = "0") int instrumentId,
                                                    @RequestParam(name = "start", defaultValue = "0") int start,
                                                    @RequestParam(name = "size", defaultValue = "20") int size) {
        return currentPriceService.listByInstrumentId(instrumentId, start, size);
    }

    @GetMapping(value = "/getCurrentPriceById")
    public CurrentPrice getCurrentPriceById(@RequestParam("id") int id) {
        return currentPriceService.get(id);
    }

}
