package com.sb.example.mq.producer;

import com.sb.example.entity.enumeration.RocketMQErrorEnum;
import com.sb.example.entity.exception.RocketMQException;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 生产者配置
 * Created by lwnull2018@gmail.com ON 2018/8/13.
 */
@SpringBootConfiguration
public class ProducerConfiguration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 发送同一类消息的设置为同一个group，保证唯一,默认不需要设置，rocketmq会使用ip@pid(pid代表jvm名字)作为唯一标示
     */
    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.producer.namesrvAddr}")
    private String namesrvAddr;
    /**
     * 消息最大大小，默认4M
     */
    @Value("${rocketmq.producer.maxMessageSize}")
    private Integer maxMessageSize ;
    /**
     * 消息发送超时时间，默认3秒
     */
    @Value("${rocketmq.producer.sendMsgTimeout}")
    private Integer sendMsgTimeout;
    /**
     * 消息发送失败重试次数，默认2次
     */
    @Value("${rocketmq.producer.retryTimesWhenSendFailed}")
    private Integer retryTimesWhenSendFailed;

    @Bean
    public DefaultMQProducer producer() throws RocketMQException {
        if (StringUtils.isEmpty(this.groupName)) {
            throw new RocketMQException(RocketMQErrorEnum.PARAMM_NULL,"groupName is blank",false);
        }
        if (StringUtils.isEmpty(this.namesrvAddr)) {
            throw new RocketMQException(RocketMQErrorEnum.PARAMM_NULL,"nameServerAddr is blank",false);
        }

        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(this.groupName);
        defaultMQProducer.setNamesrvAddr(this.namesrvAddr);

        logger.info(String.format("mq configuration message  groupName = %s, namesrvAddr = %s", this.groupName, this.namesrvAddr));

        //如果需要同一个jvm中不同的producer往不同的mq集群发送消息，需要设置不同的instanceName
        //producer.setInstanceName(instanceName);

        if(this.maxMessageSize!=null){
            defaultMQProducer.setMaxMessageSize(this.maxMessageSize);
        }
        if(this.sendMsgTimeout!=null){
            defaultMQProducer.setSendMsgTimeout(this.sendMsgTimeout);
        }

        //如果发送消息失败，设置重试次数，默认为2次
        if(this.retryTimesWhenSendFailed!=null){
            defaultMQProducer.setRetryTimesWhenSendFailed(this.retryTimesWhenSendFailed);
        }

        try {
            defaultMQProducer.start();

            logger.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]"
                    , this.groupName, this.namesrvAddr));
        } catch (MQClientException e) {
            logger.error(String.format("producer is error {%s}"
                    , e.getMessage()));
            throw new RocketMQException(e);
        }
        return defaultMQProducer;
    }
}