package com.ypc.common.utils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @Author: ypc
 * @Date: 2018-05-20 17:51
 * @Description:
 * @Modified By:
 */
public class OkhttUtils {
    /**
         application/x-www-form-urlencoded 数据是个普通表单
         multipart/form-data 数据里有文件
         application/json 数据是个json
     */
    public static String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * get同步请求
     * @param url
     * @return
     */
    public static String get(String url){
        if(StringUtils.isEmpty(url)){
            return null;
        }
        String result = null;
        //创建okHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get异步请求
     * @param url
     */
    public static void getAsyn(String url,OkhttpCallback okhttpCallback){
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
                if(okhttpCallback != null){
                    okhttpCallback.doFailure();
                }

            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if(okhttpCallback != null){
                        okhttpCallback.doSuccess(response.body().string());
                    }
                }
            }
        });
    }

    /**
     * post同步请求
     * @param url 路径
     * @param data 参数
     * @return
     */
    public static String post(String url,String data){
        if(StringUtils.isEmpty(url) || StringUtils.isEmpty(data)){
            return null;
        }
        String result = null;
        //创建okHttpClient对象
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse(CONTENT_TYPE), data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * post同步请求
     * @param url 路径
     * @param data 参数
     * @param contentType 请求方式
     * @return
     */
    public static String post(String url,String data,String contentType){
        if(StringUtils.isEmpty(url) || StringUtils.isEmpty(data)
                || StringUtils.isEmpty(contentType)){
            return null;
        }
        String result = null;
        //创建okHttpClient对象
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse(contentType), data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * post异步请求
     * @param url 路径
     * @param data 参数
     */
    public static void postAsyn(String url,String data,OkhttpCallback okhttpCallback) {
        if(StringUtils.isEmpty(url) || StringUtils.isEmpty(data)
                || okhttpCallback == null){
            return;
        }
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse(CONTENT_TYPE), data);
        //创建一个Request
        Request request=new Request.Builder()
                .post(body)
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
                if(okhttpCallback != null){
                    okhttpCallback.doFailure();
                }
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if(okhttpCallback != null){
                        okhttpCallback.doSuccess(response.body().string());
                    }
                }
            }
        });
    }

    /**
     * post异步请求
     * @param url 路径
     * @param data 参数
     */
    public static void postAsyn(String url,String data,String contentType,OkhttpCallback okhttpCallback) {
        if(StringUtils.isEmpty(url) || StringUtils.isEmpty(data)
                || StringUtils.isEmpty(contentType) || okhttpCallback == null){
            return;
        }
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse(contentType), data);
        //创建一个Request
        Request request=new Request.Builder()
                .post(body)
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
                if(okhttpCallback != null){
                    okhttpCallback.doFailure();
                }
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if(okhttpCallback != null){
                        okhttpCallback.doSuccess(response.body().string());
                    }
                }
            }
        });
    }


    /**
     * post 表单提交同步请求
     * @param url 路径
     * @param data 参数
     */
    public static String formPost(String url, JSONObject data) {
        if(StringUtils.isEmpty(url) || data == null){
            return null;
        }
        //创建okHttpClient对象
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key:data.keySet()){
            builder.add(key,data.getString(key));
        }
        FormBody body = builder.build();
        //创建一个Request
        Request request=new Request.Builder()
                .post(body)
                .url(url)
                .build();
        String result = null;
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * post 表单提交异步请求
     * @param url 路径
     * @param data 参数
     */
    public static void formPostAsyn(String url, JSONObject data, OkhttpCallback okhttpCallback) {
        if(StringUtils.isEmpty(url) || data == null || okhttpCallback == null){
            return;
        }
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key:data.keySet()){
            builder.add(key,data.getString(key));
        }
        FormBody body = builder.build();
        //创建一个Request
        Request request=new Request.Builder()
                .post(body)
                .url(url)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new Callback() {
            //请求失败时调用
            @Override
            public void onFailure(Call call, IOException e) {
                if(okhttpCallback != null){
                    okhttpCallback.doFailure();
                }
            }
            //请求成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if(okhttpCallback != null){
                        okhttpCallback.doSuccess(response.body().string());
                    }
                }
            }
        });
    }
}
