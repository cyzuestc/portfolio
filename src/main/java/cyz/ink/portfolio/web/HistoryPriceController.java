package cyz.ink.portfolio.web;

import cyz.ink.portfolio.pojo.HistoryPrice;
import cyz.ink.portfolio.service.HistoryPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Author      : Zink
 * @ Date        : Created in 22:51 2019/8/13
 * @ Description :
 * @ Version     : 1.0
 **/
@RestController
public class HistoryPriceController {
    @Autowired
    HistoryPriceService historyPriceService;

    @GetMapping("/listHistoryPricesById")
    public List<HistoryPrice> listHistoryPricesByInstrumentId(
            @RequestParam(value = "instrumentId", defaultValue = "1") int instrumentId,
            @RequestParam(name = "start", defaultValue = "0") int start,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return historyPriceService.listByInstrumentId(instrumentId, start, size);
    }

    @GetMapping("/listHistoryPrices")
    public List<HistoryPrice> listHistoryPrices() {
        return historyPriceService.list();
    }
}
