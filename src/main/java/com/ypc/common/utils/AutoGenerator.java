package com.ypc.common.utils;

/**
 * Created by dodo on 2017/9/4.
 */
public class AutoGenerator {

	// 对serviceImpl新建成Junit，在生成   生成xml 要修改CommonConstants类下的数据库47.98.184.160
	
	
    private static String packageName;//包名
    private static String fileName;//文件名
    private static String tableName;//数据库表名
    private static String dburl;//数据库路径
    private static String modelName;//项目model名

    public static void main(String[] args) {

        GenEntityMysql genEntityMysql = new GenEntityMysql();
        packageName = "com.jwd.mes.confirm";
        tableName = "list_task_comfirm";
        dburl = "jdbc:mysql://47.98.184.160:3306/icmes_print";
        fileName = genEntityMysql.getPropertyName(tableName,true);
        modelName = "base_model_server";
        CommonGenerator commonGenerator = new CommonGenerator();
        commonGenerator.setModelName(modelName);
        commonGenerator.generatorAll(packageName,fileName);

        genEntityMysql.setModelName(modelName);
        genEntityMysql.setPackageOutPath(packageName);
        genEntityMysql.setTablename(tableName);
        genEntityMysql.setAuthorName("leo");
        genEntityMysql.genAll(dburl);


    }
}
