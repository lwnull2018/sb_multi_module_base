package com.sb.example.web.controller;

import com.sb.example.service.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lwnull2018@gmail.com ON 2021/3/3.
 */
@RestController
@RequestMapping("test")
public class WebTestController {

    @Autowired
    private ServiceTest serviceTest;

    @RequestMapping("hello")
    public String hello() {
        System.out.println("进入hello");
        return serviceTest.showService()+":我是web!";
    }

}
