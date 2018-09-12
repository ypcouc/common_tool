package com.ypc.doclever;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ypc.utils.MongodbUtils;
import com.ypc.utils.ReflectionUtils;
import com.ypc.utils.UuidUtils;
import org.bson.types.ObjectId;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GenerateDocLeverAPI {
    /**
     * 获取get请求api
     * @param clazzView 返回类
     * @param clazzQuery 查询参数类
     * @param context 接口名称
     * @param url 接口路径
     */
    public static JSONObject getApiDOCleverJsonGet(Class<?> clazzView,Class<?> clazzQuery,String context,String url,
            Class<?> genericClass){
        if(clazzView == null || clazzQuery == null
                || StringUtils.isEmpty(url)
                || StringUtils.isEmpty(context)){
            return null;
        }
        JSONObject listOutObj = new JSONObject();
        listOutObj.put("flag","SBDoc");
        listOutObj.put("finish",0);
        listOutObj.put("sort",0);
        listOutObj.put("name",context);
        listOutObj.put("url",url);
        listOutObj.put("remark",context);
        listOutObj.put("method","GET");
        listOutObj.put("createdAt",new Date());
        listOutObj.put("updatedAt",new Date());

        JSONArray listParamArry = new JSONArray();
        JSONObject listParamObj = new JSONObject();
        listParamObj.put("name","参数");
        listParamObj.put("id", UuidUtils.getId());
        listParamObj.put("remark","");
        JSONObject listBefore = new JSONObject();
        listBefore.put("mode",0);
        listBefore.put("code","");
        listParamObj.put("before",listBefore);
        JSONObject listAfter = new JSONObject();
        listAfter.put("mode",0);
        listAfter.put("code","");
        listParamObj.put("after",listAfter);
        listParamObj.put("header",new JSONArray());
        listParamObj.put("restParam",new JSONArray());
        JSONObject listOutInfoObj = new JSONObject();
        listOutInfoObj.put("type",0);
        listOutInfoObj.put("rawRemark","");
        listOutInfoObj.put("rawMock","");
        listOutInfoObj.put("jsonType",0);
        listParamObj.put("outInfo",listOutInfoObj);

        //查询参数
        JSONArray listQueryParamArry = new JSONArray();
        JSONObject propertyRemark = getPropertyRemark(clazzQuery);
        if (propertyRemark != null) {
            for (Map.Entry<String,Object> entry: propertyRemark.entrySet()) {
                JSONObject queryParamObj = new JSONObject();
                queryParamObj.put("name",entry.getKey());
                queryParamObj.put("must",0);
                queryParamObj.put("remark",entry.getValue());
                listQueryParamArry.add(queryParamObj);
            }
        }
        listParamObj.put("queryParam",listQueryParamArry);

        //返回参数
        JSONObject outPropertyRemark = getPropertyRemark(clazzView);
        JSONArray outParamArry = getParamObj(clazzView,outPropertyRemark,genericClass);
        listParamObj.put("outParam",outParamArry);
        listParamArry.add(listParamObj);
        listOutObj.put("param",listParamArry);
        return listOutObj;

    }

    /**
     * 获取get请求api
     * @param clazzView 返回类
     * @param jsonObject 查询参数
     * @param context 接口名称
     * @param url 接口路径
     * @param genericClass
     * @return
     */
    public static JSONObject getApiDOCleverJsonGet(Class<?> clazzView,JSONObject jsonObject,String context,String url,
                                                   Class<?> genericClass){
        if(clazzView == null
                || StringUtils.isEmpty(url)
                || StringUtils.isEmpty(context)){
            return null;
        }
        JSONObject listOutObj = new JSONObject();
        listOutObj.put("flag","SBDoc");
        listOutObj.put("finish",0);
        listOutObj.put("sort",0);
        listOutObj.put("name",context);
        listOutObj.put("url",url);
        listOutObj.put("remark",context);
        listOutObj.put("method","GET");
        listOutObj.put("createdAt",new Date());
        listOutObj.put("updatedAt",new Date());

        JSONArray listParamArry = new JSONArray();
        JSONObject listParamObj = new JSONObject();
        listParamObj.put("name","参数");
        listParamObj.put("id",UuidUtils.getId());
        listParamObj.put("remark","");
        JSONObject listBefore = new JSONObject();
        listBefore.put("mode",0);
        listBefore.put("code","");
        listParamObj.put("before",listBefore);
        JSONObject listAfter = new JSONObject();
        listAfter.put("mode",0);
        listAfter.put("code","");
        listParamObj.put("after",listAfter);
        listParamObj.put("header",new JSONArray());
        listParamObj.put("restParam",new JSONArray());
        JSONObject listOutInfoObj = new JSONObject();
        listOutInfoObj.put("type",0);
        listOutInfoObj.put("rawRemark","");
        listOutInfoObj.put("rawMock","");
        listOutInfoObj.put("jsonType",0);
        listParamObj.put("outInfo",listOutInfoObj);

        //查询参数
        JSONArray listQueryParamArry = new JSONArray();
        if(jsonObject != null && !jsonObject.isEmpty()){
            for (Map.Entry<String,Object> entry: jsonObject.entrySet()) {
                JSONObject queryParamObj = new JSONObject();
                queryParamObj.put("name",entry.getKey());
                queryParamObj.put("must",1);
                queryParamObj.put("remark",entry.getValue());
                listQueryParamArry.add(queryParamObj);
            }
        }

        listParamObj.put("queryParam",listQueryParamArry);

        //返回参数
        JSONObject outPropertyRemark = getPropertyRemark(clazzView);
        JSONArray outParamArry = getParamObj(clazzView,outPropertyRemark,genericClass);
        listParamObj.put("outParam",outParamArry);
        listParamArry.add(listParamObj);
        listOutObj.put("param",listParamArry);
        return listOutObj;

    }

    /**
     * 获取post请求api
     * @param outClazz 返回类
     * @param inClazz 入参类
     * @param context 接口说明
     * @param url 接口路径
     * @return
     */
    public static JSONObject getApiDOCleverJsonPost(Class<?> outClazz,Class<?> inClazz,
                                                    String context,String url){
        if(outClazz == null || inClazz == null
        || StringUtils.isEmpty(context) || StringUtils.isEmpty(url)){
            return null;
        }
        JSONObject outObj = new JSONObject();
        outObj.put("flag","SBDoc");
        outObj.put("finish",0);
        outObj.put("sort",0);
        outObj.put("name",context);
        outObj.put("url",url);
        outObj.put("remark",context);
        outObj.put("method","POST");
        outObj.put("createdAt",new Date());
        outObj.put("updatedAt",new Date());

        JSONArray listParamArry = new JSONArray();
        JSONObject listParamObj = new JSONObject();
        listParamObj.put("name","参数");
        listParamObj.put("id",UuidUtils.getId());
        listParamObj.put("remark","");
        JSONObject listBefore = new JSONObject();
        listBefore.put("mode",0);
        listBefore.put("code","");
        listParamObj.put("before",listBefore);
        JSONObject listAfter = new JSONObject();
        listAfter.put("mode",0);
        listAfter.put("code","");
        listParamObj.put("after",listAfter);

        JSONArray headerArry = new JSONArray();
        JSONObject headerObj = new JSONObject();
        headerObj.put("name","Content-Type");
        headerObj.put("value","application/json");
        headerObj.put("remark","");
        JSONObject bodyInfoObj = new JSONObject();
        bodyInfoObj.put("type",0);
        bodyInfoObj.put("rawType",0);
        bodyInfoObj.put("rawTextRemark","");
        bodyInfoObj.put("rawFileRemark","");
        bodyInfoObj.put("rawText","");
        bodyInfoObj.put("rawJSON",new JSONArray());
        bodyInfoObj.put("rawJSONType",0);
        headerObj.put("bodyInfo",bodyInfoObj);
        headerArry.add(headerObj);
        listParamObj.put("header",headerArry);

        listParamObj.put("queryParam",new JSONArray());
        listParamObj.put("bodyParam",new JSONArray());

        JSONObject bodyInfoBody = new JSONObject();
        bodyInfoBody.put("type",1);
        bodyInfoBody.put("rawType",2);
        bodyInfoBody.put("rawTextRemark","");
        bodyInfoBody.put("rawFileRemark","");
        bodyInfoBody.put("rawText","");
        bodyInfoBody.put("rawJSONType",0);
        JSONObject propertyRemarkIn = getPropertyRemark(inClazz);
        JSONArray rawJSONArry = getInParamObjPost(inClazz,propertyRemarkIn);
        bodyInfoBody.put("rawJSON",rawJSONArry);
        listParamObj.put("bodyInfo",bodyInfoBody);
        //返回参数
        JSONObject outPropertyRemark = getPropertyRemark(outClazz);
        JSONArray outParamArry = getParamObj(outClazz,outPropertyRemark,null);
        listParamObj.put("outParam",outParamArry);

        JSONObject listOutInfoObj = new JSONObject();
        listOutInfoObj.put("type",0);
        listOutInfoObj.put("rawRemark","");
        listOutInfoObj.put("rawMock","");
        listOutInfoObj.put("jsonType",0);
        listParamObj.put("outInfo",listOutInfoObj);

        listParamObj.put("restParam",new JSONArray());

        listParamArry.add(listParamObj);
        outObj.put("param",listParamArry);

        return outObj;
    }

    /**
     * 获取post请求api
     * @param outClazz 返回类
     * @param jsonObject 入参
     * @param context 接口说明
     * @param url 接口路径
     * @return
     */
    public static JSONObject getApiDOCleverJsonPost(Class<?> outClazz,JSONObject jsonObject,
                                                    String context,String url){
        if(outClazz == null || jsonObject == null
                || StringUtils.isEmpty(context) || StringUtils.isEmpty(url)){
            return null;
        }
        JSONObject outObj = new JSONObject();
        outObj.put("flag","SBDoc");
        outObj.put("finish",0);
        outObj.put("sort",0);
        outObj.put("name",context);
        outObj.put("url",url);
        outObj.put("remark",context);
        outObj.put("method","POST");
        outObj.put("createdAt",new Date());
        outObj.put("updatedAt",new Date());

        JSONArray listParamArry = new JSONArray();
        JSONObject listParamObj = new JSONObject();
        listParamObj.put("name","参数");
        listParamObj.put("id",UuidUtils.getId());
        listParamObj.put("remark","");
        JSONObject listBefore = new JSONObject();
        listBefore.put("mode",0);
        listBefore.put("code","");
        listParamObj.put("before",listBefore);
        JSONObject listAfter = new JSONObject();
        listAfter.put("mode",0);
        listAfter.put("code","");
        listParamObj.put("after",listAfter);

        JSONArray headerArry = new JSONArray();
        JSONObject headerObj = new JSONObject();
        headerObj.put("name","Content-Type");
        headerObj.put("value","application/json");
        headerObj.put("remark","");
        JSONObject bodyInfoObj = new JSONObject();
        bodyInfoObj.put("type",0);
        bodyInfoObj.put("rawType",0);
        bodyInfoObj.put("rawTextRemark","");
        bodyInfoObj.put("rawFileRemark","");
        bodyInfoObj.put("rawText","");
        bodyInfoObj.put("rawJSON",new JSONArray());
        bodyInfoObj.put("rawJSONType",0);
        headerObj.put("bodyInfo",bodyInfoObj);
        headerArry.add(headerObj);
        listParamObj.put("header",headerArry);

        listParamObj.put("queryParam",new JSONArray());
        listParamObj.put("bodyParam",new JSONArray());

        JSONObject bodyInfoBody = new JSONObject();
        bodyInfoBody.put("type",1);
        bodyInfoBody.put("rawType",2);
        bodyInfoBody.put("rawTextRemark","");
        bodyInfoBody.put("rawFileRemark","");
        bodyInfoBody.put("rawText","");
        bodyInfoBody.put("rawJSONType",0);

        JSONArray rawJSONArry = new JSONArray();
        for(Map.Entry<String,Object> entry:jsonObject.entrySet()){
            JSONObject dataObj = new JSONObject();
            dataObj.put("name",entry.getKey());
            dataObj.put("remark",entry.getValue());
            dataObj.put("must",1);
            dataObj.put("show",1);
            dataObj.put("drag",1);
            dataObj.put("type",0);
            dataObj.put("mock","");
            rawJSONArry.add(dataObj);
        }

        bodyInfoBody.put("rawJSON",rawJSONArry);
        listParamObj.put("bodyInfo",bodyInfoBody);
        //返回参数
        JSONObject outPropertyRemark = getPropertyRemark(outClazz);
        JSONArray outParamArry = getParamObj(outClazz,outPropertyRemark,null);
        listParamObj.put("outParam",outParamArry);

        JSONObject listOutInfoObj = new JSONObject();
        listOutInfoObj.put("type",0);
        listOutInfoObj.put("rawRemark","");
        listOutInfoObj.put("rawMock","");
        listOutInfoObj.put("jsonType",0);
        listParamObj.put("outInfo",listOutInfoObj);

        listParamObj.put("restParam",new JSONArray());

        listParamArry.add(listParamObj);
        outObj.put("param",listParamArry);

        return outObj;
    }

    /**
     * 获取一张表的基本接口
     * @param pageClazz 分页类
     * @param clazzView view类
     * @param clazzQuery 查询类
     * @param editReturn 编辑返回类
     * @param context 说明
     * @param urlPrefix 路径前缀
     * @return
     */
    public static JSONArray getApiDOCleverJsonAll(Class<?> pageClazz,Class<?> clazzView,Class<?> clazzQuery,
                                                  Class<?> editReturn,String context,String urlPrefix){
        if(pageClazz == null || clazzView == null || clazzQuery == null || editReturn == null
        || StringUtils.isEmpty(context) || StringUtils.isEmpty(urlPrefix)){
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        //list查询
        JSONObject jsonObjectList = getApiDOCleverJsonGet(pageClazz,clazzQuery,context+"列表查询",urlPrefix+"/list",clazzView);
        jsonArray.add(jsonObjectList);
        //编辑
        JSONObject jsonObjectEdit = getApiDOCleverJsonPost(editReturn,clazzView,context+"编辑",urlPrefix+"/edit");
        jsonArray.add(jsonObjectEdit);
        //通过id查询
        JSONObject id = new JSONObject();
        id.put("id","id");
        JSONObject jsonObjectId = getApiDOCleverJsonGet(clazzView,id,context+"getById",urlPrefix+"/get",null);
        jsonArray.add(jsonObjectId);
        //删除
        JSONObject jsonObjectDel = getApiDOCleverJsonPost(editReturn,id,context+"删除",urlPrefix+"/del");
        jsonArray.add(jsonObjectDel);
        return jsonArray;
    }

    /**
     * 获取java实体属性和注释
     * @param clazz
     * @return
     */
    private static JSONObject getPropertyRemark(Class<?> clazz){
        if(clazz == null){
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
            String path = clazz.getResource("").getPath();
            path = path.replace("target/classes/","src/main/java/")+clazz.getSimpleName()+".java";
            File file = new File(path);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String strPre = "";
                String tempStr = null;
                while((tempStr = reader.readLine()) != null) {
                    String strNow = tempStr.trim();
                    if(strNow.contains("private")){
                        String[] propertyArry = strNow.split(" ");
                        if(propertyArry != null && propertyArry.length >= 3
                                && StringUtils.hasText(strPre)){
                            jsonObject.put(propertyArry[2].substring(0,propertyArry[2].length()-1),strPre);
                        }
                    }
                    int startIndex = strNow.indexOf("/**");
                    int endIndex = strNow.indexOf("*/");
                    if(startIndex > -1 && endIndex > -1){
                        strPre = strNow.substring(startIndex+3,endIndex).trim();

                    }
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




        return jsonObject;
    }

    /**
     * 生成返回参数 type 0String 1Number 2Boolen 3Arry 4Object 5Mixed
     * @param clazz
     * @param propertyRemark
     */
    public static JSONArray getParamObj(Class<?> clazz,JSONObject propertyRemark,Class<?> genericClass){
        if(clazz == null){
            return null;
        }
        List<Field> fieldList = ReflectionUtils.getDeclaredFields(clazz);
        JSONArray jsonArray = new JSONArray();
        Field field = null;
        for (int i = 0; i < fieldList.size(); i++) {
            field = fieldList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",field.getName());
            jsonObject.put("remark",propertyRemark.getString(field.getName()));
            jsonObject.put("must",0);
            if(field.getType() == Integer.class || field.getType() == Float.class
                    ||  field.getType() == Double.class || field.getType() == Long.class
                || field.getType().isPrimitive()){
                jsonObject.put("type",1);
                jsonObject.put("mock","1");
            }else if(field.getType() == String.class){
                jsonObject.put("type",0);
                jsonObject.put("mock","");
            }else if(field.getType() == Boolean.class){
                jsonObject.put("type",2);
                jsonObject.put("mock","");
            }else if(field.getType() == List.class){
                jsonObject.put("type",3);
                jsonObject.put("mock","");
                if (genericClass == null) {
                    // 如果是List类型，得到其Generic的类型
                    Type genericType = field.getGenericType();
                    // 如果是泛型参数的类型
                    if(genericType instanceof ParameterizedType){
                        ParameterizedType pt = (ParameterizedType) genericType;
                        //得到泛型里的class类型对象
                        Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                        if (genericClazz != null && genericClazz != Integer.class && genericClazz != String.class
                                && genericClazz != Float.class && genericClazz != Double.class
                                && genericClazz != Long.class && genericClazz != Boolean.class
                                && !genericClazz.isPrimitive()) {
                            JSONArray dataArryd = new JSONArray();
                            JSONObject dataObj = new JSONObject();
                            dataObj.put("name",null);
                            dataObj.put("remark","");
                            dataObj.put("must",0);
                            dataObj.put("type",4);
                            dataObj.put("mock","");
                            JSONArray dataArry = getParamObj(genericClazz,getPropertyRemark(genericClazz),null);
                            dataObj.put("data",dataArry);
                            dataArryd.add(dataObj);
                            jsonObject.put("data",dataArryd);
                        }else {
                            jsonObject.put("data",new JSONArray());
                        }
                    }else {
                        jsonObject.put("data",new JSONArray());
                    }
                }else {
                    JSONArray dataArryd = new JSONArray();
                    JSONObject dataObj = new JSONObject();
                    dataObj.put("name",null);
                    dataObj.put("remark","");
                    dataObj.put("must",0);
                    dataObj.put("type",4);
                    dataObj.put("mock","");
                    JSONArray dataArry = getParamObj(genericClass,getPropertyRemark(genericClass),null);
                    dataObj.put("data",dataArry);
                    dataArryd.add(dataObj);
                    jsonObject.put("data",dataArryd);
                }
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 生成post入参 type 0String 1Number 2Boolen 3Arry 4Object 5Mixed
     * @param clazz
     * @param propertyRemark
     */
    public static JSONArray getInParamObjPost(Class<?> clazz,JSONObject propertyRemark){
        if(clazz == null){
            return null;
        }
        List<Field> fieldList = ReflectionUtils.getDeclaredFields(clazz);
        JSONArray jsonArray = new JSONArray();
        Field field = null;
        for (int i = 0; i < fieldList.size(); i++) {
            field = fieldList.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",field.getName());
            jsonObject.put("remark",propertyRemark.getString(field.getName()));
            jsonObject.put("must",0);
            jsonObject.put("show",1);
            jsonObject.put("drag",1);
            if(field.getType() == Integer.class || field.getType() == Float.class
                    ||  field.getType() == Double.class || field.getType() == Long.class){
                jsonObject.put("type",1);
                jsonObject.put("mock","1");
            }else if(field.getType() == String.class){
                jsonObject.put("type",0);
                jsonObject.put("mock","");
            }else if(field.getType() == Boolean.class){
                jsonObject.put("type",2);
                jsonObject.put("mock","");
            }else if(field.getType() == List.class){
                jsonObject.put("type",3);
                jsonObject.put("mock","");
                // 如果是List类型，得到其Generic的类型
                Type genericType = field.getGenericType();
                // 如果是泛型参数的类型
                if(genericType instanceof ParameterizedType){
                    ParameterizedType pt = (ParameterizedType) genericType;
                    //得到泛型里的class类型对象
                    Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                    if (genericClazz != null && genericClazz != Integer.class && genericClazz != String.class
                            && genericClazz != Float.class && genericClazz != Double.class
                            && genericClazz != Long.class && genericClazz != Boolean.class) {
                        JSONArray dataArryd = new JSONArray();
                        JSONObject dataObj = new JSONObject();
                        dataObj.put("name",null);
                        dataObj.put("remark","");
                        dataObj.put("must",1);
                        dataObj.put("type",4);
                        dataObj.put("show",1);
                        dataObj.put("drag",1);
                        dataObj.put("mock","");
                        JSONArray dataArry = getInParamObjPost(genericClazz,getPropertyRemark(genericClazz));
                        dataObj.put("data",dataArry);
                        dataArryd.add(dataObj);
                        jsonObject.put("data",dataArryd);
                    }else {
                        jsonObject.put("data",new JSONArray());
                    }
                }
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    public static void main(String[] args) {
        String urlPrefix = "/test/a";
        ObjectId project = new ObjectId("5b8f4dc642acf80c64db90c6");
        ObjectId group = new ObjectId("5b97837585fc6f3ffcf8cadf");
        ObjectId owner = new ObjectId("5b8f499f42acf80c64db90b9");
        ObjectId editor = new ObjectId("5b8f499f42acf80c64db90b9");
        String id = UUID.randomUUID().toString();
        /*JSONArray jsonArray = GenerateAPI.getApiDOCleverJsonAll(Object.class, Object.class,
                Object.class,Object.class,"测试",urlPrefix);
        if(jsonArray != null){
            for (int i = 0; i < jsonArray.size(); i++) {
                System.out.println(JSONObject.toJSONString(jsonArray.get(i), SerializerFeature.WriteMapNullValue));
                JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                jsonObject.put("project",project);
                jsonObject.put("group",group);
                jsonObject.put("owner",owner);
                jsonObject.put("editor",editor);
                jsonObject.put("id",id);
                jsonObject.put("__v",0);
                MongodbUtils.insert(jsonObject);
            }
        }*/
        /*JSONObject jsonObject = GenerateAPI.getApiDOCleverJsonPost(UpdateReturnJson.class, MaintainRecordView.class,
                "保养记录详情提交",urlPrefix);*/
        JSONObject jsonObject = getApiDOCleverJsonGet(Object.class, Object.class,
                "测试", urlPrefix,Object.class);
        if(jsonObject != null){
            jsonObject.put("project",project);
            jsonObject.put("group",group);
            jsonObject.put("owner",owner);
            jsonObject.put("editor",editor);
            jsonObject.put("id",id);
            jsonObject.put("__v",0);
            MongodbUtils.insert(jsonObject);
        }
    }
}
