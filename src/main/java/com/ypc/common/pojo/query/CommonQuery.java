package com.ypc.common.pojo.query;

public class CommonQuery {
    /**开始时间*/
    private String startTime;
    /**结束时间*/
    private String endTime;

    private String[] tableNames;

    /**日期格式*/
    private String dateFormat;
    /**当前页*/
    private Integer pageNum;
    /**每页的数量*/
    private Integer pageSize;
    /**公司id*/
    private String companyId;
    /**工厂id*/
    private String factoryId;
    /**当前用户*/
    private String currentUserId;
    /**当前登陆用户角色id*/
    private String currentUserRoleId;
    /** 审核查询 0-待审核 1-已审核*/
    private Integer auditSearch;

    /** 分组字段*/
    private String groupByColumn;


    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
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

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String[] getTableNames() {
        return tableNames;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }

    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCurrentUserRoleId() {
        return currentUserRoleId;
    }

    public void setCurrentUserRoleId(String currentUserRoleId) {
        this.currentUserRoleId = currentUserRoleId;
    }

    public Integer getAuditSearch() {
        return auditSearch;
    }

    public void setAuditSearch(Integer auditSearch) {
        this.auditSearch = auditSearch;
    }

    public String getGroupByColumn() {
        return groupByColumn;
    }

    public void setGroupByColumn(String groupByColumn) {
        this.groupByColumn = groupByColumn;
    }
}
