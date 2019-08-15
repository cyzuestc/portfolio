package cyz.ink.portfolio.web;

import cyz.ink.portfolio.pojo.CurrentPrice;
import cyz.ink.portfolio.pojo.Instrument;
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
 * @ Date        : Created in 10:27 2019/8/12
 * @ Description :
 * @ Version     : 1.0
 **/
@RestController
public class InstrumentController {
    @Autowired
    InstrumentService instrumentService;

    @GetMapping(value = "/getInstruments")
    public Page<Instrument> getInstruments(@RequestParam(name = "start", defaultValue = "0") int start,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        return instrumentService.getInstruments(start, size);
    }

    @GetMapping(value = "/getInstrumentsByType")
    public Page<Instrument> getInstrumentsByType(
            @RequestParam(value = "type", defaultValue = "Bonds") InstrumentType type,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return instrumentService.getInstrumentsByType(type, start, size);
    }

    @GetMapping(value = "/getInstrumentById")
    public Instrument getInstrumentById(@RequestParam(value = "id") int id) {
        return instrumentService.getInstrumentById(id);
    }
}
