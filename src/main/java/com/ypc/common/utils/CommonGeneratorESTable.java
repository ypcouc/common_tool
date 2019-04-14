package com.ypc.common.utils;

/**
 * Created by dodo on 2017/9/2.
 */

import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;

/** 文件生成器*/
public class CommonGeneratorESTable {

    private static final String mapperSuffix = "Repository.java";
    private static final String serviceSuffix  = "Service.java";
    private static final String serviceImplSuffix  = "ServiceImpl.java";
    private static final String controllerSuffix  = "Controller.java";
    private static final String jsSuffix = ".js";
    private static final String newLine = "\r\n";
    private static final String newLine2 = "\r\n\r\n";
    private static final String newLine3 = "\r\n\r\n\r\n";
    private static final String tab = "    ";
    private static final String tab2 = "        ";
    private static final String tab3 = "            ";
    private static final String tab4 = "                ";
    private static final String tab5 = "                    ";
    private static FileOutputStream fw = null;
    private static StringBuffer sb = null;
    private static String lowerStr = null;
    private static String uppperStr = null;
    private static String pathVariable;
    private int size;//列总数
    private String[] fieldNames; // 属性名
    private String[] fieldTypes; //属性类型
    private String index; //索引
    private String type; //类型

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void generatorAll(String packagePath, String filename, String moduleName, Object obj){
        if(StringUtils.isEmpty(packagePath) || StringUtils.isEmpty(filename)){
            System.out.println("参数为空");
            return;
        }
        //CommonGeneratorES.genApiJs(packagePath,filename);
        //CommonGeneratorES.getVuePage(packagePath,filename);
        this.initFieldInfo(obj);
        this.genQuery(packagePath,filename,moduleName);
        this.genView(packagePath,filename,moduleName);
        this.generatorController(packagePath,filename,moduleName);
        this.generatorService(packagePath,filename,moduleName);
        this.generatorServiceImpl(packagePath,filename,moduleName);
        this.generatorRepository(packagePath,filename,moduleName);

        System.out.println("代码生成成功");
    }

