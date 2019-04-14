package com.ypc.common.utils.sql;

/**
 * 分页对象
 *
 * @author Administrator
 */
public class PaginatorParam {
    /**
     * 页码
     */
    private int pageNo;
    /**
     * 每页条目
     */
    private int length;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


}
