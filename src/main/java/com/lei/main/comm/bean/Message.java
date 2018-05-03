package com.lei.main.comm.bean;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel("信息")
public class Message<T> {
    @ApiModelProperty("消息编码")
    private String code;
    @ApiModelProperty("具体信息")
    private T message;

    public Message() {
        this.code = "-99";
        this.message = (T)"因未知错误操作失败";
    }

    public Message(String code, T message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

}
