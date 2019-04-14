package com.ypc.common.utils.entity;

import com.ypc.common.utils.constants.CommonConstants;

import java.io.Serializable;


public class MessageObj implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5198950848811305692L;

    /**
     * 查询结果 成功为CommonConstants.SUCCESS  失败为 CommonConstants.FAILED
     */
    private String result;

    /**
     * 消息
     */
    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 设置结果为成功
     */
    public void setSuccess() {
        this.result = CommonConstants.SUCCESS;
        this.message = CommonConstants.SUCCESS_TIP;
    }

    /**
     * 设置结果为失败
     */
    public void setFailed() {
        this.result = CommonConstants.FAILED;
        this.message = CommonConstants.FAILED_TIP;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getValid() {
        return CommonConstants.TRUE.equals(this.result);
    }
}
