package com.ypc.alg;

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
    }
}
