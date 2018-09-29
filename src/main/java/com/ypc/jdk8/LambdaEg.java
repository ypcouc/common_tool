package com.ypc.jdk8;

import java.util.Arrays;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @ClassName LambdaEg
 * @Description (params) -> expression
 *              (params) -> statement
 *              (params) -> { statements ;}
 * @Author JWD
 * @Date 2018/9/29
 * @Version 1.0
 **/
public class LambdaEg {
    /**
     * Lambda实现接口
     */
    public static void implInterface(){
        int i = 1;
        //original
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("i = " + i);
            }
        }).start();
        //lambda
        new Thread(()->System.out.println("i = " + i)).start();
    }

    /**
     * Lambda集合类处理
     * ①Predicate<T>——接收T对象并返回boolean
     * ②Consumer<T>——接收T对象，不返回值
     * ③Function<T, R>——接收T对象，返回R对象
     * ④Supplier<T>——提供T对象（例如工厂），不接收值
     * ⑤UnaryOperator<T>——接收T对象，返回T对象
     * ⑥BinaryOperator<T>——接收两个T对象，返回T对象 
     * 可自定义函数式接口
     */
    public static void collectOpt(){
        List<String> languages = Arrays.asList("Java","Scala","C++","C","C#","Python","Php");
        //遍历 Consumer
        languages.forEach(e->{
            System.out.println(e);
        });
        //过滤 以J开头长度为4的
        Predicate<String> startsWithJ = (n) -> n.startsWith("C");
        Predicate<String> fourLetterLong = (n) -> n.length() > 1;
        List<String> list = languages.stream().filter(startsWithJ.and(fourLetterLong)).collect(Collectors.toList());
        System.out.println(list);
        //or
        languages.stream().filter(startsWithJ.and(fourLetterLong)).forEach(e->System.out.println(e));

        //map reduce
        // 使用lambda表达式为每个订单加上12%的税
        List<Integer> costBeforeTax = Arrays.asList(100,200, 300,400, 500);
       // 使用lambda表达式
        costBeforeTax.stream().map((cost) -> cost + .12*cost).forEach(System.out::println);

        double result = costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum,cost)->sum+cost).get();

        LongSummaryStatistics ls = costBeforeTax.stream().mapToLong(value -> value).summaryStatistics();
        System.out.println(ls.getMax());
        System.out.println(ls.getAverage());
        System.out.println(ls.toString());

        //流并行处理
        languages.parallelStream().filter(startsWithJ.and(fourLetterLong)).forEach(e->System.out.println(e));
        List<String> strs = Arrays.asList("好,好,学", "习,天,天", "向,上");
        List<String[]> strArray = strs.stream().map(str -> str.split(",")).collect(Collectors.toList());
        System.out.println("strArray => "+ strArray);
        // flatMap与map的区别在于 flatMap是将一个流中的每个值都转成一个个流，然后再将这些流扁平化成为一个流 。
        // flatMap把多维转为降维
        List<String> strList = strs.stream().map(str -> str.split(",")).flatMap(/*Arrays::stream*/str->Arrays.stream(str)).collect(Collectors.toList());
        System.out.println("strList => " + strList);




    }

    public static void main(String[] args) {
        //implInterface();
        /*MyFunInterface<String> myFunInterface = (e)->{
            System.out.println(e);
        };
        myFunInterface.get("hello world");
        myFunInterface.getDef();
        MyFunInterface.doOther("C++");*/

        collectOpt();

    }
}
