package com.sb.example.service.impl;

import com.sb.example.dao.DaoTest;
import com.sb.example.service.ServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lwnull2018@gmail.com ON 2021/3/3.
 */
@Service
public class ServiceTestImpl implements ServiceTest {

    @Autowired
    private DaoTest daoTest;

    @Override
    public String showService() {
        return daoTest.showDao() + ":我是service!";
    }

}
