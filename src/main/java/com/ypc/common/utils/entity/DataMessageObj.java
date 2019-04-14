package com.ypc.common.utils.entity;

import com.ypc.common.utils.constants.CommonConstants;


public class DataMessageObj<T> extends MessageObj {

    /**
     *
     */
    private static final long serialVersionUID = 8446318458464706490L;
    /**
     * 返回数据字符串
     */
    private T dataObject;

    public T getDataObject() {
        return dataObject;
    }

    public void setDataObject(T dataObject) {
        this.dataObject = dataObject;
    }

    /**
     * 设置结果为成功
     */
    public DataMessageObj<T> setSuccess(T dataObject) {
        setResult(CommonConstants.SUCCESS);
        setMessage(CommonConstants.SUCCESS_TIP);
        setDataObject(dataObject);
        return this;
    }
}
