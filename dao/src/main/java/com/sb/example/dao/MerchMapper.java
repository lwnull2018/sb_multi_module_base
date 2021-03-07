package com.sb.example.dao;

import com.sb.example.entity.DTO.MerchDTO;
import com.sb.example.entity.Merch;
import com.sb.example.tk.mapper.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lwnull2018@gmail.com ON 2021/3/3.
 */
@Repository
public interface MerchMapper extends MyMapper<Merch> {

    List<MerchDTO> getMerchList(MerchDTO merchDTO);

}
