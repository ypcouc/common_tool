package com.ypc.common.utils.html;

import java.util.List;

/**
 * 参数页面列表返回格式
 *
 * @author cff
 */
public class CategoryReturnJson {

    public static final String QUERY_MESSAGE_ERROR = "查询失败!";

    public static final String QUERY_MESSAGE_SUCCESS = "查询成功!";

    private List<?> rows;

    private Integer status = 1;

    private String msg = QUERY_MESSAGE_SUCCESS;

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
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
