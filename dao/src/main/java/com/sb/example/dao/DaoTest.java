package com.sb.example.dao;

import com.sb.example.entity.EntityTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by lwnull2018@gmail.com ON 2021/3/3.
 */
@Repository
public class DaoTest {

    public String showDao() {
        EntityTest entityTest = new EntityTest();
        return entityTest.showEntity() + ":我是dao";
    }

}
