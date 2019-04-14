package com.ypc.common.utils;

/**
 * @Author: ypc
 * @Date: 2018-05-31 21:45
 * @Description:
 * @Modified By:
 */
public class AutoGeneratorES {

    private static String packageName;//包名
    private static String className;//文件名
    private static String moduleName;//
    private static String tableName;//数据库表名
    public static void main(String[] args) {


        CommonGeneratorES commonGeneratorES = new CommonGeneratorES();
        packageName = "com.jwd.es";
        className = "WorkstationES";
        moduleName = "aps_server";
        Object obj = new Object();
        commonGeneratorES.generatorAll(packageName, className,moduleName,obj);

    }
}
