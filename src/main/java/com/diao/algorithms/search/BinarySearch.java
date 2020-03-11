package com.diao.algorithms.search;

import java.lang.reflect.Array;

/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/3/10 20:57
 * @description:二分查找 前提：二分查找的前提是数组内的元素必须是有序的
 * 思想：找到数组的中间值，和需要查找的值进行对比：如果中间值等于查找值，直接返回中间值下标；如果中间值大于查找值，则递归向左边查找；如果中间值小于查找值，则递归向右边查找，直到找完所有的元素
 * 递归的结束条件——数组的开始下标 > 结束下标
 */
public class BinarySearch {
    /**
     * @param arr     查找数组
     * @param left    左边索引
     * @param right   右边索引
     * @param findVal 要查找的值
     * @return 如果找到，返回数值下标；如果没找到，返回-1
     */
    public static int binarySearch(int[] arr, int left, int right, int findVal) {
        if (left > right) {
            return -1;
        }
        int mid = (left + right) / 2;
        if (findVal < arr[mid]) {
            return binarySearch(arr, left, mid - 1, findVal);
        } else if (findVal > arr[mid]) {
            return binarySearch(arr, mid + 1, right, findVal);
        } else {
            return mid;
        }
    }

    public static void main(String[] args) {
        int[] arr = {0,3,7,12,45,466,1234};
        int findVal=1111;
        int index = binarySearch(arr,0,arr.length-1,findVal);
        System.out.println("index: " + index);
    }
}
