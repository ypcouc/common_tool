package com.ypc.alg;

import java.util.*;

/**
 * @ClassName LeetCode
 * @Description TODO
 * @Author JWD
 * @Date 2018/9/30
 * @Version 1.0
 **/
public class LeetCode {
    /**
     *  两个排序数组的中位数
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n1 = nums1.length,n2 = nums2.length,i=0,j=0,k=0;
        int m = n1 + n2;
        int[] temp = new int[m];
        //把较小的数先移到新数组中
        while (i < n1 && j < n2){
            if(nums1[i] < nums2[j]){
                temp[k++] = nums1[i++];
            }else {
                temp[k++] = nums2[j++];
            }
        }
        // 把左边剩余的数移入数组
        while(i < n1){
            temp[k++] = nums1[i++];
        }
        // 把右边边剩余的数移入数组
        while(j < n2){
            temp[k++] = nums2[j++];
        }
        double result = -1;
        if(m%2 == 0){
            result = (temp[m/2-1]+temp[m/2])/(double)2;
        }else {
            result = temp[m/2];
        }

        return result;
    }

    //二进制手表
    public static List<String> readBinaryWatch(int num) {
        Integer[] hour = {1,2,4,8};
        Integer[] minute = {1,2,4,8,16,32};
        List<String> result = new ArrayList<>();
        if(num > 8 || num < 0){
            return new ArrayList<>();
        }
        if(num == 0){
            return Arrays.asList("0:00");
        }
        int hourNM = 0;
        int minuteNM = 0;
        while (hourNM <= num && minuteNM <= num && hourNM <= 3 ){
            minuteNM = num - hourNM;
            if(minuteNM > 5){
                hourNM++;
                continue;
            }
            Set<Set<Integer>> hourSet = randomSelectN(hour,hourNM++);
            Set<Set<Integer>> minuteSet = randomSelectN(minute,minuteNM);

            if ((hourSet != null && !hourSet.isEmpty()) ||
                    (minuteSet != null && !minuteSet.isEmpty())) {
                Set<Integer> hourSumSet = new HashSet<>();
                if(hourSet == null || hourSet.isEmpty()){
                    hourSumSet.add(0);
                }else {
                    Iterator<Set<Integer>> itHour = hourSet.iterator();
                    while (itHour.hasNext()){
                        Integer hourSum = 0;
                        Iterator<Integer> itHourItem = itHour.next().iterator();
                        while (itHourItem.hasNext()){
                            hourSum += itHourItem.next();
                        }
                        if (hourSum <= 11) {
                            hourSumSet.add(hourSum);
                        }
                    }
                }

                Set<Integer> minuteSumSet = new HashSet<>();
                if(minuteSet == null || minuteSet.isEmpty()){
                    minuteSumSet.add(0);
                }else {
                    Iterator<Set<Integer>>  itMinute = minuteSet.iterator();
                    while (itMinute.hasNext()){
                        Integer minuteSum = 0;
                        Iterator<Integer> itMinuteItem = itMinute.next().iterator();
                        while (itMinuteItem.hasNext()){
                            minuteSum += itMinuteItem.next();
                        }
                        if (minuteSum <= 59) {
                            minuteSumSet.add(minuteSum);
                        }
                    }
                }

                Iterator<Integer> itHourSum = hourSumSet.iterator();

                while (itHourSum.hasNext()){
                    Integer tempHour = itHourSum.next();
                    Iterator<Integer> itMinuteSum = minuteSumSet.iterator();
                    while (itMinuteSum.hasNext()){
                        Integer tempMinute = itMinuteSum.next();
                        result.add(tempHour+":"+(tempMinute>=10?tempMinute:"0"+tempMinute));
                    }
                }
            }
        }

        return result;

    }

    /**
     * 从集合中随意挑选n个元素
     * @param arry
     * @param num
     * @return
     */
    public static Set<Set<Integer>> randomSelectN(Integer[] arry,int num){
        if(arry == null || num > arry.length){
            return new HashSet<Set<Integer>>();
        }
        if(num < 1){
            return new HashSet<Set<Integer>>();
        }
        if(num == 1){
            Set<Set<Integer>> result = new HashSet<>();
            for (int i = 0; i < arry.length; i++) {
                result.add(new HashSet<>(Arrays.asList(arry[i])));
            }
            return result;
        }else {
            Set<Set<Integer>> resultTemp = randomSelectN(arry,num-1);
            Set<Set<Integer>> result = new HashSet<>();
            if (resultTemp != null && resultTemp.size() > 0) {
                Iterator<Set<Integer>> setIt = resultTemp.iterator();
                while (setIt.hasNext()){
                    Set<Integer> value = setIt.next();
                    Set<Integer> temp = new HashSet<>(Arrays.asList(arry));
                    temp.removeAll(value);
                    Iterator<Integer> it = temp.iterator();
                    while (it.hasNext()){
                        Set<Integer> orig =  new HashSet<>();
                        orig.addAll(value);
                        orig.add(it.next());
                        result.add(orig);
                    }
                }
            }
            return result;
        }

    }

    public void getRead(int[] hour,int[] minute,int num,List<Integer> hours,List<Integer> minutes){

    }

    public static void main(String[] args) {
        /*int 祖 = 22269;
        int 国 = 24198;
        int 生 = 21152;
        int 日 = 29677;
        int 快 = 24555;
        int 乐 = 20048;
        System.err.print((char)祖);
        System.err.print((char)国);
        System.err.print((char)生);
        System.err.print((char)日);
        System.err.print((char)快);
        System.err.print((char)乐);*/
        //List<Integer> hour = Arrays.asList(new Integer[]{1,2,4,8});
        /*Integer[] hour = new Integer[]{32,16,8,4,2,1};
        Set<Set<Integer>> sets = randomSelectN(hour,4);
        sets.forEach(e->{
            e.forEach(a->{
                System.out.print(a);
                System.out.print(",");
            });
            System.out.print("\n");
        });*/


        List<String> list = readBinaryWatch(5);
        System.out.println(list);


    }
}
