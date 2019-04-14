package com.ypc.common.utils.html;


/**
 * 修改、删除返回json对象
 *
 * @author cff
 */
public class UpdateReturnJson {

    /**成功状态*/
    public static final Integer UPDATE_STATUS_SUCCESS = 1;

    /**失败状态*/
    public static final Integer UPDATE_STATUS_ERROR = 0;

    /**新增、修改成功信息*/
    public static final String UPDATE_MESSAGE_SUCCESS = "保存成功!";

    /**新增、修改失败信息*/
    public static final String UPDATE_MESSAGE_ERROR = "保存失败!";

    /**删除成功信息*/
    public static final String DELETE_MESSAGE_SUCCESS = "删除成功!";

    /**删除失败信息*/
    public static final String DELETE_MESSAGE_ERROR = "删除失败!";

    /**返回状态 1成功 0失败*/
    private Integer status;

    /**返回信息*/
    private String msg;

    /**返回数据*/
    private Object data;

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

    /**
     * s
     * 设置编辑成功
     *
     * @param
     */
    public void setUpdateSuccess() {
        this.msg = UPDATE_MESSAGE_SUCCESS;
        this.status = UPDATE_STATUS_SUCCESS;
    }

    /**
     * 设置编辑成功
     *
     * @param
     */
    public void setUpdateSuccess(String succMsg) {
        this.msg = succMsg;
        this.status = UPDATE_STATUS_SUCCESS;
    }

    /**
     * 设置删除成功
     *
     * @param
     */
    public void setDeleteSuccess() {
        this.msg = DELETE_MESSAGE_SUCCESS;
        this.status = UPDATE_STATUS_SUCCESS;
    }

    /**
     * 设置编辑失败
     *
     * @param
     */
    public void setUpdateError() {
        this.msg = UPDATE_MESSAGE_ERROR;
        this.status = UPDATE_STATUS_ERROR;
    }

    /**
     * 设置删除失败
     *
     * @param
     */
    public void setDeleteError() {
        this.msg = DELETE_MESSAGE_ERROR;
        this.status = UPDATE_STATUS_ERROR;
    }

    /**
     * 设置编辑失败
     *
     * @param
     */
    public void setUpdateError(String errorMsg) {
        this.msg = errorMsg;
        this.status = UPDATE_STATUS_ERROR;
    }

    /**
     * 设置查询成功
     *
     * @param
     */
    public void setSelectSuccess(String succMsg) {
        this.msg = succMsg;
        this.status = UPDATE_STATUS_SUCCESS;
    }

}
