package cyz.ink.portfolio.service;

import cyz.ink.portfolio.pojo.Admin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Adminservice_test {
    @Autowired
    AdminService adminService ;

    @Test
    public void t(){


    }
    @Test
    public void addData(){
       // addData();


        List<Admin> test= adminService.get();
        System.out.println(test);


    }



}
