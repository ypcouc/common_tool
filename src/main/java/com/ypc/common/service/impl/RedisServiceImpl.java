package com.ypc.common.service.impl;

import com.ypc.common.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: ypc
 * @Date: 2018-06-05 9:55
 * @Description:
 * @Modified By:
 */
@Service
public class RedisServiceImpl implements RedisService {
    private final Logger logger = LogManager.getLogger(RedisServiceImpl.class);

    @Value("${spring.redis.prefKey}")
    private String prefKey;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void mSet(Map<String, String> map, long second) {
        /** 事务内执行 **/
        SessionCallback<Object> sessionCallback = new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    operations.opsForValue().set(getKey(entry.getKey()), entry.getValue(), second, TimeUnit.SECONDS);
                }
                return operations.exec();
            }
        };
        redisTemplate.execute(sessionCallback);
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(getKey(key), value);
    }

    @Override
    public void set(String key, String value, long second) {
        redisTemplate.opsForValue().set(getKey(key), value, second, TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(getKey(key));
    }

    @Override
    public boolean expire(String key, long second) {
        return redisTemplate.expire(getKey(key), second, TimeUnit.SECONDS);
    }

    @Override
    public boolean setNx(String key, String value) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.setNX(serializer.serialize(getKey(key)), serializer.serialize(value));
        }, true);
    }

    @Override
    public boolean lock(String key, long second) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nxValue = sdf.format(now.getTime());
        if (setNx(key, nxValue)) {
            /** 获取锁成功 **/
            if (expire(key, second)) {
                return true;
            }
            delete(key);
        } else {
            /** 检查是否超时 **/
            nxValue = get(key);
            try {
                Date date = sdf.parse(nxValue);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.SECOND, (int) second);
                if (calendar.getTimeInMillis() < now.getTime()) {
                    delete(key);
                    return true;
                }
            } catch (Exception e) {
                delete(key);
            }
        }
        return false;
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(getKey(key));
    }

    @Override
    public long lPush(String key, String value) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.lPush(serializer.serialize(getKey(key)), serializer.serialize(value));
        }, true);
    }

    @Override
    public long rPush(String key, String value) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.rPush(serializer.serialize(getKey(key)), serializer.serialize(value));
        }, true);
    }

    @Override
    public String lPop(String key) {
        return redisTemplate.execute((RedisConnection conn) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] res = conn.lPop(serializer.serialize(getKey(key)));
            return serializer.deserialize(res);
        }, true);
    }

    @Override
    public Long lLen(String key) {
        return redisTemplate.execute((RedisConnection conn) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Long res = conn.lLen(serializer.serialize(getKey(key)));
            return res;
        }, true);
    }

    @Override
    public String rPop(String key) {
        return redisTemplate.execute((RedisConnection conn) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            byte[] res = conn.rPop(serializer.serialize(getKey(key)));
            return serializer.deserialize(res);
        }, true);
    }

    @Override
    public Long sAdd(String key, String value) {
        return redisTemplate.opsForSet().add(getKey(key), value);
    }

    @Override
    public Long sAdd(String key, String... values) {
        if(values == null){
            return null;
        }
        Long result = redisTemplate.opsForSet().add(getKey(key), values);
        return result;
    }

    @Override
    public Long sMove(String key, String value) {
        return redisTemplate.opsForSet().remove(getKey(key), value);
    }

    @Override
    public Set<String> sMembers(String key) {
        return redisTemplate.opsForSet().members(getKey(key));
    }

    @Override
    public long incr(String key) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.incr(serializer.serialize(getKey(key)));
        }, true);
    }

    @Override
    public long incrBy(String key, long val) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.incrBy(serializer.serialize(getKey(key)), val);
        }, true);
    }

    @Override
    public long decr(String key) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.decr(serializer.serialize(getKey(key)));
        }, true);
    }

    @Override
    public long decrBy(String key, long val) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.decrBy(serializer.serialize(getKey(key)), val);
        }, true);
    }


    @Override
    public long publish(String channel, String msg) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            return connection.publish(serializer.serialize(getKey(channel)), serializer.serialize(msg));
        }, true);
    }

    @Override
    public boolean hasKey(String Key) {
        return redisTemplate.hasKey(getKey(Key));
    }

    @Override
    public boolean isMember(String key, String value) {
        return redisTemplate.opsForSet().isMember(getKey(key), value);
    }

    @Override
    public Map<String, String> hgetall(String key) {
        return redisTemplate.execute((RedisConnection connection) -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Map<byte[], byte[]> map = connection.hGetAll(serializer.serialize(getKey(key)));
            if (map == null) {
                return Collections.emptyMap();
            }
            return new HashMap<String, String>() {{
                for (Entry<byte[], byte[]> entry : map.entrySet()) {
                    put(serializer.deserialize((entry.getKey())), serializer.deserialize((entry.getValue())));
                }
            }};
        }, true);
    }

    @Override
    public long getExpire(String key) {
        return redisTemplate.getExpire(getKey(key),TimeUnit.SECONDS);
    }

    @Override
    public void del(String... key) {
        if(key !=null && key.length>0){
            if(key.length==1){
                redisTemplate.delete(getKey(key[0]));
            }else{
                Set<String> delKeyset = new HashSet<>();
                for (int i = 0; i < key.length; i++) {
                    delKeyset.add(getKey(key[i]));
                }
                redisTemplate.delete(delKeyset);
            }
        }
    }

    @Override
    public String hget(String key, String item) {
        Object obj = redisTemplate.opsForHash().get(getKey(key), item);
        return obj==null?null:obj.toString();
    }

    @Override
    public Map<String, String> hmget(String key) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(getKey(key));
        if (map == null) {
            return Collections.emptyMap();
        }
        return new HashMap<String, String>() {{
            for (Entry<Object, Object> entry : map.entrySet()) {
                if (entry.getKey() != null) {
                    put(entry.getKey().toString(),entry.getValue()==null?null:entry.getValue().toString());
                }
            }
        }};
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    @Override
    public boolean hmset(String key, Map<String,String> map){
        try {
            redisTemplate.opsForHash().putAll(getKey(key), map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    @Override
    public boolean hmset(String key, Map<String,String> map, long time){
        try {
            redisTemplate.opsForHash().putAll(getKey(key), map);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    @Override
    public boolean hset(String key,String item,String value) {
        try {
            redisTemplate.opsForHash().put(getKey(key), item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    @Override
    public boolean hset(String key,String item,String value,long time) {
        try {
            redisTemplate.opsForHash().put(getKey(key), item, value);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    @Override
    public void hdel(String key, String... item){
        redisTemplate.opsForHash().delete(getKey(key),item);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    @Override
    public boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(getKey(key), item);
    }

    /**
     * 获取hash所有key
     * @param key
     * @return
     */
    @Override
    public Set<String> hkeys(String key) {
        Set<Object> set = redisTemplate.opsForHash().keys(getKey(key));
        Set<String> result = new HashSet<String>();
        if (set != null && set.size() > 0) {
            Iterator<Object> it = set.iterator();
            while (it.hasNext()) {
                Object str = it.next();
                if (str != null) {
                    result.add(str.toString());
                }
            }
        }

        return result;
    }

    private String getKey(String key) {
        return prefKey + "_" + key;
    }

    public String getPrefKey() {
        return prefKey;
    }

    public void setPrefKey(String prefKey) {
        this.prefKey = prefKey;
    }
}
