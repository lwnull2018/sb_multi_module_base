package com.sb.example.service.cache;

import com.sb.example.entity.Merch;

/**
 * 缓存服务类接口
 * Created by lwnull2018@gmail.com ON 2021/3/4.
 */
public interface CacheService {

    /**
     * 通过商户ID获取商户对象
     * @param merchId
     * @return
     */
    public Merch selectMerchById(Integer merchId);

    /**
     * 移除商户对象缓存
     * @param merchId
     * @return
     */
    public boolean removeMerchById(Integer merchId);

}

