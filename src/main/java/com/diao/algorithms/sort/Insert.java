package com.diao.algorithms.sort;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/9 10:42
 * @description:插入排序
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 * @version: 1.0
 */
public class Insert {
    public static void main(String[] args) {
        int[] arr = {101,34,119,10};
        int[] sort = insertSort(arr);
        for (int i : sort) {
            System.out.println(i+" ");
        }
    }

    /**
     * 升序插入排序
     * @param arr
     * @return
     */
    public static int[] insertSort(int[] arr) {
        for (int i =1;i<arr.length;i++) {
            //定义插入的数，从数组第2个数开始
            int insertVal = arr[i];
            //定义插入位置，从插入数的前一个位置坐标开始依次往前判断
            int insertIndex = i - 1;
            //给insertVal找到插入的位置
            //说明：
            //1.insertIndex>=0保证插入位置下标不越界
            //2.arr[insertIndex] > insertVal 待插入的数小于前面的数，说明还没找到插入位置（因为是升序排，所以待插入值前面的值都是升序排好的，所以只要找到前面数小于待插入数时即找到位置）
            //3.需要将arr[insertIndex]后移：arr[insertIndex + 1] = arr[insertIndex]
            //4.insertIndex前移继续找位置
            while (insertIndex >= 0 && arr[insertIndex] > insertVal) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            //当退出while循环时，说明insertVal的位置已经找到，就是insertIndex+1
            arr[insertIndex + 1] = insertVal;
        }
        return arr;
    }
}
