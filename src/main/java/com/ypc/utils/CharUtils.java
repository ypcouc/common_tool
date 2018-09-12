package com.ypc.utils;

/**
 * Created by dodo on 2017/9/2.
 */
public class CharUtils {

    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        char[] ch = s.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }

        return new String(ch);
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s) {
        char[] ch = s.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }

    public static String dotTransfer(String s) {
        if (s.length() > 0) {
            String replaceStr = s.replace(".", "/");
            return replaceStr;
        } else {
            return "";
        }

    }

    /**
     * 判断字符串是不是数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


}
