package com.diao.algorithms.sort;

import java.util.Arrays;

/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/3/29 22:56
 * @description:堆排序
 */
public class HeapSort {
    public static void main(String[] args) {
        //将下面的数组用堆排序按升序排列
        int[] arr = {4, 6, 8, 5, 9};
        heapSort(arr);
    }

    public static void heapSort(int[] arr) {
        System.out.println("堆排序！！");
        int tmp = 0;
        //将给定数组构造成大顶堆,i是给定数组对应二叉树结构最右下方非叶子节点的下标
        //调整后的大顶堆对应的数组，下标为0的值为数组的最大值
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }
        //交换堆顶和数组末尾的值，然后调整 除数组末尾元素外 的其它元素继续成为大顶堆，重复交换过程
        for (int j = arr.length - 1; j > 0; j--) {
            tmp = arr[j];
            arr[j] = arr[0];
            arr[0] = tmp;
            //继续调整结构
            //这里由于交换了堆顶和数组末尾元素，最上面的局部子树不再满足大顶堆，
            //然后从上向下调整树为大顶堆，而这个顶部子树的非叶子节点下标就是0（堆顶元素）
            adjustHeap(arr, 0, j);
        }
        System.out.println("数组:" + Arrays.toString(arr));

    }

    /**
     * 功能：完成 将以i为非叶子节点的树调整成大顶堆
     *
     * @param arr    待调整的数组
     * @param i      非叶子节点在数组中的下标
     * @param length 对多少个元素进行调整（每次调整后，交换根节点（数组头部元素）和数组末尾元素，这个值就减少1）
     */
    public static void adjustHeap(int[] arr, int i, int length) {
        int tmp = arr[i];//先取出当前非叶子节点的值，保存在临时变量中
        //开始调整
        /*
        说明：
        1.k = 2 * i + 1，k是该非叶子节点的左子节点
         */
        for (int k = 2 * i + 1; k < length; k = k * 2 + 1) {//这里注意是比较k和length的大小，而不是arr.length，因为给定数组后，arr.length是固定不变的
            if (k + 1 < length && arr[k] < arr[k + 1]) {//说明非叶子节点的右子节点的值大于左子节点的值
                k++;//将k指向值大的那个子节点
            }
            if (arr[k] > tmp) {//说明左（或者右）子节点的值大于父节点
                arr[i] = arr[k];//把较大的值赋给父节点
                i = k;//!!!  i指向k，继续循环比较（因为上面临时变量保存的是arr[i]的值，所有要更新i的值为k）
            } else {
                break;
            }
        }
        //当for循环结束后，已经将以i为父节点的局部子树的最大值放在了该子树的最顶上
        arr[i] = tmp;//将tmp值放到调整后的位置
    }
}
