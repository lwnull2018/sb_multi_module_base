package com.sb.example.service;

import com.sb.example.common.response.ActionResult;
import com.sb.example.common.response.PageDataResult;
import com.sb.example.entity.DTO.MerchDTO;
import com.sb.example.entity.Merch;

/**
 * Created by lwnull2018@gmail.com ON 2021/3/3.
 */
public interface MerchManageService {

    public PageDataResult getMerchList(MerchDTO merchSearch, Integer pageNum, Integer pageSize);

    public Merch selectMerchById(Integer merchId);

    public boolean removeMerch(Integer merchId);

    public ActionResult changeBalance(Integer merchId);

    public boolean sendData(Integer merchId);

}
