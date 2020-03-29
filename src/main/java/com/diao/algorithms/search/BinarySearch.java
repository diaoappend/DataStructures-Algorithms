package com.diao.algorithms.search;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
     * 该方法只能返回第一次找到数值时的下标，如果数组中存在多个要查找的值，不能返回所有的下标
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

    /**
     * 当数据中包含多个查找的数值时，返回所有的下标
     * @param arr
     * @param left
     * @param right
     * @param findVal
     * @return 查找数值的所有下标的集合
     * 思路分析：
     * 1.在找到mid索引值后，不要马上返回；
     * 2.向mid索引值的左边扫描，将所有满足查找值的下标，添加到ArrayList集合中；
     * 3.向mid索引值的右边扫描，将所有满足查找值的下标，添加到ArrayList集合中；
     * 4.将ArrayList返回
     */
    public static ArrayList binarySearchAll(int[] arr, int left, int right, int findVal){
        if (left > right) {
            throw new RuntimeException("没有要查找的数！");
        }
        int mid = (left + right) / 2;
        if (findVal < arr[mid]) {
            return binarySearchAll(arr, left, mid - 1, findVal);
        } else if (findVal > arr[mid]) {
            return binarySearchAll(arr, mid + 1, right, findVal);
        } else {
            //定义一个存放下标的集合
            ArrayList<Integer> resIndexList = new ArrayList<>();
            int tmp = mid -1;
            //向左扫描，直到下标小于0或数值不为要查找的数
            while (true){
                if (tmp<0||arr[tmp]!=findVal){
                    break;
                }
                //如果mid左侧有要查找的数，将下标放入集合
                resIndexList.add(tmp);
                tmp--;
            }
            //将mid放入集合
            resIndexList.add(mid);
            //向右扫描，直到下标大于数组最大下标或数值不为要查找的数
            tmp = mid +1;
            while (true){
                if (tmp>arr.length-1||arr[tmp]!=findVal){
                    break;
                }
                resIndexList.add(tmp);
                tmp++;
            }
            return resIndexList;
        }
    }

    public static void main(String[] args) {
        int[] arr = {0,3,7,12,12,45,466,1234};
        int findVal=1111;
        ArrayList<Integer> arrayList = binarySearchAll(arr, 0, arr.length - 1, findVal);
        for (Integer o : arrayList) {
            System.out.println(o);
        }
    }
}