    public void initFieldInfo(Object obj){
        Field[] fields=obj.getClass().getDeclaredFields();
        size = fields.length;
        fieldNames = new String[size]; //列
        fieldTypes = new String[size];//列类型
        for (int i = 0; i < size; i++) {
            fieldNames[i] = fields[i].getName();
            String typeStr = fields[i].getType().toString();
            fieldTypes[i] = typeStr.substring(typeStr.lastIndexOf(".")+1);
        }
    }
    /**
     *
     * @param packagePath
     * @param filename 文件名（大写）例如：UserInfo
     * @return
     */
    public String generatorRepository(String packagePath, String filename,String moduleName) {
        String result = "";
        try {

            sb = new StringBuffer();
            lowerStr = CharUtils.toLowerCaseFirstOne(filename);
            uppperStr = CharUtils.toUpperCaseFirstOne(filename);
            sb.append("package " + packagePath +".dao;");
            sb.append(newLine2);
            sb.append("import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo." + filename + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.view." + filename + "View" + ";");
            sb.append(newLine);

            sb.append("public interface " + filename +"Repository " +
                    "extends ElasticsearchRepository<" +
                    filename +
                    ",String> {");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("// 根据id查询");
            sb.append(newLine);
            sb.append(tab);
            sb.append(uppperStr + "View " + "query" + uppperStr +
                    "By" + uppperStr + "TableAnd" + uppperStr +"Id" +
                    "(String " + lowerStr + "Table," + "String " + lowerStr +"Id" +
                    ");");
            sb.append(newLine2);
            sb.append("}");

            File temp = new File("");

            pathVariable = temp.getAbsolutePath()+
                    "/src/main/java/"+ packagePath.replace(".", "/")
                    + "/dao/" +filename + mapperSuffix;
            if(!StringUtils.isEmpty(moduleName)){
                pathVariable = temp.getAbsolutePath()+"/"+moduleName+
                        "/src/main/java/"+ packagePath.replace(".", "/")
                        + "/dao/" +filename + mapperSuffix;
            }
            File file = new File(pathVariable);
            fw = new FileOutputStream(file);
            fw.write(sb.toString().getBytes());
            fw.flush();
            fw.close();

            result = "success!!!";
        } catch (IOException e) {
            e.printStackTrace();
            result = "error!!!";
        }
        return result;
    }


    public String generatorService(String packagePath, String filename, String moduleName) {
        String result = "";
        try {
            lowerStr = CharUtils.toLowerCaseFirstOne(filename);
            uppperStr = CharUtils.toUpperCaseFirstOne(filename);

            sb = new StringBuffer();
            sb.append("package " + packagePath +".service;");
            sb.append(newLine2);

            sb.append("import com.ypc.common.utils.html.UpdateReturnJson;");
            sb.append(newLine);
            sb.append("import org.springframework.data.domain.Page;");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo." + filename + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.query." + filename + "Query" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.view." + filename + "View" + ";");
            sb.append(newLine2);
            sb.append("import java.util.List;");
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
            sb.append(uppperStr +"View getById(String " + lowerStr +"Id"+
                    ");");
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
            sb.append("UpdateReturnJson delete(String " + lowerStr +"Id"+
                    ");");
            sb.append(newLine2);
            sb.append(tab);
            sb.append("//获取table列表数据");
            sb.append(newLine);
            sb.append(tab);
            sb.append("Page<" + uppperStr +"View> getList(" + uppperStr + "Query " +
                            lowerStr + "Query" +
                    ");");
            sb.append(newLine);
            sb.append(tab);

            sb.append(newLine2);
            sb.append(tab);
            sb.append("//查询list");
            sb.append(newLine);
            sb.append(tab);
            sb.append("List<"+ uppperStr +"View> get"+uppperStr +"ViewList(" + uppperStr + "Query " +
                    lowerStr + "Query" +
                    ");");

            sb.append(newLine2);
            sb.append(tab);
            sb.append("//编辑");
            sb.append(newLine);
            sb.append(tab);
            sb.append("UpdateReturnJson edit("+ uppperStr +"View "+lowerStr + "View" +
                    ");");

            sb.append(newLine2);
            sb.append("}");

            File temp = new File("");
            pathVariable = temp.getAbsolutePath()+
                    "/src/main/java/"+ packagePath.replace(".", "/")
                    + "/service/" +filename + serviceSuffix;
            if(!StringUtils.isEmpty(moduleName)){
                pathVariable = temp.getAbsolutePath()+"/"+moduleName+
                        "/src/main/java/"+ packagePath.replace(".", "/")
                        + "/service/" +filename + serviceSuffix;
            }
            File file = new File(pathVariable);

            fw = new FileOutputStream(file);
            fw.write(sb.toString().getBytes());
            fw.flush();
            fw.close();

            result = "success!!!";
        } catch (IOException e) {
            e.printStackTrace();
            result = "error!!!";
        }
        return result;
    }

    public String generatorServiceImpl(String packagePath, String filename, String moduleName) {
        String result = "";
        try {
            lowerStr = CharUtils.toLowerCaseFirstOne(filename);
            uppperStr = CharUtils.toUpperCaseFirstOne(filename);

            sb = new StringBuffer();
            sb.append("package " + packagePath +".service.impl;");
            sb.append(newLine2);

            sb.append("import com.ypc.common.utils.BeanToolUtils;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.UuidUtils;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.DateUtils;");
            sb.append(newLine);
            sb.append("import com.jwd.common.constants.MyConstants;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.html.UpdateReturnJson;");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo." + filename + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".dao." + filename + "Repository" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.query." + filename + "Query" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo.view." + filename + "View" + ";");
            sb.append(newLine);
            sb.append("import " + packagePath + ".service." +  filename + "Service" + ";");
            sb.append(newLine);
            sb.append("import org.elasticsearch.index.query.BoolQueryBuilder;");
            sb.append(newLine);
            sb.append("import org.elasticsearch.index.query.QueryBuilder;");
            sb.append(newLine);
            sb.append("import org.elasticsearch.index.query.QueryBuilders;");
            sb.append(newLine);
            sb.append("import org.springframework.data.domain.Page;");
            sb.append(newLine);
            sb.append("import org.springframework.data.domain.PageRequest;");
            sb.append(newLine);
            sb.append("import org.springframework.data.domain.Pageable;");
            sb.append(newLine);
            sb.append("import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;");
            sb.append(newLine);
            sb.append("import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;");
            sb.append(newLine);
            sb.append("import org.springframework.data.elasticsearch.core.query.SearchQuery;");
            sb.append(newLine);
            sb.append("import org.springframework.beans.factory.annotation.Autowired;");
            sb.append(newLine);
            sb.append("import org.springframework.stereotype.Service;");
            sb.append(newLine);
            sb.append("import org.springframework.util.CollectionUtils;");
            sb.append(newLine);
            sb.append("import org.springframework.util.StringUtils;");
            sb.append(newLine);
            sb.append("import java.util.List;");
            sb.append(newLine2);
            sb.append("@Service");
            sb.append(newLine);
            sb.append("public class " + uppperStr +"ServiceImpl implements " + uppperStr +"Service {");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@Autowired");
            sb.append(newLine);
            sb.append(tab);
            sb.append("private " + uppperStr +"Repository " + lowerStr +"Repository;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@Autowired");
            sb.append(newLine);
            sb.append(tab);
            sb.append("private ElasticsearchTemplate elasticsearchTemplate;");
            sb.append(newLine2);
            sb.append(tab);

            sb.append("@Override");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public UpdateReturnJson add(" + uppperStr +" " + lowerStr + ") {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("UpdateReturnJson returnJson = new UpdateReturnJson();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("try{");
            sb.append(newLine);
            sb.append(tab3);
            sb.append(lowerStr + ".set" + uppperStr + "Id" + "(UuidUtils.getId());");
            sb.append(newLine);
            sb.append(tab3);
            sb.append(lowerStr + ".set" + uppperStr + "Table(\"1\");");
            sb.append(newLine);
            sb.append(tab3);
            sb.append(lowerStr + ".setCreateTime(DateUtils.getSystemDateForString(\"yyyy-MM-dd HH:mm:ss\"));");
            sb.append(newLine);
            sb.append(tab3);
            sb.append(lowerStr + "Repository.save(" + lowerStr + ");") ;
            sb.append(newLine);
            sb.append(tab3);
            sb.append("returnJson.setUpdateSuccess();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}catch(Exception e){");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("e.printStackTrace();");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("returnJson.setUpdateError();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");
            sb.append(newLine2);
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
            sb.append("public " + uppperStr + "View getById(String " + lowerStr +
                    "Id) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append(uppperStr + "View "+ lowerStr + "View" +" = " + lowerStr + "Repository." +
                    "query" + uppperStr + "By" + uppperStr + "TableAnd" + uppperStr +"Id"  +
                    "(\"1\","  + lowerStr +"Id" + ");");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return " + lowerStr + "View" + ";");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine2);
            sb.append(tab);

            sb.append("@Override");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public UpdateReturnJson update(" + uppperStr + " " + lowerStr + ") {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("UpdateReturnJson returnJson = new UpdateReturnJson();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("try{");
            sb.append(newLine);
            sb.append(tab3);


            sb.append("if(" + lowerStr + " == " + "null ||");
            sb.append(newLine);
            sb.append(tab5);
            sb.append("StringUtils.isEmpty(" + lowerStr +".get" +uppperStr+
                    "Id())){");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("returnJson.setUpdateError();");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("return returnJson;");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("}");
            sb.append(newLine);
            sb.append(tab3);

            sb.append(uppperStr+"View "+lowerStr+"View = this.getById("+
            lowerStr+".get" +uppperStr+ "Id());");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("if(" + lowerStr+"View != null){");
            sb.append(newLine);
            sb.append(tab4);
            sb.append(uppperStr+" "+lowerStr+"1 = " + "new " + uppperStr + "();");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("BeanToolUtils.copyProperties("+ lowerStr+"View,"+lowerStr+"1);");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("BeanToolUtils.copyPropertiesIgnoreNull("+ lowerStr+","+lowerStr+"1,null);");
            sb.append(newLine);
            sb.append(tab4);
            sb.append(lowerStr+"1.setUpdateTime(DateUtils.getSystemDateForString(\"yyyy-MM-dd HH:mm:ss\"));");
            sb.append(newLine);
            sb.append(tab4);
            sb.append(lowerStr + "Repository.save("+lowerStr+"1);");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("}else {");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("returnJson.setUpdateError();");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("return returnJson;");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("}");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("returnJson.setUpdateSuccess();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}catch(Exception e){");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("e.printStackTrace();");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("returnJson.setUpdateError();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");
            sb.append(newLine2);
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
            sb.append("public UpdateReturnJson delete(String " +  lowerStr + "Id) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("UpdateReturnJson returnJson = new UpdateReturnJson();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("try{");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("if(StringUtils.isEmpty(" + lowerStr + "Id)){");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("returnJson.setDeleteError();");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("return returnJson;");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("}");
            sb.append(newLine);
            sb.append(tab3);
            sb.append(uppperStr+"View "+lowerStr+"View = this.getById("+lowerStr + "Id);");
            sb.append(newLine);
            sb.append(tab3);
            sb.append(uppperStr+" "+ lowerStr+" = new " + uppperStr+"();");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("BeanToolUtils.copyProperties(" +lowerStr+"View,"+lowerStr+");");
            sb.append(newLine);
            sb.append(tab3);
            sb.append(lowerStr+"Repository.delete("+lowerStr+");");
            sb.append(newLine);
            sb.append(tab3);

            sb.append("returnJson.setDeleteSuccess();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}catch(Exception e){");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("e.printStackTrace();");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("returnJson.setDeleteError();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");
            sb.append(newLine2);
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
            sb.append("public Page<" + uppperStr + "View> getList(" + uppperStr + "Query "+ lowerStr+"Query) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("Integer page = "+ lowerStr+"Query.getPageNum();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("if (page != null && page.intValue()>0){");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("page = page - 1;");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("Integer pageSize = "+ lowerStr+"Query.getPageSize();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("SearchQuery searchQuery = null;");
            sb.append(newLine);
            sb.append(tab2);
            /*sb.append("BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("QueryBuilder query = boolQueryBuilder;");*/
            sb.append("QueryBuilder query = this.getQueryCondition("+lowerStr+"Query);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("if(page != null && pageSize != null){");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("Pageable pageable = PageRequest.of(page,pageSize);");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("searchQuery = new NativeSearchQueryBuilder().withQuery(query).withIndices(" +this.index+
                    ").withPageable(pageable).build();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}else{");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("searchQuery = new NativeSearchQueryBuilder().withQuery(query).withIndices(" +this.index+
                    ").build();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("Page<"+uppperStr+"View> "+lowerStr+"Views = " +
                    "elasticsearchTemplate.queryForPage(searchQuery,"+uppperStr+"View.class);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return " +lowerStr+"Views;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine2);
            sb.append(tab);

            sb.append("@Override");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public List<"+ uppperStr +"View> get"+uppperStr +"ViewList(" + uppperStr + "Query " +
                    lowerStr + "Query" +
                            "){");
            sb.append(newLine);
            sb.append(tab2);
            /*sb.append("BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("QueryBuilder query = boolQueryBuilder;");*/
            sb.append("QueryBuilder query = this.getQueryCondition("+lowerStr+"Query);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).withIndices(" +this.index+
                    ").withPageable(PageRequest.of(0,10000-1)).build();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("List<"+uppperStr+"View> list = " +
                    " elasticsearchTemplate.queryForList(searchQuery,"+uppperStr+"View.class);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return list;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine2);
            sb.append(tab);

            sb.append("@Override");
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
            sb.append("try{");
            sb.append(newLine);
            sb.append(tab3);
            sb.append(uppperStr+" "+lowerStr+" = new "+uppperStr+"();");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("BeanToolUtils.copyProperties("+lowerStr+"View "+","+lowerStr+");");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("if(StringUtils.isEmpty("+lowerStr+"View.get" +uppperStr+ "Id())) {");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("this.add("+lowerStr+");");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("}else {");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("this.update("+lowerStr+");");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("}");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("updateReturnJson.setUpdateSuccess();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}catch(Exception e){");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("e.printStackTrace();");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("updateReturnJson.setDeleteError();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");
            sb.append(newLine2);
            sb.append(tab2);
            sb.append("return updateReturnJson;");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");


            sb.append(newLine2);
            sb.append(tab);
            //生成公用查询条件
            sb.append("private BoolQueryBuilder getQueryCondition(" + uppperStr + "Query " +
                    lowerStr + "Query" +
                    "){");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("boolQueryBuilder.must(QueryBuilders.termQuery(\"" +lowerStr+
                    "Table.keyword\",\"1\"));");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("if("+lowerStr + "Query"+" == null){");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("return boolQueryBuilder;");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("if("+lowerStr + "Query"+" != null){");
            sb.append(newLine);
            sb.append(tab3);

            sb.append(newLine);
            sb.append(tab3);
            sb.append("if(StringUtils.hasText("+lowerStr + "Query"+"." + "getCompanyId())){");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("boolQueryBuilder.must(QueryBuilders.termQuery(\"companyId.keyword\","+lowerStr + "Query"+"." + "getCompanyId()));");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("}");

            sb.append(newLine);
            sb.append(tab3);
            sb.append("if(StringUtils.hasText("+lowerStr + "Query"+"." + "getFactoryId())){");
            sb.append(newLine);
            sb.append(tab4);
            sb.append("boolQueryBuilder.must(QueryBuilders.termQuery(\"factoryId.keyword\","+lowerStr + "Query"+"." + "getFactoryId()));");
            sb.append(newLine);
            sb.append(tab3);
            sb.append("}");
            for (int i = 0; i < size; i++) {
                if (fieldNames[i].endsWith("Id")) {
                    sb.append(newLine);
                    sb.append(tab3);
                    if (fieldTypes[i].equals("String")){
                        sb.append("if(StringUtils.hasText("+lowerStr + "Query"+"." + "get" + initcap(fieldNames[i])+"())){");
                    }else if (fieldTypes[i].equals("Integer")){
                        sb.append("if("+lowerStr + "Query"+".getResourceType()!=null){");
                    }
                    sb.append(newLine);
                    sb.append(tab4);
                    sb.append("boolQueryBuilder.must(QueryBuilders.termQuery(\""+fieldNames[i]+".keyword\","+lowerStr + "Query"+"." + "get" + initcap(fieldNames[i])+"()));");
                    sb.append(newLine);
                    sb.append(tab3);
                    sb.append("}");
                }
            }

            sb.append(newLine);
            sb.append(tab2);
            sb.append("}");

            sb.append(newLine);
            sb.append(tab2);
            sb.append("return boolQueryBuilder;");

            sb.append(newLine);
            sb.append(tab);
            sb.append("}"); //方法结束

            sb.append(newLine2);
            sb.append("}");

            File temp = new File("");
            pathVariable = temp.getAbsolutePath()+
                    "/src/main/java/"+ packagePath.replace(".", "/")
                    + "/service/impl/" +filename + serviceImplSuffix;
            if(!StringUtils.isEmpty(moduleName)){
                pathVariable = temp.getAbsolutePath()+"/"+moduleName+
                        "/src/main/java/"+ packagePath.replace(".", "/")
                        + "/service/impl/" +filename + serviceImplSuffix;
            }
            File file = new File(pathVariable);

            fw = new FileOutputStream(file);
            fw.write(sb.toString().getBytes());
            fw.flush();
            fw.close();
            result = "success!!!";
        } catch (IOException e) {
            e.printStackTrace();
            result = "error!!!";
        }
        return result;
    }


    public String generatorController(String packagePath, String filename, String moduleName) {
        String result = "";
        try {

            sb = new StringBuffer();
            lowerStr = CharUtils.toLowerCaseFirstOne(filename);
            uppperStr = CharUtils.toUpperCaseFirstOne(filename);
            sb.append("package " + packagePath +".controller;");
            sb.append(newLine2);
            sb.append("import com.alibaba.fastjson.JSON;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.html.DataTablesParam;");
            sb.append(newLine);
            /*sb.append("import com.ypc.common.utils.html.DataTablesReturnJSON;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.html.DataTablesReturnObj;");*/
            sb.append("import com.ypc.common.utils.sql.MyPageInfo;");
            sb.append(newLine);
            sb.append("import org.springframework.util.StringUtils;");
            sb.append(newLine);
            sb.append("import org.springframework.data.domain.Page;");
            sb.append(newLine);
            sb.append("import com.ypc.common.utils.html.UpdateReturnJson;");
            sb.append(newLine);
            sb.append("import " + packagePath + ".pojo." + filename + ";");
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
            sb.append(newLine);
            sb.append("import java.util.List;");
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
            sb.append("//分页查询");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@RequestMapping(\"/list\")");
            sb.append(newLine);
            sb.append(tab);
            sb.append("@ResponseBody");
            sb.append(newLine);
            sb.append(tab);
            sb.append("public MyPageInfo<"+uppperStr+"View> list(WebRequest request,"+uppperStr+"Query " +
            lowerStr+"Query){");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("Page<"+uppperStr+"View> "+lowerStr+"Views = "+
                    lowerStr+"Service.getList("+lowerStr+"Query);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("MyPageInfo<"+uppperStr+"View> returnJson = new MyPageInfo("+lowerStr+"Views);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return returnJson;");
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
            sb.append("UpdateReturnJson returnJson="+lowerStr + "Service.delete(id);");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return returnJson;");
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
            sb.append("public "+ uppperStr +"View getById(WebRequest request, String " +
                    lowerStr +"Id) {");
            sb.append(newLine);
            sb.append(tab2);
            sb.append("return "+ lowerStr +"Service.getById(" + lowerStr+
                    "Id);");
            sb.append(newLine);
            sb.append(tab);
            sb.append("}");
            sb.append(newLine2);
            sb.append("}");

            File temp = new File("");
            pathVariable = temp.getAbsolutePath()+
                    "/src/main/java/"+ packagePath.replace(".", "/")
                    + "/controller/" +filename + controllerSuffix;
            if(!StringUtils.isEmpty(moduleName)){
                pathVariable = temp.getAbsolutePath()+"/"+moduleName+
                        "/src/main/java/"+ packagePath.replace(".", "/")
                        + "/controller/" +filename + controllerSuffix;
            }
            File file = new File(pathVariable);
            fw = new FileOutputStream(file);
            fw.write(sb.toString().getBytes());
            fw.flush();
            fw.close();
            result = "success!!!";
        } catch (IOException e) {
            e.printStackTrace();
            result = "error!!!";
        }
        return result;
    }


    /**
     * 生成view
     */
    private void genView(String packagePath,String className,String moduleName) {
        StringBuffer sb = new StringBuffer();
        lowerStr = CharUtils.toLowerCaseFirstOne(className);
        uppperStr = CharUtils.toUpperCaseFirstOne(className);
        sb.append("package " + packagePath + ".pojo.view;");
        sb.append(newLine3);
        //导包
        sb.append("import " + packagePath + ".pojo." + uppperStr + ";");
        sb.append(newLine2);
        //注释部分
        sb.append("/**" + newLine);
        sb.append(" * " + className + " view展示类" + newLine);
        sb.append(" * " + DateUtils.getSystemDateForString("yyyy-MM-dd HH:mm:ss.SSS") + " "  + newLine);
        sb.append(" */ " + newLine2);
        sb.append("public class " + uppperStr + "View extends "
                + uppperStr + " {");
        sb.append(newLine2);
        sb.append("}");
        sb.append(newLine2);

        String content = sb.toString();
        try {
            File directory = new File("");
            String outputPath = directory.getAbsolutePath() + "/src/main/java/" + packagePath.replace(".", "/") + "/pojo/view/" + className + "View.java";
            if(!StringUtils.isEmpty(moduleName)){
                outputPath = directory.getAbsolutePath() +"/"+moduleName+"/src/main/java/" + packagePath.replace(".", "/") + "/pojo/view/" + className + "View.java";
            }
            System.out.println(outputPath);
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成query
     */
    private void genQuery(String packagePath,String className,String moduleName) {
        StringBuffer sb = new StringBuffer();
        lowerStr = CharUtils.toLowerCaseFirstOne(className);
        uppperStr = CharUtils.toUpperCaseFirstOne(className);
        sb.append("package " + packagePath+ ".pojo.query;");
        sb.append(newLine3);

        sb.append("import com.ypc.common.pojo.query.CommonQuery;");
        sb.append(newLine2);

        //注释部分
        sb.append("/**" + newLine);
        sb.append(" * " + uppperStr + " query查询" + newLine);
        sb.append(" * " + DateUtils.getSystemDateForString("yyyy-MM-dd HH:mm:ss.SSS")  + newLine);
        sb.append(" */ " + newLine2);
        sb.append("public class " + className + "Query extends CommonQuery {");
        sb.append(newLine2);

        processQueryAttrs(sb);
        processQueryMethod(sb);

        sb.append("}");
        sb.append(newLine2);

        String content = sb.toString();
        try {
            File directory = new File("");
            String outputPath = directory.getAbsolutePath() +"/src/main/java/" + packagePath.replace(".", "/") + "/pojo/query/" + className + "Query.java";;
            if(!StringUtils.isEmpty(moduleName)){
                outputPath = directory.getAbsolutePath() +"/"+moduleName+"/src/main/java/" + packagePath.replace(".", "/") + "/pojo/query/" + className + "Query.java";
            }
            System.out.println(outputPath);
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能：生成查询所有属性
     *
     * @param sb
     */
    private void processQueryAttrs(StringBuffer sb) {

        for (int i = 0; i < size; i++) {
            if (fieldNames[i].endsWith("Id")) {
                sb.append(tab);
                sb.append("/** " + fieldNames[i] + "*/");
                sb.append(newLine);
                sb.append(tab);
                sb.append("private " + fieldTypes[i] + " " + fieldNames[i] + ";");
                sb.append(newLine2);
            }
        }

    }

    /**
     * 功能：生成所有方法
     *
     * @param sb
     */
    private void processQueryMethod(StringBuffer sb) {

        for (int i = 0; i < size; i++) {
            if (fieldNames[i].endsWith("Id")) {
                sb.append(tab + "public void set" + initcap(fieldNames[i]) + "(" + fieldTypes[i] + " " +
                        fieldNames[i] + "){" + newLine);
                sb.append(tab2 + "this." + fieldNames[i] + " = " + fieldNames[i] + ";" + newLine);
                sb.append(tab + "}" + newLine);
                sb.append(tab + "public " + fieldTypes[i] + " get" + initcap(fieldNames[i]) + "(){" + newLine);
                sb.append(tab2 + "return " + fieldNames[i] + ";" + newLine);
                sb.append(tab + "}" + newLine);
            }
        }

    }

    /**
     * 生成api.js
     */
    private static void genApiJs(String packagePath,String filename) {
        StringBuffer sb = new StringBuffer();
        lowerStr = CharUtils.toLowerCaseFirstOne(filename);
        uppperStr = CharUtils.toUpperCaseFirstOne(filename);
        String newStr = packagePath.replace("com.jwd.", "");
        String s = CharUtils.dotTransfer(newStr);
        s = "/" + s;
        sb.append("const apiURL = window.config.api;");
        sb.append(newLine);
        sb.append("export default {");
        sb.append(newLine);
        sb.append("\t"+"get"+uppperStr+"List"+": `${apiURL}" + s + "/" + lowerStr + "/list`,");
        sb.append(newLine);
        sb.append("\t"+"edit"+uppperStr+": `${apiURL}" + s + "/" + lowerStr + "/edit`,");
        sb.append(newLine);
        sb.append("\t"+"del"+uppperStr+": `${apiURL}" + s + "/" + lowerStr + "/del`,");
        sb.append(newLine);
        sb.append("\t"+"get"+uppperStr+"By"+lowerStr+"Id: `${apiURL}" + s + "/" + lowerStr + "/get`,");
        sb.append(newLine);
        sb.append("}");
        String content = sb.toString();
        try {
            File directory = new File("");
            String outputPath = directory.getAbsolutePath()  +"/" + "Api.js";
            System.out.println(outputPath);
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成vuePage
     */
    private static void getVuePage(String packagePath,String filename) {
        StringBuffer sb = new StringBuffer();
        lowerStr = CharUtils.toLowerCaseFirstOne(filename);
        uppperStr = CharUtils.toUpperCaseFirstOne(filename);
        String content = sb.toString();
        sb.append("<template>");
        sb.append("  <el-row>");
        sb.append("  </el-row>");
        sb.append("</template>");
        sb.append("<script>");
        sb.append("</script>");
        sb.append("<style>");
        sb.append("</style>");
        try {
            File directory = new File("");
            String outputPath = directory.getAbsolutePath() +"/"+uppperStr+".vue";
            System.out.println(outputPath);
            FileWriter fw = new FileWriter(outputPath);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    public String initcap(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }


}

