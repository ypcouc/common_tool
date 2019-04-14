package com.ypc.common.pojo;

import com.alibaba.fastjson.JSONObject;

public class TokenModel {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户名称
     */
    private String userCode;

    /**
     * 随机生成的uuid
     */
    private String token;

    /**工厂id*/
    private String factoryId;
    /**工厂编号*/
    private String factoryCode;
    /**工厂名称*/
    private String factoryName;
    /**
     * 是否超级管理员
     */
    private Integer isSuperAdmin;

    /** 登录设备 （1-pc 2-一体机 3-手机端 4-小程序）*/
    private Integer terminal;

    /**小程序id**/
    private String smallId;
    /**公司id*/
    private String companyId;
    /**公司编号*/
    private String companyCode;
    /**公司名称*/
    private String companyName;
    /**角色id逗号拼接*/
    private String roleStr;
    /**时间*/
    private String time;

    /**其它信息*/
    private JSONObject otherInfo;

    public String getFactoryCode() {
        return factoryCode;
    }

    public void setFactoryCode(String factoryCode) {
        this.factoryCode = factoryCode;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSmallId() {
        return smallId;
    }

    public void setSmallId(String smallId) {
        this.smallId = smallId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public JSONObject getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(JSONObject otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getRoleStr() {
        return roleStr;
    }

    public void setRoleStr(String roleStr) {
        this.roleStr = roleStr;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public Integer getIsSuperAdmin() {
        return isSuperAdmin;
    }

    public void setIsSuperAdmin(Integer isSuperAdmin) {
        this.isSuperAdmin = isSuperAdmin;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
