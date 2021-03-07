package com.sb.example.mq.consumer;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.sb.example.entity.Merch;
import com.sb.example.service.MerchManageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.sb.example.common.RichConstants.REDISSON_LEASETIME_SEC;
import static com.sb.example.common.RichConstants.REDISSON_WAITTIME_SEC;


/**
 * 消费者消费消息路由
 * Created by lwnull2018@gmail.com ON 2018/8/13.
 */
@Component
public class ConsumeListenerProcessor implements MessageListenerConcurrently {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private MerchManageService merchManageService;

    @Autowired
    private RedissonClient redissonClient;

    @Value("${rocketmq.producer.TestTopicName}")
    private String testTopicName;

    @Value("${consumer.max.reconsume.times}")
    private int reconsumeTimes;

    /**
     * 收款名或账号不存在
     */
    private static final String PAYEE_NOT_EXIST = "PAYEE_NOT_EXIST";

    /**
     * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息<br/>
     * 不要抛异常，如果没有return CONSUME_SUCCESS ，consumer会重新消费该消息，直到return CONSUME_SUCCESS
     */
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        //默认重试
        ConsumeConcurrentlyStatus result = ConsumeConcurrentlyStatus.RECONSUME_LATER;
        if (CollectionUtils.isEmpty(list)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        try {
            for (MessageExt me : list) {
                String topic = me.getTopic();
                String tags = me.getTags();
                Long mqStoreTime = me.getStoreTimestamp();
                int times = me.getReconsumeTimes();
                String keys = me.getKeys();
                String msgBody = new String(me.getBody(), RemotingHelper.DEFAULT_CHARSET);

                //获取该消息重试次数
                int reconsume = me.getReconsumeTimes();
                if (reconsume >= reconsumeTimes) {//超过最大重试次数，将数据保存到数据库，避免数据丢失
                    msgSaveDB(topic, tags, keys, msgBody);
                    continue;
                }

                //订单消息
                if (testTopicName.contains(topic)) {
                    System.out.println(String.format("拉取消费消息：msg:%s, keys:%s", msgBody, keys));

                    Merch merch = new Gson().fromJson(msgBody, Merch.class);
                    handler(merch);
                } else {
                    logger.warn("消息的主题不对，topic:%s", topic);
                }
            }

            //成功处理完成才返回success
            result = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[MQ消费异常]", e);
        }

        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return result;
    }

    /**
     *  业务处理部分
     * @param order
     */
    private void handler(Merch merch) {
        //获取锁实例
        RLock rLock = redissonClient.getLock(String.valueOf(merch.getId()));
        boolean isLock = false;
        try {
            //等待10秒，持有30秒自动释放
            isLock = rLock.tryLock(REDISSON_WAITTIME_SEC, REDISSON_LEASETIME_SEC, TimeUnit.SECONDS);
            if (!isLock) {
                logger.warn(Thread.currentThread().getName() + ",isLock=" + isLock + ",消费端未抢到锁");
                logger.error("系统繁忙订单处理失败，商户订单号:");
                return;
            }

            logger.info("MQ消费者开始处理业务逻辑部分。。。。。。");
            Thread.sleep(5 * 1000);
            logger.info("MQ消费者业务逻辑部分处理完毕。");

        } catch (Exception e) {
            logger.error("业务处理部分发生异常，异常信息:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if(isLock){
                logger.info(Thread.currentThread().getName()+",消费端处理完毕，释放锁！！！");
                rLock.unlock();
            }
        }
    }


    /**
     * 将消费失败的消息存储到数据库中
     *
     * @param topicName
     * @param tag
     * @param keys
     * @param msg
     */
    private void msgSaveDB(String topicName, String tag, String keys, String msg) {

        int saveResult = 0;//getMqConsumerFailLogMapper().insertSelective(failLog);
        if (saveResult <= 0) {
            logger.error(String.format("MQ消费超过最大次数，保存到数据库中失败！。topic:%s,tag:%s,keys:%s,msg:%s", topicName, tag, keys, msg));
        } else {
            logger.info("MQ消费失败，保存数据库结果saveResult=" + saveResult);
        }
    }



}