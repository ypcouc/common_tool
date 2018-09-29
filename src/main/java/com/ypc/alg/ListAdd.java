package com.ypc.alg;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @ClassName ListAdd
 * @Description TODO
 * @Author JWD
 * @Date 2018/9/20
 * @Version 1.0
 **/
public class ListAdd {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode resultNext = result;
        int carry = 0;
        while (l1 != null || l2 != null){
            if(l1 != null){
                carry += l1.val;
                l1 = l1.next;
            }
            if(l2 != null){
                carry += l2.val;
                l2 = l2.next;
            }
            resultNext.next = new ListNode(carry%10);
            carry /= 10;
            resultNext = resultNext.next;
        }
        if(carry > 0){
            resultNext.next = new ListNode(1);
        }
        return result.next;
    }

    public static int lengthOfLongestSubstring(String s) {
        int result = 0;
        if (s != null) {
            char[] chars = s.toCharArray();
            Map<String,String> map = new HashMap<>();
            int max = 0;
            int temp = 0;
            int n = 0;
            int i = 0;
            while (i < chars.length && n < chars.length){
                String key = String.valueOf(chars[i]);
                i++;
                if(map.get(key) == null){
                    temp++;
                    if(max < temp){
                        max = temp;
                    }
                    map.put(key,"1");
                }else {
                    temp = 0;
                    map.clear();
                    n++;
                    i = n;
                }

            }
            result = max;
        }
        return result;
    }


    public static void main(String[] args) {
        /*ListNode listNode1 = new ListNode(2);
        listNode1.next = new ListNode(4);
        listNode1.next.next = new ListNode(3);
        ListNode listNode2 = new ListNode(5);
        listNode2.next = new ListNode(6);
        listNode2.next.next = new ListNode(4);

        ListNode result = addTwoNumbers(listNode1,listNode2);
        ListNode next = result.next;
        System.out.println(result.val);
        while (next != null){
            System.out.println(next.val);
            next = next.next;
        }*/
        List<String> list = Arrays.asList("dasd","dasd","adwdeg");
        list.forEach(e->{
            System.out.println(e);
        });
        String a = "aaa";
        List<String> list1 = list.stream().map(e->{
            return e+a;
        }).collect(Collectors.toList());
        System.out.println(list1);

        //Predicate

    }
    interface MathOperation {
        int operation(int a, int b);
    }
}
