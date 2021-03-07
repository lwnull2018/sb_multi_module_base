package com.sb.example.common.response;

import java.io.Serializable;

/**
 * Created by lwnull2018@gmail.com ON 2021/2/2.
 */
public class ActionResult implements Serializable {

    private Integer code;
    private String msg;
    private Object obj;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "ActionResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }
}
