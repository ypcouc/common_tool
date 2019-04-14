package com.ypc.common.utils.html;

import java.io.Serializable;
import java.util.List;

public class DataTablesReturnObj implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6514434412074872815L;

    private int total;

    private List<?> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
