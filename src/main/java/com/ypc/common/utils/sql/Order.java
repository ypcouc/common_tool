package com.ypc.common.utils.sql;

import java.util.Collections;
import java.util.List;

public class Order implements Comparable<Order> {
    public Order(int index, String col, String direct) {
        this.col = col;
        this.direct = direct;
        this.index = index;
    }

    public Order() {
    }

    /**
     * 名称
     */
    private String col;
    /**
     * 排序
     */
    private String direct;
    /**
     * order 出现的顺序
     */
    private int index;

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public static final String ASC = "asc";
    public static final String DESC = "desc";

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int compareTo(Order o) {
        if (o.getIndex() < this.getIndex())
            return 1;
        else if (o.getIndex() > this.getIndex())
            return -1;
        else
            return 0;

    }

    public static List<Order> getEmptyOrderList() {
        return Collections.emptyList();
    }
}
