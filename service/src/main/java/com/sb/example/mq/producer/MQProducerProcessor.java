package com.sb.example.mq.producer;

import com.google.gson.Gson;
import com.sb.example.entity.enumeration.RocketMQErrorEnum;
import com.sb.example.entity.exception.AppException;
import org.apache.ibatis.session.SqlSession;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 生产者发送消息处理器
 * .<br/>
 * 
 */
@Component
public class MQProducerProcessor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
    private DefaultMQProducer producer;

    @Autowired
    private SqlSession sqlSession;

    @Value("${rocketmq.producer.TestTopicName}")
	private String testTopicName;

    public String getTestTopicName() {
        return testTopicName;
    }

	/**
	 * 同步发送消息
	 * @param topic	主题
	 * @param tag 消息标签，只支持设置一个Tag（服务端消息过滤使用）
	 * @param keys 消息关键词，多个Key用MessageConst.KEY_SEPARATOR隔开（查询消息使用）
	 * @param msg	消息
	 * @return
	 */
	public MQSendResult send(String topic,String tag,String keys,String msg){
		MQSendResult mqSendResult = null;
		try {
			validateSendMsg(topic, tag, msg);
			org.apache.rocketmq.client.producer.SendResult sendResult = null;
             Message sendMsg = new Message(topic,tag,keys,msg.getBytes(RemotingHelper.DEFAULT_CHARSET));
			//默认3秒超时
			sendResult = producer.send(sendMsg);
			mqSendResult = new MQSendResult(sendResult);
		}catch(Exception e){
			logger.error("消息发送失败",e);
			mqSendResult = new MQSendResult("消息发送失败", e);
		}
		logger.info("发送消息到消息队列的响应信息为："+mqSendResult.toString());
		return mqSendResult==null?new MQSendResult():mqSendResult;
	}

    /**
     * 发送数据
     * @param keys  订单号
     * @param msg   数据对象
     * @param tag 消息标签，存储【商户id】
     */
	public void asynSendData(String keys, Object msg, String tag) {
        Gson gson = new Gson();
		try {
			validateSendMsg(testTopicName, tag, msg);
			Message sendMsg = new Message(testTopicName, tag, keys, gson.toJson(msg).getBytes(RemotingHelper.DEFAULT_CHARSET));
			producer.send(sendMsg, new SendCallback() {
				@Override
				public void onSuccess(org.apache.rocketmq.client.producer.SendResult sendResult) {
					logger.info(String.format("订单消息发送成功。topic:%s,tag:%s,keys:%s,msg:%s", testTopicName, tag, keys, msg));
				}

				@Override
				public void onException(Throwable e) {
                    msgSaveDB(testTopicName, tag, keys, gson.toJson(msg),e);
				}
			});
		} catch (Exception e) {
            msgSaveDB(testTopicName, tag, keys, gson.toJson(msg), e);
		}
	}

	/**
	 * 校验参数
	 * @param topic
	 * @param tag
	 * @param msg
	 */
	private void validateSendMsg(String topic, String tag, Object msg) {
		if(topic==null){
			throw new AppException(RocketMQErrorEnum.PARAMM_NULL,"topic为空",false);
		}
		if(tag == null){
			throw new AppException(RocketMQErrorEnum.PARAMM_NULL,"tag为空",false);
		}
		if(msg==null){
			throw new AppException(RocketMQErrorEnum.PARAMM_NULL,"msg为空",false);
		}
	}

    /**
     * 将发送失败的消息存储到文件中
     * @param topicName
     * @param tag
     * @param keys
     * @param msg
     * @param e
     */
    private void msgSaveDB(String topicName, String tag, String keys, String msg, Throwable e){

        int saveResult = 0;//getMapper().insertSelective(failLog);
        if (saveResult <= 0){
            logger.info(String.format("消息发送异常，保存数据库也失败。topic:%s,tag:%s,keys:%s,msg:%s", topicName,tag,keys,msg));
        }
        logger.error("MQ发送失败，保存数据库结果saveResult="+saveResult,e);
    }
	
}
