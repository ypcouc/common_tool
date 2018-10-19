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

    static class StopMsgException extends RuntimeException {
    }

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


    /**
     * 给定一个字符串 S，找出 S 中不同的非空回文子字符串个数，并返回该数字与 10^9 + 7 的模。
     * @param S
     * @return
     */
    public static int countPalindromicSubsequences(String S) {
        if(S == null || S.length() == 0){
            return -1;
        }
        long n = 0;
        //String[] data = {"a", "b", "c", "d"};
        char[] chars = S.toCharArray();
        Map<String,Integer> map = new HashMap<>();
        Map<String,List<Integer>> indexMap = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            Integer value = map.get(chars[i]+"");
            List<Integer> indexs = indexMap.get(chars[i]+"");
            if(indexs == null){
                indexs = new ArrayList<>();
            }
            indexs.add(i);
            map.put(chars[i]+"",value==null?1:value+1);
            indexMap.put(chars[i]+"",indexs);
        }
        /*Long[] totalNum = {0L};
        try {
            palindrome(map,indexMap,S.length(),totalNum);
        } catch (Exception e) {
        }
        Long l = totalNum[0]%((long)Math.pow(10,9)+ 7);*/
        return palindromez(map,indexMap,S.length());
    }

    /**
     * 判断字符串是否是回文字符串
     * @param s
     * @param low
     * @param high
     * @return
     */
    public Boolean palindromeNumber(String s, int low, int high) {
        if (low == high){
            return true;
        } else if (low < high) {
            if (s.charAt(low) == s.charAt(high) && (high - low) == 1) {
                return true;
            }else if (s.charAt(low) == s.charAt(high) && (high - low) != 1) {
                return palindromeNumber(s, low + 1, high - 1);
            }else {
                return false;
            }
        } else{
            return false;
        }
    }

    public static List<List<String>> partition(String s) {
        List<List<String>>lists=new ArrayList<List<String>>();
        List<String>list=new ArrayList<String>();
        partitionHelper(lists,list,s);
        return lists;
    }
    public static void partitionHelper(List<List<String>> lists, List<String> list, String s) {
        if (null == s || s.length() == 0) {
            lists.add(new ArrayList<>(list));
            return;
        }
        int len = s.length();
        for (int i = 1; i <= len;i++) {
            String subStr = s.substring(0, i);
            if (isPalindrome(subStr)) {
                list.add(subStr);
                partitionHelper(lists, list, s.substring(i, len));
                list.remove(list.size() - 1);
            }
        }
    }

    public static boolean isPalindrome(String s){
        if(s==null||s.length()==0) {
            return false;
        }
        int len=s.length()/2;
        for(int i=0;i<len;i++){
            if(s.charAt(i)!=s.charAt(s.length()-i-1)){
                return false;
            }
        }
        return true;
    }

    public static void combine(char[] a, int num, String b, int low, Set<String> result) {
        if (num == 0) {
            result.add(b);
        }else {
            for (int i = low; i < a.length; i++) {
                b += a[i];
                combine(a, num - 1, b, i + 1,result);
                b = b.substring(0, b.length() - 1);
            }
        }
    }

    /**
     * 生成回文串
     * @param map
     * @param indexMap
     * @param num
     * @param totalNum
     * @return
     */
    public static Set<String> palindrome(Map<String,Integer> map,Map<String,List<Integer>> indexMap,
                                         int num,Long[] totalNum){
        if(map == null || map.isEmpty() || totalNum == null || totalNum.length == 0){
            return new HashSet<>();
        }
        if(num == 0){
            return new HashSet<>();
        }
        Set<String> result = new HashSet<>();
        if(num == 1){
            map.forEach((k,v)->{
                if(v > 0){
                    result.add(k);
                    totalNum[0]++;
                }
            });
            return result;

        }
        Set<String> temp =  palindrome(map,indexMap,num-1,totalNum);
        if(temp != null && !temp.isEmpty()){
            temp.forEach(e->{
                if(num%2 == 0){
                    String midStr = e.substring(e.length()/2,e.length()/2+1);
                    int count = getCount(e.toCharArray(),midStr.charAt(0));
                    Integer value =  map.get(midStr);
                    if(value != null && value > count){
                        StringBuffer sb = new StringBuffer(e);
                        sb.insert(e.length()/2+1,midStr);
                        //判断是否是原子串
                        if (judgeSubStr(sb.toString(),indexMap)) {
                            result.add(sb.toString());
                            totalNum[0]++;
                        }
                    }
                }else {
                    map.forEach((k,v)->{
                        int count = getCount(e.toCharArray(),k.charAt(0));
                        if(v > count){
                            StringBuffer sb = new StringBuffer(e);
                            sb.insert(e.length()/2,k);
                            if (judgeSubStr(sb.toString(),indexMap)) {
                                result.add(sb.toString());
                                totalNum[0]++;
                            }
                        }
                    });
                }
            });
        }else {
            throw new StopMsgException();
        }
        return result;
    }

    /**
     * 生成回文串
     * @param map
     * @param indexMap

     * @return
     */
    public static int palindromez(Map<String,Integer> map,Map<String,List<Integer>> indexMap,
                                         int length){
        Long[] totalNum = {0L};
        if(map == null || map.isEmpty() || length == 0
            || indexMap == null || indexMap.isEmpty()){
            return -1;
        }
        Set<String> result = new HashSet<>();
        map.forEach((k,v)->{
            if(v > 0){
                result.add(k);
                totalNum[0]++;
            }
        });
        Integer[] num = {2};
        while (num[0] <= length && !result.isEmpty()){
            Set<String> temp = new HashSet<>(result);
            result.clear();
            temp.parallelStream().forEach(e->{
                if(num[0]%2 == 0){
                    String midStr = e.substring(e.length()/2,e.length()/2+1);
                    int count = getCount(e.toCharArray(),midStr.charAt(0));
                    Integer value =  map.get(midStr);
                    if(value != null && value > count){
                        StringBuffer sb = new StringBuffer(e);
                        sb.insert(e.length()/2+1,midStr);
                        //判断是否是原子串
                        if (judgeSubStr(sb.toString(),indexMap)) {
                            result.add(sb.toString());
                            synchronized (totalNum[0]) {
                                totalNum[0]++;
                            }
                        }
                    }
                }else {
                    map.forEach((k,v)->{
                        int count = getCount(e.toCharArray(),k.charAt(0));
                        if(v > count){
                            StringBuffer sb = new StringBuffer(e);
                            sb.insert(e.length()/2,k);
                            if (judgeSubStr(sb.toString(),indexMap)) {
                                result.add(sb.toString());
                                synchronized (totalNum[0]) {
                                    totalNum[0]++;
                                }
                            }
                        }
                    });
                }
            });

            num[0]++;
        }

        Long l = totalNum[0]%((long)Math.pow(10,9)+ 7);
        return l.intValue();
    }

    /**
     * 得到字符数组中目标字符出现的此次
     * @param chars
     * @param tar
     * @return
     */
    public static int getCount(char[] chars,char tar){
        int count = 0;
        if(chars != null && chars.length >0){
            for (int i = 0; i < chars.length; i++) {
                if(tar == chars[i]){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 判断字符串是不是原字符子串
     * @param str
     * @param map
     * @return
     */
    public static boolean judgeSubStr(String str,Map<String,List<Integer>> map){
        boolean result = true;
        if(str == null || str.length() == 0 ||
            map == null || map.isEmpty()){
            return false;
        }
        char[] chars = str.toCharArray();
        //记录前一个最小下标
        int n = 0;
        for (int i = 0; i < chars.length-1; i++) {
            List<Integer> pre = map.get(chars[i]+"");
            List<Integer> after = map.get(chars[i+1]+"");
            int k = 0;
            while (k < after.size()){
                if(pre.get(n) < after.get(k)){
                    n = k;
                    break;
                }
                k++;
            }
            if(k == after.size()){
                return false;
            }
        }
        return result;
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


       /* List<String> list = readBinaryWatch(5);
        System.out.println(list);*/
        /*String s="bccb";
        List<List<String>> lists = partition(s);
        System.out.println(lists);*/
        /*char[] a ={'b','c','c','b'};
        int num = 2;
        String b = new String();
        int low=0;
        int high=2;
        Set<String> result = new HashSet<>();
        combine(a,num,b,low,result);
        System.out.println(result);*/
        Long start = System.currentTimeMillis();
        String s = "abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba";
        //String s = "dbaaaacbaccdbddbddcabdddcaaccdcdbaaaadcbbdabdaacac";
        //String s = "dbcbaaacdcbabcbddaac";
        int n = countPalindromicSubsequences(s);
        Long end = System.currentTimeMillis();
        System.out.println(end-start);
        System.out.println(n);
    }
}
