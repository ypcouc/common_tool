package com.ypc.common.utils.sql;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Author: ypc
 * @Date: 2018-07-01 11:37
 * @Description:
 * @Modified By:
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MyPageInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**当前页*/
    private int pageNum;
    /**每页的数量*/
    private int pageSize;
    /**总记录数*/
    private long total;
    /**总页数*/
    private int pages;
    /**结果集*/
    private List<T> list;
    /**是否为第一页*/
    private Boolean isFirstPage = false;
    /**是否为最后一页*/
    private Boolean isLastPage = false;


    public MyPageInfo() {
    }

    /**
     * 包装Page对象
     *
     * @param list
     */
    public MyPageInfo(List<T> list) {
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();

            this.pages = page.getPages();
            this.list = page;
            this.total = page.getTotal();
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();

            this.pages = 1;
            this.list = list;
            this.total = list.size();
        }
        if (list instanceof Collection && !(list instanceof org.springframework.data.domain.Page)) {
            //判断页面边界
            judgePageBoudary();
        }
    }

    /**
     * es分页
     * @param page
     */
    public MyPageInfo(org.springframework.data.domain.Page page){
        this.pageNum = page.getNumber();
        this.pageSize = page.getSize();

        this.pages = page.getTotalPages();
        this.list = page.getContent();
        this.total = page.getTotalElements();
        this.isFirstPage = page.isFirst();
        this.isLastPage = page.isLast();
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Boolean getFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(Boolean firstPage) {
        isFirstPage = firstPage;
    }

    public Boolean getLastPage() {
        return isLastPage;
    }

    public void setLastPage(Boolean lastPage) {
        isLastPage = lastPage;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageInfo{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", list=").append(list);
        sb.append(", isFirstPage=").append(isFirstPage);
        sb.append(", isLastPage=").append(isLastPage);
        sb.append(", navigatepageNums=");
        sb.append('}');
        return sb.toString();
    }

}
