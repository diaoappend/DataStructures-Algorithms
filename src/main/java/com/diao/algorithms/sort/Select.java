package com.diao.algorithms.sort;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/9 10:25
 * @description:选择排序
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 * @version: 1.0
 */
public class Select {
    public static void main(String[] args) {
        /*int[] arr = {1, 6, 7, 50, 17};
        int[] sort = selectSort(arr);
        for (int i : sort) {
            System.out.print(i + " ");
        }*/

        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date1);
        System.out.println("排序前时间=" + date1Str);
        selectSort(arr);

        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("排序后时间=" + date2Str);
    }

    public static int[] selectSort(int[] arr) {
        int tmp = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    min = arr[j];
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                arr[minIndex] = arr[i];
                arr[i] = min;
            }
        }
        return arr;
    }
}
