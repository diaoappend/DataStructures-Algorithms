package com.diao.algorithms.sort;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/9 16:16
 * @description:希尔排序（也称为缩小增量排序）
 * 时间复杂度：O()
 * 空间复杂度：O(1)
 * @version: 1.0
 * 说明：希尔排序是对插入排序的一种优化，因为在插入排序中，如果升序排列时一个较小的数在最后，移动的次数会非常多，效率受影响
 */
public class Shell {
    public static void main(String[] args) {
        int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        int[] sort = moveShellSort(arr);
        for (int i : sort) {
            System.out.print(i + " ");
        }
    }

    /**
     * 采用交换法插入的希尔排序（效率低）
     * 对每个组中的元素两两进行比较，不满足顺序即交换
     * @param arr
     * @return
     */
    public static int[] transShellSort(int[] arr) {
        int tmp = 0;
        //增量从数组长度/2开始，之后每次都/2，只要得到的增量大于0，就一直循环
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                //遍历各组中所有的元素，(共gap组，每组有length/gap个元素)，步长为gap
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {//这里虽然定义j=i-gap,但这只是初始j的值，当j-=gap后这个等式便不再成立，所以交换时不能直接将arr[j]和arr[i]的值交换
                        tmp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = tmp;
                    }
                }
            }
        }
        return arr;
    }

    /**
     * 采用移位法插入的希尔排序（效率高）
     * 对每个组进行直接插入排序
     * @param arr
     * @return
     */
    public static int[] moveShellSort(int[] arr) {
        int tmp = 0;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            //从第gap个元素开始，逐个对其所在的组进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                tmp = arr[j];//这里的tmp就相当于插入排序中的insertVal，而j-gap就相当于insertIndex
                if (arr[j] < arr[j - gap]) {//gap所在的组，前面的数如果大于待插入的数，那么表示没有找到插入位置
                    while (j - gap >= 0 && arr[j - gap] > tmp) {
                        arr[j] = arr[j - gap];
                        j -= gap;//下标前移量为步长
                    }//当跳出while循环时，表示找到了插入位置
                    arr[j] = tmp;//当j-gap小于0时，tmp的位置下标就是j-gap+gap=j
                }
            }
        }
        return arr;
    }
}
