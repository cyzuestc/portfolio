package cyz.ink.portfolio.web;

import cyz.ink.portfolio.dao.FundManagerDAO;
import cyz.ink.portfolio.pojo.FundManager;
import cyz.ink.portfolio.service.FundManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @ Author      : Zink
 * @ Date        : Created in 22:03 2019/8/13
 * @ Description :
 * @ Version     : 1.0
 **/
@RestController
public class FundManagerController {
    @Autowired
    FundManagerService fundManagerService;

    @GetMapping("/listFundManager")
    public Page<FundManager> list(
            HttpSession session,
            @RequestParam(value = "start", defaultValue = "0") int start,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return fundManagerService.list(start, size);
    }
}
