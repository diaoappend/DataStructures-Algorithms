package com.diao.algorithms.sort;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/9 8:53
 * @description:冒泡排序
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 * @version: 1.0
 * 优化：如果发现某趟排序中，没有发生一次交换，可以提前结束冒泡排序
 * 思路：定义一个boolean变量记录是否进行交换，当这个变量为false时，结束
 */
public class Bubbling {
    public static void main(String[] args) {
        /*int[] arr = {1, 6, 7, 50, 17};
        int[] sort = sort(arr);
        for (int i : sort) {
            System.out.print(i + " ");
        }*/

        int[] arr = new int[80000];
        for (int i=0;i<80000;i++){
            arr[i]=(int)(Math.random()*80000);
        }

        Date date1 = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = simpleDateFormat.format(date1);
        System.out.println("排序前时间="+date1Str);
        sort(arr);

        Date date2 = new Date();
        String date2Str = simpleDateFormat.format(date2);
        System.out.println("排序后时间="+date2Str);
    }

    /**
     * 升序排列
     *
     * @param arr
     */
    public static int[] sort(int[] arr) {
        int len = arr.length;
        boolean flag=false;
        int tmp = 0;
        for (int i = 0; i < len - 1; i++) {

            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag=true;
                    tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
            //如果内层循环未进行交换，即已排好序，直接结束；否则重置flag，继续循环。
            if (!flag) {
                break;
            }else {
                flag=false;
            }
        }
        return arr;
    }
}
