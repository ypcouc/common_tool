package com.ypc.common.utils.html;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 列表查询返回json对象
 *
 * @author cff
 */
@JsonIgnoreProperties(value = {"handler"})
public class DataTablesReturnJSON implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String QUERY_MESSAGE_ERROR = "查询失败!";

    public static final String QUERY_MESSAGE_SUCCESS = "查询成功!";

    private DataTablesReturnObj data;

    private Integer status = 1;

    private String msg = QUERY_MESSAGE_SUCCESS;

    public DataTablesReturnObj getData() {
        return data;
    }

    public void setData(DataTablesReturnObj data) {
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
