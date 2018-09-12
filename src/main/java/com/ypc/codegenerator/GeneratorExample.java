package com.ypc.codegenerator;

/**
 * @ClassName GeneratorExample
 * @Description TODO
 * @Author JWD
 * @Date 2018/9/12
 * @Version 1.0
 **/
public class GeneratorExample {
    private static String packageName;//包名
    private static String fileName;//文件名
    private static String tableName;//数据库表名
    private static String dburl;//数据库路径
    private static String modelName;//项目model名 非多model工程传null

    private static String className;//类名

    public static void main(String[] args) {

        //mysql自动生成代码
        GenEntityMysql genEntityMysql = new GenEntityMysql();
        packageName = "com.ypc";
        tableName = "main_test";
        dburl = "jdbc:mysql://localhost:3306/test";
        fileName = genEntityMysql.getPropertyName(tableName,true);
        modelName = "/test";

        CommonGenerator commonGenerator = new CommonGenerator();
        commonGenerator.setModelName(modelName);
        commonGenerator.generatorAll(packageName,fileName);

        genEntityMysql.setModelName(modelName);
        genEntityMysql.setPackageOutPath(packageName);
        genEntityMysql.setTablename(tableName);
        genEntityMysql.setUserName("root");
        genEntityMysql.setPassword("root");
        genEntityMysql.setAuthorName("ypc");
        genEntityMysql.genAll(dburl);

        //ES自动生成代码
        CommonGeneratorES commonGeneratorES = new CommonGeneratorES();
        packageName = "com.ypc";
        className = "Test";
        modelName = null;
        Object obj = new Object();//查询条件类
        commonGeneratorES.generatorAll(packageName, className,modelName,obj);
    }
}
