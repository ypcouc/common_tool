package com.ypc.common.utils.html;

import java.io.Serializable;


public class DataTablesParam implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 51144995237768482L;

    private int offset;

    private int limit;

    private String orders;

    private String name;

    /**当前页*/
    private Integer pageNum;
    /**每页的数量*/
    private Integer pageSize;
    /**开始时间*/
    private String startTime;
    /**结束时间*/
    private String endTime;

    /**公司Id**/
    private String companyId;
    /**工厂Id**/
    private String factoryId;

    /**日期格式*/
    private String dateFormat;

    public String getDateFormat() {
        return dateFormat==null?"yyyy-MM-dd HH:mm:ss":dateFormat;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * 获取页码
     *
     * @return
     */
    public int getPageNumber() {
        if (limit <= 0) {//不分页则返回1
            return 1;
        }else {
            return offset / limit + 1;
        }
    }



    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }
}
