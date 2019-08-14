package cyz.ink.portfolio.web;

import cyz.ink.portfolio.pojo.FundManager;
import cyz.ink.portfolio.pojo.TradingHistory;
import cyz.ink.portfolio.service.TradingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ Author      : Zink
 * @ Date        : Created in 21:34 2019/8/13
 * @ Description :
 * @ Version     : 1.0
 **/
@RestController
public class TradingHistoryController {
    @Autowired
    TradingHistoryService tradingHistoryService;

    @GetMapping("/tradingHistoryList")
    public Page<TradingHistory> tradingHistoryList(HttpSession session,
                                                   @RequestParam(value = "start", defaultValue = "0") int start,
                                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        FundManager fundManager = (FundManager) session.getAttribute("user");
        if (fundManager == null) return null;
        return tradingHistoryService.list(fundManager.getId(), start, size);
    }
}
