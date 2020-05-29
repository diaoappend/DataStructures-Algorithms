package com.diao;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/3/30 20:42
 * @description:
 */
public class sortTest {
    public static void main(String[] args) {
        int[] arr = {13, -1, 2, 5, 67, 11};
        int[] tmp = new int[6];
        int[] ints = heapSort(arr);
        System.out.println(Arrays.toString(ints));
    }

    public static int[] bublingSort(int[] arr) {
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
            if (!flag) {
                break;
            } else {
                flag = false;
            }
        }
        return arr;
    }

    public static int[] selectSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min) {
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

    public static int[] quickSort(int[] arr, int begin, int end) {
        if (begin < end) {
            int index = partition(arr, begin, end);
            quickSort(arr, begin, index - 1);
            quickSort(arr, begin + 1, end);
        }
        return arr;
    }

    public static int partition(int[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = begin - 1;
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        int tmp = arr[end];
        arr[end] = arr[i + 1];
        arr[i + 1] = tmp;
        return i + 1;
    }

    public static int[] mergeSort(int[] arr, int begin, int end, int[] tmp) {
        if (begin < end) {
            int mid = (begin + end) / 2;
            mergeSort(arr, begin, mid, tmp);
            mergeSort(arr, mid + 1, end, tmp);
            merge(arr, begin, mid, end, tmp);
        }
        return arr;
    }

    public static void merge(int[] arr, int begin, int mid, int end, int[] tmp) {
        int t = 0;
        int i = begin;
        int j = mid + 1;
        while (i <= mid && j <= end) {
            if (arr[i] >= arr[j]) {
                tmp[t] = arr[j];
                j++;
                t++;
            } else {
                tmp[t] = arr[i];
                i++;
                t++;
            }
        }
        while (i <= mid) {
            tmp[t] = arr[i];
            i++;
            t++;
        }
        while (j <= end) {
            tmp[t] = arr[j];
            j++;
            t++;
        }
        int leftIndex = begin;
        t = 0;
        while (leftIndex <= end) {
            arr[leftIndex] = tmp[t];
            leftIndex++;
            t++;
        }
    }

    public static ArrayList<Integer> binarySearch(int[] arr, int begin, int end, int findVal) {
        int mid = (begin + end) / 2;
        if (arr[mid] > findVal) {
            return binarySearch(arr, begin, mid - 1, findVal);
        } else if (arr[mid] < findVal) {
            return binarySearch(arr, mid + 1, end, findVal);
        } else {
            ArrayList<Integer> resIndexList = new ArrayList<>();
            int i = mid - 1;
            while (true) {
                if (i < 0 || arr[i] != findVal) {
                    break;
                }
                resIndexList.add(i);
                i--;
            }
            resIndexList.add(mid);
            i = mid + 1;
            while (true) {
                if (i > end || arr[i] != findVal) {
                    break;
                }
                resIndexList.add(i);
                i++;
            }
            return resIndexList;
        }
    }

    public static int[] heapSort(int[] arr){
        int tmp = 0;
        for (int i = arr.length/2-1;i>=0;i--){
            adjustHeap(arr,i,arr.length);
        }
        for (int j = arr.length-1;j>0;j--){
            tmp = arr[0];
            arr[0] = arr[j];
            arr[j] = tmp;
            adjustHeap(arr,0,j);
        }
        return arr;
    }

    public static void adjustHeap(int[] arr,int i,int length){
        int tmp =arr[i];
        for (int k = 2*i+1;k<length;k=k*2+1){
            if (k+1<length&&arr[k]<arr[k+1]){
                k++;
            }
            if (arr[k]>tmp){
                arr[i] = arr[k];
                i=k;
            }
            arr[i] = tmp;
        }
    }
}
