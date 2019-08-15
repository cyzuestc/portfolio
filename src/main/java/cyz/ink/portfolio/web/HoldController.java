package cyz.ink.portfolio.web;

import cyz.ink.portfolio.dao.CurrentPriceDAO;
import cyz.ink.portfolio.dao.FundManagerDAO;
import cyz.ink.portfolio.dao.HoldDAO;
import cyz.ink.portfolio.dao.TradingHistoryDAO;
import cyz.ink.portfolio.pojo.FundManager;
import cyz.ink.portfolio.pojo.Hold;
import cyz.ink.portfolio.pojo.HoldAndInstrumentBean;
import cyz.ink.portfolio.pojo.TradingHistory;
import cyz.ink.portfolio.service.HoldService;
import cyz.ink.portfolio.service.InstrumentsValueService;
import cyz.ink.portfolio.service.TradingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @ Author      : Zink
 * @ Date        : Created in 17:38 2019/8/13
 * @ Description :
 * @ Version     : 1.0
 **/
@RestController
public class HoldController {
    @Autowired
    HoldService holdService;
    @Autowired
    FundManagerDAO fundManagerDAO;
    @Autowired
    TradingHistoryService tradingHistoryService;
    @Autowired
    InstrumentsValueService instrumentsValueService;


    @GetMapping("/sellPortfolio")
    public int sellPortfolio(HttpSession session,
                             @RequestParam("instrumentId") int instrumentId,
                             @RequestParam("volume") int volume) {
        FundManager fundManager = (FundManager) session.getAttribute("user");
        //session中没有用户,无法购买
        if (fundManager == null) return -1;
        //session有用户,直接出售,-1:没有购买  -2:卖出数量大于购入数量 1:成功卖出
        return holdService.sell(instrumentId, volume, fundManager);
    }

    @GetMapping("/addPortfolio")
    public int addPortfolio(HttpSession session,
                            @RequestParam("instrumentId") int instrumentId,
                            @RequestParam("volume") int volume,
                            @RequestParam("price") float price) {
        FundManager fundManager = (FundManager) session.getAttribute("user");
        //session中没有用户或者volume小于等于0,无法购买
        if (fundManager == null || volume <= 0) return -1;
        //session有用户,直接购买
        holdService.add(instrumentId, volume, price, fundManager);
        return 1;
    }

    @GetMapping("/getPortfolio")
    public Page<HoldAndInstrumentBean> addPortfolio(HttpSession session,
                                                    @RequestParam(value = "start", defaultValue = "0") int start,
                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        FundManager fundManager = (FundManager) session.getAttribute("user");
        if (fundManager == null) return null;
        return holdService.list(fundManager, start, size);
    }

    @GetMapping(value = "/testAddPrice")
    public int testAddPrice(@RequestParam(value = "value", defaultValue = "10") int value) {
        instrumentsValueService.testCalculateInstrumentsValue(value);
        return 1;
    }
}
