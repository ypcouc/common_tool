package com.ypc.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(value = "handler")
public class SimpleChildPojo {

    private String id;
    private String name;
    /**公司id*/
    private String companyId;
    /**工厂id*/
    private String factoryId;
    private List<SimpleChildPojo> children;


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

    public List<SimpleChildPojo> getChildren() {
        if(this.children != null && this.children.size() == 0){
            this.children = null;
        }
        return children;
    }

    public void setChildren(List<SimpleChildPojo> children) {
        this.children = children;
    }

}
