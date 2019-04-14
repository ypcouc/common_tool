package com.ypc.common.utils;

/**
 * @Author: ypc
 * @Date: 2018-05-20 18:04
 * @Description:
 * @Modified By:
 */
public interface OkhttpCallback {
    /**
     * 成功回调
     * @param resultstr
     */
    void doSuccess(String resultstr);

    /**
     * 失败回调
     */
    void doFailure();

}
