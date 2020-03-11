package com.diao.algorithms.sort;

/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/3/10 11:28
 * @description:归并排序
 * 时间复杂度；O(NlogN)
 * 空间复杂度：O(N)
 */
public class Merge {
    public static int[] mergeSort(int[] arr,int left,int right,int[] tmp){
        if (left<right){
            //只要left<right,就可以继续分,直到将所有元素全部打散成单个
            //取数组中间值下标
            int mid = (left+right)/2;
            //对数组中间元素左侧递归切分
            mergeSort(arr,left,mid,tmp);
            //对数组中间元素右侧递归切分
            mergeSort(arr,mid+1,right,tmp);
            //merge是合并的操作
            merge(arr,left,mid,right,tmp);
        }
        return arr;
    }

    public static void merge(int[] arr,int left,int mid,int right,int[] tmp){
        int i = left;//左边开始的下标
        int j = mid+1;//右边开始的下标
        int t = 0;//临时数组tmp的开始下标
        while(i<=mid&&j<=right){//说明左右两个数组都有数据
            if (arr[i]<=arr[j]){
                tmp[t]=arr[i];
                t++;
                i++;
            }else{
                tmp[t]=arr[j];
                t++;
                j++;
            }
        }
        //不满足第一个while条件说明有一侧数据已经全部拷贝到tmp数组中了
        while (i<=mid){//说明左边数据还有数，右边数组已空，将左边数组中的数依次放入临时数组
            tmp[t]=arr[i];
            i++;
            t++;
        }
        while(j<=right){
            tmp[t]=arr[j];
            j++;
            t++;
        }
        //将本次的tmp数组的数据拷贝到原数组arr中
        t=0;
        //注意这里原数组下标tmpLeft不从0开始，而是从left开始，因为
        int tmpLeft = left;
        while (tmpLeft<=right){
            arr[tmpLeft]=tmp[t];
            t++;
            tmpLeft++;
        }
    }
    public static void main(String[] args) {
        int[] arr = {7, 5, 11, 0, 10, 5, 17, 15};
        int[] tmp = new int[8];
        int[] ints = mergeSort(arr, 0, arr.length - 1,tmp);
        for (int anInt : ints) {
            System.out.print(anInt + ";");
        }
    }

}
