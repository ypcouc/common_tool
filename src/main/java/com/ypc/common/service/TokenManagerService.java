package com.ypc.common.service;

import com.alibaba.fastjson.JSONObject;
import com.ypc.common.pojo.TokenModel;

public interface TokenManagerService {
    /**
     * 创建一个token关联上指定用户
     * @param userId 指定用户的id
     * @param userName 用户名称
     * @param userCode 用户编号
     * @param factoryId 工厂id
     * @param terminal 操作终端 terminal（1-pc 2-一体机 3-手机端,4-小程序,9-后台管理）
     * @param companyId 公司id
     * @param roleStr 角色id逗号拼接
     * @param smallId 小程序id
     * @return 生成的token
     */
    TokenModel createToken(String userId, String userName, String userCode, String factoryId, Integer isSuperAdmin, Integer terminal,
                           String companyId, String roleStr, String smallId, String companyCode,
                           String companyName, String factoryCode, String factoryName);

    /**
     * 检查token是否有效
     * @param model token
     * @return 是否有效
     */
    boolean checkToken(TokenModel model);

    /**
     * 获取token信息
     * @param token 秘钥
     * @return
     */
    TokenModel getTokenModel(String token);

    /**
     * 清除token
     * @param userId 用户id
     */
    void deleteToken(String userId);

    /**
     *
     * @param jsonObject
     * @return
     */
    TokenModel creatCompanyToken(JSONObject jsonObject);

    /**
     * 获取token
     * @param userId
     * @return
     */
    TokenModel getUserToken(String userId);

    /**
     * 删除token
     * @param token
     */
    void deleteTokenByToken(String token);


    /**
     * 更新token时间
     * @param token
     * @param tokenModel
     */
    void updateToken(String token, TokenModel tokenModel);
}
