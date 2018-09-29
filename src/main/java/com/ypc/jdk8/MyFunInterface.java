package com.ypc.jdk8;

@FunctionalInterface
public interface MyFunInterface<T> {
    void get(T t);

    default void getDef(){
        System.out.println("java");
    }

    static void doOther(String s){
        System.out.println(s);
    }
}
