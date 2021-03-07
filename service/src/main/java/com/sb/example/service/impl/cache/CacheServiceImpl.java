package com.sb.example.service.impl.cache;

import com.sb.example.dao.MerchMapper;
import com.sb.example.entity.Merch;
import com.sb.example.service.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.sb.example.common.CacheConstants.CACHE_ONE_HOURS;
import static com.sb.example.common.CacheConstants.MERCH_ID_CACHE_KEY;

/**
 * 缓存服务类
 * Created by lwnull2018@gmail.com ON 2021/3/4.
 */
@Service
public class CacheServiceImpl implements CacheService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MerchMapper merchMapper;

    @Override
    public Merch selectMerchById(Integer merchId) {
        String key = String.format(MERCH_ID_CACHE_KEY, merchId);

        ValueOperations<String, Object> stringOperations = redisTemplate.opsForValue();

        Merch merch = null;

        //判断缓存是否存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            Object obj = stringOperations.get(key);
            if (null != obj) {
                logger.info("从redis缓存获取的Merch对象");
                merch = (Merch) obj;
                return merch;
            }
        }

        merch = merchMapper.selectByPrimaryKey(merchId);
        if (null != merch) {
            //将加载后的数据放置到缓存中
            stringOperations.set(key, merch, CACHE_ONE_HOURS, TimeUnit.HOURS);

            return merch;
        }

        return null;
    }

    @Override
    public boolean removeMerchById(Integer merchId) {
        boolean remove = false;

        String key = String.format(MERCH_ID_CACHE_KEY, merchId);
        if (redisTemplate.hasKey(key)) {
            remove = redisTemplate.delete(key);
            logger.info("成功删除redis中Merch缓存对象");
        }

        return remove;
    }
}
