package com.sb.example.common.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Title: PageDataResult
 * @Description: 封装DTO分页数据（记录数和所有记录）
 * @author: jack
 * @version: 1.0
 * @date: 2018/11/21 11:15
 */
public class PageDataResult {

    private Integer code=200;

    //总记录数量
    private Integer totals;

    private List<?> list;

    //用于存放一些
    private BigDecimal sumMoney;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getTotals() {
        return totals;
    }

    public void setTotals(Integer totals) {
        this.totals = totals;
    }

    public List <?> getList() {
        return list;
    }

    public void setList(List <?> list) {
        this.list = list;
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    @Override
    public String toString() {
        return "PageDataResult{" +
                "code=" + code +
                ", totals=" + totals +
                ", list=" + list +
                ", sumMoney=" + sumMoney +
                '}';
    }
}
