package cyz.ink.portfolio.web;

import cyz.ink.portfolio.dao.CurrentPriceDAO;
import cyz.ink.portfolio.dao.FundManagerDAO;
import cyz.ink.portfolio.dao.InstrumentDAO;
import cyz.ink.portfolio.pojo.InstrumentType;
import cyz.ink.portfolio.service.excel.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class AdminController {
    @Autowired
    CurrentPriceDAO currentPriceDAO;
    @Autowired
    FundManagerDAO fundManagerDAO;
    @Autowired
    InstrumentDAO instrumentDAO;
    @Autowired
    ExcelService excelService;

    @GetMapping(value = "/getData")
    public List getData(){
        return currentPriceDAO.findAll();
    }


    @PostMapping("/uploadExcel")
    public Object add(@RequestParam(name = "InstrumentType", defaultValue = "Bonds") InstrumentType instrumentType, MultipartFile excelFile, HttpServletRequest request) {

        File file = new File("d:\\" + excelFile.getOriginalFilename());
        try {
            excelFile.transferTo(file);
            log.warn(file.isFile() + "");
            excelService.addDataFromExcel(file, instrumentType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
