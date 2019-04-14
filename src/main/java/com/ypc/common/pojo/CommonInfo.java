package com.ypc.common.pojo;

/**
 * Created by dodo on 2017/7/25.
 */
public class CommonInfo {
	/** 新增用户id*/
    private String createBy;
    /** 新增用户姓名*/
    private String createByName;
    /** 新增时间*/
    private String createTime;
    /** 编辑人id*/
    private String updateBy;
    /** 编辑人姓名*/
    private String updateByName;
    /** 编辑时间*/
    private String updateTime;
    /**公司id*/
    private String companyId;
    /**公司编号*/
    private String companyCode;
    /**公司名称*/
    private String companyName;
    /**工厂id*/
    private String factoryId;
    /**工厂编号*/
    private String factoryCode;
    /**工厂名称*/
    private String factoryName;
    /**小程序id*/
    private String smallId;
    /**当前登陆用户角色id*/
    private String currentUserRoleId;

    public String getSmallId() {
        return smallId;
    }

    public void setSmallId(String smallId) {
        this.smallId = smallId;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

	public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCurrentUserRoleId() {
        return currentUserRoleId;
    }

    public void setCurrentUserRoleId(String currentUserRoleId) {
        this.currentUserRoleId = currentUserRoleId;
    }
}
