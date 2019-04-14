package com.ypc.common.service;

import java.util.Map;
import java.util.Set;

/**
 * @Author: ypc
 * @Date: 2018-06-05 9:52
 * @Description:
 * @Modified By:
 */
public interface RedisService {

    /**
     * 事务 multi exec
     * @param map
     * @param second
     */
     void mSet(Map<String, String> map, long second);

     void set(String key, String value);

     void set(String key, String value, long second);

     String get(String key);

     boolean expire(String key, long second);

     boolean setNx(String key, String value);

     boolean lock(String key, long second);

     void delete(String key);

     long lPush(String key, String value);

     long rPush(String key, String value);

     String lPop(String key);

     String rPop(String key);

     Long lLen(String key);

     Long sAdd(String key, String value);

     Long sAdd(String key, String... values);

     Long sMove(String key, String value);

     Set<String> sMembers(String key);

     long incr(String key);

     long incrBy(String key, long val);

     long decr(String key);

     long decrBy(String key, long val);

     long publish(String channel, String msg);

     boolean hasKey(String Key);

     boolean isMember(String key, String value);

     Map<String,String> hgetall(String key);
    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    long getExpire(String key);

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    void del(String... key);

    String hget(String key, String item);

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    Map<String,String> hmget(String key);

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    boolean hmset(String key, Map<String, String> map);

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    boolean hmset(String key, Map<String, String> map, long time);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    boolean hset(String key, String item, String value);

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    boolean hset(String key, String item, String value, long time);

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    void hdel(String key, String... item);

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
   boolean hHasKey(String key, String item);

    /**
     * 获取hash所有key
     * @param key
     * @return
     */
    Set<String> hkeys(String key);
}
