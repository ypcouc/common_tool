package com.ypc.common.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SortUtils
 * @Description TODO
 * @Author JWD
 * @Date 2018/9/9
 * @Version 1.0
 **/
public class SortUtils {

    /**
     * 快速排序
     * @param arry
     * @param low
     * @param high
     */
    public static void quickSort(int[] arry,int low,int high){
        if(low >= high){
            return;
        }
        int middle = getFastMiddle(arry,low,high);
        quickSort(arry,low,middle-1);
        quickSort(arry,middle+1,high);
    }

    /**
     * 获取快速排序中间位置
     * @param arry
     * @param low
     * @param high
     * @return
     */
    public static int getFastMiddle(int[] arry,int low,int high){
        //以low为关键字
        int key = arry[low];
        while (low < high) {
            //从后往前找到比key小的
            while (arry[high] > key && low < high){
                high--;
            }
            arry[low] = arry[high];
            //从前往后找到比key大的
            while (arry[low] < key && low < high){
                low++;
            }
            arry[high] = arry[low];
        }
        arry[high] = key;

        return low;
    }

    /**
     * 冒泡排序
     * @param arry
     */
    public static void bubbleSort(int[] arry){
        int i,j = arry.length;
        boolean flag = true;
        while (flag){
            flag = false;
            for (i = 1; i < j; i++) {
                if(arry[i] < arry[i-1]){
                    int temp = arry[i-1];
                    arry[i-1] = arry[i];
                    arry[i] = temp;
                    flag = true;
                }
            }
            j--;
        }
    }

    /**
     * 归并排序
     * @param arry
     */
    public static void mergeSort(int arry[],int low,int high){
        if(low >= high){
            return;
        }
        int middle = (low+high) >>> 1;
        mergeSort(arry,low,middle);
        mergeSort(arry,middle+1,high);

    }

    public static void merge(int arry[],int low,int middle,int high){
        int[] temp = new int[high-low+1];
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = {-1,-1};
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            int temp = target - nums[i];
            if(map.containsKey(temp)){
                result[0] = map.get(temp);
                result[1] = i;
                break;
            }
            map.put(nums[i],i);

        }
        return result;
    }

    public static int[] getTwoNumForTarget(int[] nums,int target){
        int[] result = {-1,-1};
        Map<Integer,Integer> map = new HashMap<>();
        int n = 0;
        outterLoop :while (n < target){
            for(int i = 0; i < nums.length; i++){
                int temp = target - n - nums[i];
                if(map.containsKey(temp)){
                    result[0] = temp;
                    result[1] = nums[i];
                    break outterLoop;
                }
                map.put(nums[i],i);
            }
            n++;
        }

        return result;
    }

    /**
     * 二分查找
     * @param arry
     * @param value
     * @return
     */
    public static int binarySearch(int[] arry,int value){
        int low = 0;
        int high = arry.length-1;
        while (low <= high){
            int middle = (low+high) >>> 1;
            if(arry[middle] < value){
                low = middle+1;
            }else if(arry[middle] > value){
                high = middle-1;
            }else {
                return middle;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        /*int[] arry = {6,23,3,67,55,99};
        System.out.println(Arrays.toString(arry));
        bubbleSort(arry);
        System.out.println(Arrays.toString(arry));
        int index = binarySearch(arry,1);
        System.out.println(index);*/
        int[] numbers = {3,2,4};
        int target=3;
        int[] result = getTwoNumForTarget(numbers,target);
        System.out.println(Arrays.toString(result));
    }

}
