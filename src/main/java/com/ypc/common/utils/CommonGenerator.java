package com.ypc.common.utils;

/**
 * Created by dodo on 2017/9/2.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/** 文件生成器*/
public class CommonGenerator {

    private static final String mapperSuffix = "Mapper.java";
    private static final String serviceSuffix  = "Service.java";
    private static final String serviceImplSuffix  = "ServiceImpl.java";
    private static final String controllerSuffix  = "Controller.java";
    private static final String newLine = "\r\n";
    private static final String newLine2 = "\r\n\r\n";
    private static final String newLine3 = "\r\n\r\n\r\n";
    private static final String tab = "    ";
    private static final String tab2 = "        ";
    private static final String tab3 = "            ";
    private static FileOutputStream fw = null;
    private static StringBuffer sb = null;
    private static String lowerStr = null;
    private static String uppperStr = null;
    private static String pathVariable;
    private  String modelName="";//项目model名

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void generatorAll(String packageName, String fileName){
        try {
            this.generatorMapper(packageName, fileName);
            this.generatorService(packageName, fileName);
            this.generatorServiceImpl(packageName, fileName);
            this.generatorController(packageName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param packagePath
     * @param filename 文件名（大写）例如：UserInfo
     * @return
     */
    public  String generatorMapper(String packagePath, String filename) {
        String result = "";
        try {
            File temp = new File("");

            pathVariable = temp.getAbsolutePath()+ modelName+
                    "/src/main/java/"+ packagePath.replace(".", "/")
                    + "/dao/" +filename + mapperSuffix;
            File file = new File(pathVariable);
            fw = new FileOutputStream(file);
            sb = new StringBuffer();
            lowerStr = CharUtils.toLowerCaseFirstOne(filename);
            uppperStr = CharUtils.toUpperCaseFirstOne(filename);
            sb.append("package " + packagePath +".dao;");
            sb.append(newLine2);
            sb.append("import com.github.pagehelper.Page;");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo." + filename + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.query." + filename + "Query" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.view." + filename + "View" + ";");
            sb.append(newLine2);

            sb.append("public interface " + filename +"Mapper {");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 根据id查询");
            sb.append(newLine);
            sb.append(tab);
            sb.append(uppperStr + "View" + " get" + uppperStr + "ById(String id);");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 查询所有");
            sb.append(newLine);
            sb.append(tab);
            sb.append("Page<" + uppperStr +"View> get" + uppperStr +"List("+uppperStr +"Query "+ lowerStr +"Query);");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 增加");
            sb.append(newLine);
            sb.append(tab);
            sb.append("int add" + uppperStr +"("+ uppperStr +" " + lowerStr +");");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 修改");
            sb.append(newLine);
            sb.append(tab);
            sb.append("int update"+  uppperStr +"(" + uppperStr +" " + lowerStr +");");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 删除");
            sb.append(newLine);
            sb.append(tab);
            sb.append("int delete" + uppperStr +"(String id);");
            sb.append(newLine2);
            sb.append("}");
            fw.write(sb.toString().getBytes());

            result = "success!!!";
        } catch (IOException e) {
            e.printStackTrace();
            result = "error!!!";
        }
        return result;
    }


    public  String generatorService(String packagePath, String filename) {
        String result = "";
        try {
            lowerStr = CharUtils.toLowerCaseFirstOne(filename);
            uppperStr = CharUtils.toUpperCaseFirstOne(filename);
            File temp = new File("");
            pathVariable = temp.getAbsolutePath()+modelName+
                    "/src/main/java/"+ packagePath.replace(".", "/")
                    + "/service/" +filename + serviceSuffix;
            File file = new File(pathVariable);

            fw = new FileOutputStream(file);
            sb = new StringBuffer();
            sb.append("package " + packagePath +".service;");
            sb.append(newLine2);
            sb.append("import com.ypc.common.utils.sql.MyPageInfo;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.html.UpdateReturnJson;");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo." + filename + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.query." + filename + "Query" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.view." + filename + "View" + ";");
            sb.append(newLine2);
            sb.append("public interface " + filename +"Service {");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 添加");
            sb.append(newLine);
            sb.append(tab);
            sb.append("UpdateReturnJson add(" + uppperStr +" "+ lowerStr +");");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 根据id查找");
            sb.append(newLine);
            sb.append(tab);
            sb.append(uppperStr +"View getById(String id);");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 修改");
            sb.append(newLine);
            sb.append(tab);
            sb.append("UpdateReturnJson update(" + uppperStr +" " + lowerStr +");");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 删除");
            sb.append(newLine);
            sb.append(tab);
            sb.append("UpdateReturnJson delete(String id);");
            sb.append(newLine2);
            sb.append(tab);

            sb.append("//编辑");
            sb.append(newLine);
            sb.append(tab);
            sb.append("UpdateReturnJson edit("+ uppperStr +"View "+lowerStr + "View" +
                    ");");
            sb.append(newLine2);
            sb.append(tab);

            sb.append("// 获取table列表数据");
            sb.append(newLine);
            sb.append(tab);
            sb.append("MyPageInfo<" + uppperStr +"View> getList(" + uppperStr +"Query "+lowerStr+ "Query);");

            sb.append(newLine2);
            sb.append("}");
            fw.write(sb.toString().getBytes());

            result = "success!!!";
        } catch (IOException e) {
            e.printStackTrace();
            result = "error!!!";
        }
        return result;
    }

    public  String generatorServiceImpl(String packagePath, String filename) {
        String result = "";
        try {
            lowerStr = CharUtils.toLowerCaseFirstOne(filename);
            uppperStr = CharUtils.toUpperCaseFirstOne(filename);
            File temp = new File("");
            pathVariable = temp.getAbsolutePath()+modelName+
                    "/src/main/java/"+ packagePath.replace(".", "/")
                    + "/service/impl/" +filename + serviceImplSuffix;
            File file = new File(pathVariable);

            fw = new FileOutputStream(file);
            sb = new StringBuffer();
            sb.append("package " + packagePath +".service.impl;");
            sb.append(newLine2);
            sb.append("import com.github.pagehelper.PageHelper;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.BeanToolUtils;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.html.UpdateReturnJson;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.sql.MyPageInfo;");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo." + filename + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".dao." + filename + "Mapper" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.query." + filename + "Query" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.view." + filename + "View" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".service." +  filename + "Service" + ";");
            sb.append(newLine);
            sb.append("import org.springframework.beans.factory.annotation.Autowired;");
            sb.append(newLine);
            sb.append("import org.springframework.transaction.annotation.Transactional;");
            sb.append(newLine);
            sb.append("import org.springframework.stereotype.Service;");
            sb.append(newLine);
            sb.append("import org.springframework.util.StringUtils;");
            sb.append(newLine2);
            sb.append("@Service");
            sb.append(newLine);
            sb.append("public class " + uppperStr +"ServiceImpl implements " + uppperStr +"Service {");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@Autowired");
            sb.append(newLine);
            sb.append(tab);
            sb.append("private " + uppperStr +"Mapper " + lowerStr +"Mapper;");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("@Override");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@Transactional(rollbackFor = Exception.class)");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public UpdateReturnJson add(" + uppperStr +" " + lowerStr + ") {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("UpdateReturnJson returnJson = new UpdateReturnJson();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append(lowerStr + "Mapper.add" + uppperStr + "(" + lowerStr + ");");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("returnJson.setUpdateSuccess();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return returnJson;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("@Override");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public " + uppperStr + "View getById(String id) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append(uppperStr + "View "+ lowerStr + "View "+ " = " + lowerStr + "Mapper.get" + uppperStr + "ById(id);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return " + lowerStr + "View "+ ";");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("@Override");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@Transactional(rollbackFor = Exception.class)");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public UpdateReturnJson update(" + uppperStr + " " + lowerStr + ") {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("UpdateReturnJson returnJson = new UpdateReturnJson();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append(lowerStr + "Mapper.update" + uppperStr + "(" + lowerStr + ");");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("returnJson.setUpdateSuccess();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return returnJson;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("@Override");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@Transactional(rollbackFor = Exception.class)");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public UpdateReturnJson delete(String id) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("UpdateReturnJson returnJson = new UpdateReturnJson();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append(lowerStr + "Mapper.delete" + uppperStr + "(id);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("returnJson.setDeleteSuccess();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return returnJson;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine2);
            sb.append(tab);

            sb.append("@Override");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@Transactional(rollbackFor = Exception.class)");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public UpdateReturnJson edit("+uppperStr +"View "+lowerStr+"View) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("UpdateReturnJson updateReturnJson = new UpdateReturnJson();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("if("+lowerStr+"View == null) {");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("updateReturnJson.setUpdateError();");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("return updateReturnJson;");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");
            sb.append(newLine);
            sb.append(tab2);
            sb.append(uppperStr+" "+lowerStr+" = new "+uppperStr+"();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("BeanToolUtils.copyProperties("+lowerStr+"View "+","+lowerStr+");");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("if(StringUtils.isEmpty("+lowerStr+"View.getId())) {");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("this.add("+lowerStr+");");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}else {");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("this.update("+lowerStr+");");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("updateReturnJson.setUpdateSuccess();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return updateReturnJson;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");

            sb.append(newLine2);
            sb.append(tab);
            sb.append("@Override");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public MyPageInfo<" + uppperStr + "View> getList(" + uppperStr + "Query "+lowerStr+ "Query) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("if("+lowerStr+ "Query.getPageNum() != null && " +lowerStr+ "Query.getPageSize() != null){");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("PageHelper.startPage("+lowerStr+ "Query.getPageNum()," +lowerStr+"Query.getPageSize());");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("MyPageInfo<"+uppperStr+"View> myPageInfo = new MyPageInfo<>(" + lowerStr + "Mapper.get" + uppperStr + "List("+lowerStr+ "Query));");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return myPageInfo;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine);
            sb.append("}");
            fw.write(sb.toString().getBytes());
            result = "success!!!";
        } catch (IOException e) {
            e.printStackTrace();
            result = "error!!!";
        }
        return result;
    }


    public  String generatorController(String packagePath, String filename) {
        String result = "";
        try {

            File temp = new File("");
            pathVariable = temp.getAbsolutePath()+modelName+
                    "/src/main/java/"+ packagePath.replace(".", "/")
                    + "/controller/" +filename + controllerSuffix;
            File file = new File(pathVariable);

            fw = new FileOutputStream(file);
            sb = new StringBuffer();
            lowerStr = CharUtils.toLowerCaseFirstOne(filename);
            uppperStr = CharUtils.toUpperCaseFirstOne(filename);
            sb.append("package " + packagePath +".controller;");
            sb.append(newLine2);
            sb.append("import com.alibaba.fastjson.JSON;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.sql.MyPageInfo;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.html.UpdateReturnJson;");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.query." + filename + "Query" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.view." + filename + "View" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".service." +  filename + "Service" + ";");
            sb.append(newLine);
            sb.append("import org.springframework.beans.factory.annotation.Autowired;");
            sb.append(newLine);
            sb.append("import org.springframework.stereotype.Controller;");
            sb.append(newLine);
            sb.append("import org.springframework.web.bind.annotation.RequestMapping;");
            sb.append(newLine);
            sb.append("import org.springframework.web.bind.annotation.ResponseBody;");
            sb.append(newLine);
            sb.append("import org.springframework.web.context.request.WebRequest;");
            sb.append(newLine2);
            sb.append("@Controller");
            sb.append(newLine);
            String newStr = packagePath.replace("com.jwd.", "");
            String s = CharUtils.dotTransfer(newStr);
            s = "/" + s;
            sb.append("@RequestMapping(\"" + s + "/" + lowerStr + "\") ");
            sb.append(newLine);
            sb.append("public class " + uppperStr +"Controller {");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@Autowired");
            sb.append(newLine);
            sb.append(tab);
            sb.append("private " + uppperStr +"Service " + lowerStr +"Service;");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("@RequestMapping(\"/list\")");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@ResponseBody");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public MyPageInfo<" + uppperStr + "View> getList(WebRequest request, " + uppperStr + "Query "+lowerStr+ "Query) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("MyPageInfo<"+uppperStr+"View> myPageInfo =" + lowerStr + "Service.getList("+lowerStr+ "Query);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return myPageInfo;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");


            sb.append(newLine2);
            sb.append(tab);
            sb.append("//编辑");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@RequestMapping(\"/edit\")");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@ResponseBody");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public UpdateReturnJson edit(WebRequest request, String data) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("UpdateReturnJson returnJson = new UpdateReturnJson();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append(uppperStr + "View " + lowerStr + "View = JSON.parseObject(data, " + uppperStr + "View.class);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("returnJson = " + lowerStr + "Service.edit(" + lowerStr + "View);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return returnJson;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");

            sb.append(newLine);
            sb.append(newLine);
            sb.append(tab);
            sb.append("// 刪除");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@RequestMapping(\"/del\")");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@ResponseBody");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public UpdateReturnJson del(String id){");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("UpdateReturnJson dmo = "+lowerStr + "Service.delete(id);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return dmo;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 通过id获取");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@RequestMapping(\"/get\")");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@ResponseBody");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public "+ uppperStr +"View getById(WebRequest request, String id) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return "+ lowerStr +"Service.getById(id);");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine2);
            sb.append("}");
            fw.write(sb.toString().getBytes());
            result = "success!!!";
        } catch (IOException e) {
            e.printStackTrace();
            result = "error!!!";
        }
        return result;
    }

}

