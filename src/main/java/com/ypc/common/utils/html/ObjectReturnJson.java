package com.ypc.common.utils.html;

public class ObjectReturnJson {

    /**返回数据*/
    private Object data;

    /**状态 1成功 0失败*/
    private Integer status;

    /**返回信息*/
    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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


}
