package com.ypc.common.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ypc.common.pojo.TokenModel;
import com.ypc.common.service.RedisService;
import com.ypc.common.service.TokenManagerService;
import com.ypc.common.utils.DaoUtils;
import com.ypc.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

@Service("tokenManagerService")
public class TokenManagerServiceImpl implements TokenManagerService {
    public final static String tokenKey = "tokenArr";
    @Autowired
    private RedisService redisService;
    @Override
    public TokenModel createToken(String userId,String userName,String userCode,String factoryId,Integer isSuperAdmin,Integer terminal,
                                  String companyId,String roleStr,String smallId, String companyCode,
                                  String companyName,String factoryCode,String factoryName) {
        if(StringUtils.isEmpty(userId)){
            return null;
        }
        TokenModel tokenModel = new TokenModel();

        try {
            //删除原已登录的token
            if (terminal != null && terminal != 2) {
                this.deleteToken(terminal+"_"+userId);
            }

            //使用uuid作为源token
            String token = terminal+"_"+userId + UUID.randomUUID().toString().replace("-", "");
            tokenModel.setUserId(userId);
            tokenModel.setToken(token);
            tokenModel.setFactoryId(factoryId);
            tokenModel.setIsSuperAdmin(isSuperAdmin);
            tokenModel.setUserCode(userCode);
            tokenModel.setUserName(userName);
            tokenModel.setTerminal(terminal);
            tokenModel.setCompanyId(companyId);
            tokenModel.setRoleStr(roleStr);
            tokenModel.setSmallId(smallId);
            tokenModel.setCompanyCode(companyCode);
            tokenModel.setCompanyName(companyName);
            tokenModel.setFactoryCode(factoryCode);
            tokenModel.setFactoryName(factoryName);
            tokenModel.setTime(DateUtils.getSystemDateForString("yyyy-MM-dd HH:mm:ss"));
            redisService.hset(tokenKey,token, JSON.toJSONString(tokenModel));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tokenModel;
    }

    @Override
    public TokenModel creatCompanyToken(JSONObject jsonObject) {
        if(jsonObject == null || StringUtils.isEmpty(jsonObject.getString("companyId"))){
            return null;
        }
        TokenModel tokenModel = new TokenModel();
        //使用uuid作为源token
        String token = UUID.randomUUID().toString().replace("-", "");
        tokenModel.setToken(token);
        tokenModel.setFactoryId(jsonObject.getString("factoryId"));
        tokenModel.setCompanyId(jsonObject.getString("companyId"));
        tokenModel.setOtherInfo(jsonObject);

        redisService.hset(tokenKey,token, JSON.toJSONString(tokenModel));

        return tokenModel;
    }

    @Override
    public boolean checkToken(TokenModel model) {
        if(model == null || StringUtils.isEmpty(model.getToken())){
            return false;
        }

        TokenModel tokenModel = this.getTokenModel(model.getToken());
        if (tokenModel == null || StringUtils.isEmpty(tokenModel.getUserId())){
            return false;
        }

        return true;
    }

    @Override
    public TokenModel getTokenModel(String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        //Jedis jedis = RedisUtil.getJedis();
        TokenModel tokenModel = null;

        try {
            String value = redisService.hget(tokenKey,token);
            if(StringUtils.hasText(value)){
                tokenModel = JSON.parseObject(value, TokenModel.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tokenModel;
    }

    @Override
    public void deleteToken(String userId) {
        if (StringUtils.hasText(userId)){
            //Jedis jedis = RedisUtil.getJedis();
            try {
                Set<String> keySet = redisService.hkeys(tokenKey);
                if (keySet != null && !keySet.isEmpty()) {
                    Iterator<String> it = keySet.iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        if(key.startsWith(userId)){
                            redisService.hdel(tokenKey,key);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public TokenModel getUserToken(String userId) {
        TokenModel tokenModel = null;
        if (StringUtils.hasText(userId)){
            //Jedis jedis = RedisUtil.getJedis();
            try {
                Set<String> keySet = redisService.hkeys(tokenKey);
                if (keySet != null && !keySet.isEmpty()) {
                    Iterator<String> it = keySet.iterator();
                    while (it.hasNext()) {
                        String key = it.next();
                        if(key.startsWith(userId)){
                            String value = redisService.hget(tokenKey,key);
                            if(StringUtils.hasText(value)){
                                tokenModel = JSON.parseObject(value, TokenModel.class);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tokenModel;
    }
    @Override
    public void deleteTokenByToken(String token) {
        if(StringUtils.hasText(token)){
            redisService.hdel(tokenKey,token);
        }
    }
    @Override
    public void updateToken(String token,TokenModel tokenModel) {
        if(tokenModel != null && StringUtils.hasText(token)){
            redisService.hset(tokenKey,token, JSON.toJSONString(tokenModel));
        }
    }
}
