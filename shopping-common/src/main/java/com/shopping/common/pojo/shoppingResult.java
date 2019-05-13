package com.shopping.common.pojo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 */
public class shoppingResult implements Serializable{
    // 响应业务状态
    private Integer status;
    // 响应消息
    private String msg;
    // 响应中的数据
    private Object data;
    //构建其他状态的taotaoresult对象
    
    public static shoppingResult build(Integer status, String msg, Object data) {
        return new shoppingResult(status, msg, data);
    }

    public static shoppingResult ok(Object data) {
        return new shoppingResult(data);
    }

    public static shoppingResult ok() {
        return new shoppingResult(null);
    }

    public shoppingResult() {

    }

    public static shoppingResult build(Integer status, String msg) {
        return new shoppingResult(status, msg, null);
    }

    public shoppingResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public shoppingResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
