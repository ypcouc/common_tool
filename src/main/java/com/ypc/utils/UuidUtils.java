package com.ypc.utils;

import java.util.UUID;

/**
 * @Author: ypc
 * @Date: 2018-05-27 20:48
 * @Description:
 * @Modified By:
 */
public class UuidUtils {
    public static String getId(){
        String id = UUID.randomUUID().toString().replace("-", "");
        return id;
    }
}
