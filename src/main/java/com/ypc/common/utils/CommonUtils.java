package com.ypc.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ypc.common.utils.constants.CommonConstants;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class CommonUtils {

    /**
     * 除
     * @param num1 除数
     * @param num2 被除数
     * @param decimals 保留小数位
     * @return
     */
    public static Float doDivide(Integer num1,Integer num2,Integer decimals){
        Float returnNum = (float) 0.0;
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        if (num2 == null) {
            returnNum = (float) 0.0;
        }
        BigDecimal num1Double = new BigDecimal(num1);
        BigDecimal num2Double = new BigDecimal(num2);
        BigDecimal resultDouble = num1Double.divide(num2Double);
        resultDouble = resultDouble.setScale(decimals, roundingMode);
        returnNum = resultDouble.floatValue();
        return returnNum;
    }

    /**
     * 除
     * @param num1 除数
     * @param num2 被除数
     * @param decimals 保留小数位
     * @return
     */
    public static Float doDivide(Float num1,Float num2,Integer decimals){
        Float returnNum = (float) 0.0;
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        if (num2 == null) {
            returnNum = (float) 0.0;
        }
        BigDecimal num1Double = new BigDecimal(num1.toString());
        BigDecimal num2Double = new BigDecimal(num2.toString());
        BigDecimal resultDouble = num1Double.divide(num2Double);
        resultDouble = resultDouble.setScale(decimals, roundingMode);
        returnNum = resultDouble.floatValue();
        return returnNum;
    }
    /**
     * 除
     * @param num1 除数
     * @param num2 被除数
     * @param decimals 保留小数位
     * @return
     */
    public static Float doDivide(Long num1,Long num2,Integer decimals){
        Float returnNum = (float) 0.0;
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        if (num2 == null) {
            returnNum = (float) 0.0;
        }
        BigDecimal num1Double = new BigDecimal(num1.toString());
        BigDecimal num2Double = new BigDecimal(num2.toString());
        BigDecimal resultDouble = num1Double.divide(num2Double,decimals, BigDecimal.ROUND_HALF_EVEN);
        //resultDouble = resultDouble.setScale(decimals, roundingMode);
        returnNum = resultDouble.floatValue();
        return returnNum;
    }
    /**
     * 浮点数据取decimals位小数
     *
     * @param number
     * @param decimals
     * @return
     */
    public static Float numberFormat(Float number, Integer decimals) {
        Float returnNum = (float) 0.0;
        int roundingMode = 4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
        if (number == null) {
            number = (float) 0.0;
        }
        BigDecimal toDouble = new BigDecimal((double) number);
        toDouble = toDouble.setScale(decimals, roundingMode);
        returnNum = toDouble.floatValue();
        return returnNum;
    }



    /**
     * 数组转String
     *
     * @param arr
     * @param spilt
     * @return
     */
    public static String arrayToString(String[] arr, String spilt) {
        String s1 = "";
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                s1 = s1 + arr[i] + spilt;
            }
            if (StringUtils.hasText(s1)) {
                s1 = s1.substring(0, s1.length() - 1);
            }
        }
        return s1;
    }

    /**
     * 中文字符转英文字符
     * @param str
     * @return
     */
    public static String zhToEng(String str){
        String result = "";
        String[] zh = { "！", "，", "。", "；", "~", "《", "》", "（", "）", "？",
                "｛", "｝", "：", "【", "】", "”", "“","‘", "’", "—","～"," "};
        String[] eng = {"!", ",", ".", ";", "`", "<", ">", "(", ")", "?",
                "{", "}", ":", "{", "}", "\"","\"", "\'", "\'", "-", "~",""};
        for (int i = 0; i < zh.length; i++) {
            str = str.replaceAll(zh[i], eng[i]);
        }
        return str;
    }

    public static String SerialNumber() {
        AtomicLong number = new AtomicLong();
        return "";
    }

    public static String getServer_ip() {
        return CommonConstants.LOCAL_SERVER_IP;
    }

    /**
     * 读取超级用户编码
     *
     * @return
     * @throws IOException
     */
    public static String getSystemUserCode() throws IOException {
        return PropertiesUtils.getPropertyValue("systemUserCode");
    }

    /**
     * 读取超级用户密码
     *
     * @return
     * @throws IOException
     */
    public static String getSystemUserPwd() throws IOException {
        return PropertiesUtils.getPropertyValue("systemUserPwd");
    }

    /**
     * 读取超级用户密码
     *
     * @return
     * @throws IOException
     */
    public static Boolean getIsDisabledBtn() throws IOException {
        return Boolean.valueOf(PropertiesUtils.getPropertyValue("isDisabledBtn"));
    }

    /**
     * 读取上传路径
     *
     * @return
     * @throws IOException
     */
    public static String getFileUploadUrl() throws IOException {
        return PropertiesUtils.getPropertyValue("fileUploadUrl");
    }

    /**
     * 读取文件相对路径
     *
     * @return
     * @throws IOException
     */
    public static String getRelativeFileUploadUrl() throws IOException {
        return PropertiesUtils.getPropertyValue("relativeFileUploadUrl");
    }



    public static JSONObject compareTwoObj(Object obj1,Object obj2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(obj1 == null || obj2 == null){
            return null;
        }
        Class c = obj2.getClass();
        if(!obj1.getClass().isInstance(obj2)){
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        // 获取实体类的所有属性，返回Field数组
        Field[] field = obj1.getClass().getDeclaredFields();
        // 遍历所有属性
        for (int j = 0; j < field.length; j++) {
            // 获取属性的名字
            String name = field[j].getName();
            // 将属性的首字符大写，方便构造get，set方法
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            // 获取属性的类型
            String type = field[j].getGenericType().toString();
            // 如果type是类类型，则前面包含"class "，后面跟类名
            Method m1 = obj1.getClass().getMethod("get" + name);
            Method m2 = obj2.getClass().getMethod("get" + name);
            // 调用getter方法获取属性值
            String value1 = (String) m1.invoke(obj1);
            String value2 = (String) m2.invoke(obj1);
            if (!value1.equals(value2)) {
                jsonObject.put(name,value1+"_"+value2);
            }
        }
        return  jsonObject;
    }

    /**
     * 比较两个是否相等
     * @param obj1
     * @param obj2
     * @return
     */
    public static Boolean isEqual(Object obj1,Object obj2){
        Boolean result = false;
        if(obj1 == null && obj2 == null){
            result = true;
        }else if(obj1 != null && obj2 != null
                && obj1.toString().equals(obj2.toString())){
            result = true;
        }
        return result;
    }

    /**
     * 对象转Map
     * @param obj
     * @return
     */
    public static Map<String,Object> ObjectToMap(Object obj){
        Map<String,Object> map = new HashMap<>();
        try {
            return PropertyUtils.describe(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return map;
    }

}
