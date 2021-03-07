package com.sb.example.web.controller;

import com.sb.example.common.response.ActionResult;
import com.sb.example.common.response.PageDataResult;
import com.sb.example.entity.DTO.MerchDTO;
import com.sb.example.entity.Merch;
import com.sb.example.service.MerchManageService;
import com.sb.example.web.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 示例Controller包括：
 *   dao读取、redis缓存查询、分布式锁(Redission)、MQ消息发送
 * Created by lwnull2018@gmail.com ON 2021/3/3.
 */
@RestController
@RequestMapping("merch")
public class MerchManageController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MerchManageService merchManageService;

    @Autowired
    private AppConfig appConfig;

    /**
     * 访问示例：http://localhost:8080/merch/list?pageNum=1&pageSize=10
     * @param pageNum
     * @param pageSize
     * @param merchSearch
     * @return
     */
    @RequestMapping("list")
    public PageDataResult list(@RequestParam("pageNum") Integer pageNum,
                       @RequestParam("pageSize") Integer pageSize, MerchDTO merchSearch) {
        System.out.println("merch list");
        logger.info("上传文件路径：" + appConfig.uploadPath);
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }

            // 获取用户列表
            pdr = merchManageService.getMerchList(merchSearch, pageNum ,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("商户列表查询异常！", e);
        }
        return pdr;
    }

    /**
     * 访问示例：http://localhost:8080/merch/get?id=10008
     * 从缓存获取Merch对象
     * @param merchId
     * @return
     */
    @RequestMapping("get")
    public Merch get(@RequestParam("id") Integer merchId) {
        System.out.println("merch get from redis");
        Merch merch = null;
        try {
            // 从redis缓存获取Merch对象
            merch = merchManageService.selectMerchById(merchId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("从redis缓存获取商户对象发生异常！", e);
        }
        return merch;
    }

    /**
     * 访问示例: http://localhost:8080/merch/del?id=xxx
     * 从redis缓存删除Merch对象
     * @param merchId
     * @return
     */
    @RequestMapping("del")
    public Boolean del(@RequestParam("id") Integer merchId) {
        System.out.println("merch del from redis");
        boolean result = false;
        try {
            // 从redis缓存删除Merch对象
            result = merchManageService.removeMerch(merchId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("从redis缓存移除商户对象发生异常！", e);
        }
        return result;
    }

    /**
     * 访问示例: http://localhost:8080/merch/change?id=xxx
     * 共用对象部分的处理逻辑演示，需要用到分布式锁(Redission)，避免并发操作时数据被改写
     * @param merchId
     * @return
     */
    @RequestMapping("change")
    public ActionResult change(@RequestParam("id") Integer merchId) {
        System.out.println("分布式锁。。。。。。");
        ActionResult result = new ActionResult();
        try {
            result = merchManageService.changeBalance(merchId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("高并发处理部分发生异常！", e);
        }
        return result;
    }

    /**
     * 访问示例: http://localhost:8080/merch/send?id=xxx
     * MQ消息发送演示，需要用到分布式锁，避免并发操作时数据被改写
     * @param merchId
     * @return
     */
    @RequestMapping("send")
    public ActionResult send(@RequestParam("id") Integer merchId) {
        System.out.println("发送MQ消息对象。。。。。。");
        ActionResult result = new ActionResult();
        try {
            result = merchManageService.changeBalance(merchId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("高并发处理部分发生异常！", e);
        }
        return result;
    }

}
