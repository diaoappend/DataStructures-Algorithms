package com.diao.algorithms.search;

/**
 * @author: chenzhidiao
 * @date: 2020/6/3 19:58
 * @description: 非递归实现的二分查找
 * @version: 1.0
 */
public class BinarySearchNoRecursion {

    public static void main(String[] args) {
        int[] arr = {1,3,5,7,9,12,14,15,18,23,56,67};
        int index =binarySearch(arr,0,arr.length-1,56);
        System.out.println(index);

    }

    public static int binarySearch(int[] arr,int begin,int end,int target){
        while (begin<=end){
            int mid = (begin+end)/2;
            if (arr[mid]==target){
                return mid;
            }
            //如果数组中间值比目标值大，说明在左边，那么把end修改为mid-1，然后在左边继续二分查找
            if (arr[mid]>target){
                end=mid-1;
            }else{
                begin=mid+1;
            }
        }
        return -1;
    }
}
