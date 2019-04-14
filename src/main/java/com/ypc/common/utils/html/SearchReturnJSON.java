package com.ypc.common.utils.html;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 普通查询统一返回接口
 *
 * @author cff
 */
@JsonIgnoreProperties(value = {"handler"})
public class SearchReturnJSON implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
