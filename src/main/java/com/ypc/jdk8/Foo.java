package com.ypc.jdk8;

/**
 * @ClassName Foo
 * @Description TODO
 * @Author JWD
 * @Date 2018/9/29
 * @Version 1.0
 **/
public class Foo {
    private String code;

    private Integer count;

    public Foo(String code, Integer count) {
        this.code = code;
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
