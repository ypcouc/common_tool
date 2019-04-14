package com.ypc.common.utils;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class DaoUtils {

    public static JdbcTemplate getJDBCTemp() {
        JdbcTemplate jt = SpringContextsUtil.getBean(JdbcTemplate.class);
        return jt;
    }


    public static List getSqlList(String sql, Object[] paramObj) {
        return getJDBCTemp().queryForList(sql, paramObj);
    }

    /**
     * 采用预编译方式
     * 获取查询唯一一条记录一个字段的sql语句的唯一返回值，如果没有查询到则返回空,如果返回多条记录则抛出异常
     *
     * @param sql
     * @return
     */
    public static String getSqlStrVal(String sql, Object[] paramObj) {
        String returnVal = "";
        List returnList = getJDBCTemp().queryForList(sql, paramObj);
        if (returnList.size() == 1) {
            Map map = (Map) returnList.get(0);
            if (map.size() == 1) {
                for (Iterator iterator = map.keySet().iterator(); iterator.hasNext(); ) {
                    Object obj = map.get(iterator.next());
                    if (obj != null) {
                        returnVal = obj.toString();
                    }
                }
            } else {
                throw new RuntimeException("查询字段大于1");
            }
        } else {
            if (returnList.size() == 0) {
                //throw new Exception("未找到结果");
            } else {
                throw new RuntimeException("结果大于一条记录");
            }
        }
        return returnVal;
    }

    /**
     * 获取查询唯一一条记录一个字段的sql语句的唯一返回值，如果没有查询到则返回空,如果返回多条记录则抛出异常
     *
     * @param sql
     * @return
     */
    public static Map getSqlMapVal(String sql, Object[] obj) {
        Map returnMap = new HashMap();
        try {
            List returnList = getJDBCTemp().queryForList(sql, obj);
            if (returnList.size() > 1) {
                throw new Exception("返回了多于一条的结果");
            } else if (returnList.size() == 1) {
                returnMap = (Map) returnList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMap;
    }
}
