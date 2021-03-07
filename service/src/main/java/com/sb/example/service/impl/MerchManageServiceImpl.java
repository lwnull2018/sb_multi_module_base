package com.sb.example.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sb.example.common.response.ActionResult;
import com.sb.example.common.response.PageDataResult;
import com.sb.example.dao.MerchMapper;
import com.sb.example.entity.DTO.MerchDTO;
import com.sb.example.entity.Merch;
import com.sb.example.mq.producer.MQProducerProcessor;
import com.sb.example.service.MerchManageService;
import com.sb.example.service.cache.CacheService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sb.example.common.RichConstants.*;

/**
 * Created by lwnull2018@gmail.com ON 2021/3/3.
 */
@Service
public class MerchManageServiceImpl implements MerchManageService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MerchMapper merchMapper;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private MQProducerProcessor mqProcuder;

    @Override
    public PageDataResult getMerchList(MerchDTO merchSearch, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<MerchDTO> merchList = merchMapper.getMerchList(merchSearch);

        PageHelper.startPage(pageNum, pageSize);

        if(merchList.size() != 0){
            PageInfo<MerchDTO> pageInfo = new PageInfo<>(merchList);
            pageDataResult.setList(merchList);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }

        return pageDataResult;
    }

    @Override
    public Merch selectMerchById(Integer merchId) {
        return cacheService.selectMerchById(merchId);
    }

    @Override
    public boolean removeMerch(Integer merchId) {
        return cacheService.removeMerchById(merchId);
    }

    @Override
    public ActionResult changeBalance(Integer merchId) {
        ActionResult actionResult = new ActionResult();

        //获取锁实例
        RLock rLock = redissonClient.getLock(String.valueOf(merchId));
        boolean isLock = false;
        try {
            //等待10秒，持有30秒自动释放
            isLock = rLock.tryLock(REDISSON_WAITTIME_SEC, REDISSON_LEASETIME_SEC, TimeUnit.SECONDS);
            if (!isLock) {
                logger.warn(Thread.currentThread().getName() + ",isLock=" + isLock + ",未抢到分布式锁");
                actionResult.setCode(FAIL);
                actionResult.setMsg("系统繁忙操作失败，请稍后再试");
                logger.error("系统繁忙，手动变更商户余额失败");
                return actionResult;
            }

            //拿到锁之后的业务处理部分
            logger.info("拿到分布式锁，开始处理业务逻辑部分。。。。。。");
            Thread.sleep(5 * 1000);
            logger.info("业务逻辑部分处理完成。。。。。。");

            actionResult.setCode(SUCCESS);
            actionResult.setMsg("操作成功");

            return actionResult;
        } catch (Exception e) {
            actionResult.setCode(FAIL);
            actionResult.setMsg("变更商户余额发生异常！");
            logger.error("手动变更商户余额发生异常！", e);
            return actionResult;
        } finally {
            if(isLock){
                logger.info(Thread.currentThread().getName()+",处理完毕，释放锁！！！");
                rLock.unlock();
            }
        }
    }

    @Override
    public boolean sendData(Integer merchId) {
        Merch merch = cacheService.selectMerchById(merchId);

        mqProcuder.asynSendData(String.valueOf(merch.getId()), merch, String.valueOf(merch.getAgentId()));

        logger.info("消息发布成功");

        return true;
    }
}
