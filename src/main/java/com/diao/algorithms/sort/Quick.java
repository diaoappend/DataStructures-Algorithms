package com.diao.algorithms.sort;

import java.lang.reflect.Array;

/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/3/10 9:31
 * @description:快速排序
 * 最好时间复杂度：O(NlogN)
 * 最差时间复杂度：O(N^2)
 * 空间复杂度：O(N)
 * 说明；选一个中间值，把数组中比它小的元素放到左边，比它大的元素放到右边，这时形成三个子数组，分别是中间值
 * 比它大的数和比它小的数，然后对前后两个数组进行递归
 */
public class Quick {
    public static int[] quickSort(int[] arr,int begin,int end){
        //递归必须满足条件：起始值小于终止值
        if(begin<end){
            //这个方法是确定数组哨兵在排序后的最终位置下标，由于哨兵左边的数都小于哨兵，右边的数都大于哨兵，所以对两边子数组进行递归
            int index = partition(arr,begin,end);
            //对左边的子数组进行递归
            quickSort(arr,begin,index-1);
            //对右边的数组进行递归
            quickSort(arr,index+1,end);
        }
        return arr;
    }
    public static int partition(int[] arr,int begin,int end){
        //将数组的最后一个元素作为哨兵
        int pivot = arr[end];
        //i为起始坐标-1，注意不一定是(-1)
        int i = begin -1;
        //对数组进行遍历，j从数组的第一个下标开始，当j对应元素小于哨兵时，i加1，交换i和j对应元素
        for (int j = begin; j < end; j++) {//这里j从begin开始，而不是从0开始
            //当
            if(arr[j]<=pivot){//这里注意是小于等于
                i++;
                int tmp = arr[i];
                arr[i]=arr[j];
                arr[j]=tmp;
            }
        }
        //遍历结束后，交换arr[i+1]和哨兵的值
        int tmp = arr[i+1];
        arr[i+1]=arr[end];//这里不能用pivot来代替arr[end]
        arr[end]= tmp;
        //i+1就是哨兵最终排序后对应的下标
        return i+1;
    }

    /**
     * 求数组中第k小的值
     * 分析：第k小的值，就是下标为k-1的值
     * 如果要找第k大的值，只需将数组降序排列（partition方法中的判断条件if(arr[j]>=pivot)改为大于等于即可）
     * 然后找到下标为k-1的值即可
     * @param arr
     * @param begin
     * @param end
     * @param k 第k小的值
     * @return
     */
    public static int quickSortKMin(int[] arr,int begin,int end,int k){
        //递归必须满足条件：起始值小于等于终止值
        if(begin<=end){
            //这个方法是确定数组哨兵在排序后的最终位置下标，由于哨兵左边的数都小于哨兵，右边的数都大于哨兵，所以对两边子数组进行递归
            int index = partition(arr,begin,end);
            //如果哨兵的下标就等于k-1，那哨兵就是数组中第k小的值，直接输出
            if(index==k-1){
                return arr[index];
            }else if(index > k-1){
                //如果k-1小于哨兵的下标，说明在哨兵左侧，递归查找即可
                return quickSortKMin(arr,begin,index-1,k);
            }else{
                //如果k-1大于哨兵的下标，说明在哨兵右侧，递归查找即可
                return quickSortKMin(arr,index+1,end,k);
            }
        }
        return Integer.MIN_VALUE;
    }

    public static void main(String[] args) {
        int[] arr = {7, 5, 11, 0, 10, 5, 17, 15};
        int[] ints = quickSort(arr, 0, arr.length - 1);
        for (int anInt : ints) {
            System.out.print(anInt + ";");
        }
    }
}
