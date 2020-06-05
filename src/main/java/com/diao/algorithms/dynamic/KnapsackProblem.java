package com.diao.algorithms.dynamic;

/**
 * @author: chenzhidiao
 * @date: 2020/6/3 23:19
 * @description: 动态规划解决背包问题
 * @version: 1.0
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        int[] weight = {1, 4, 3};//物品的重量
        int[] value = {1500, 3000, 2000};//物品的价值

        int m = 4;//背包的容量
        int n = value.length;//物品的个数

        //创建二维数组 v[i][j]表示前i个物品在容量为j的背包中的最大价值
        int[][] v = new int[n + 1][m + 1];
        //为了记录放入商品的情况，定义一个二维数组
        int[][] path = new int[n + 1][m + 1];

        //初始化第一行和第一列，这里均为0
        for (int i = 0; i < v[0].length; i++) {
            v[0][i] = 0;//初始化第一行为0
        }
        for (int j = 0; j < v.length; j++) {
            v[j][0] = 0;//初始化第一列为0
        }
        //根据前面的公式来动态规划处理
        for (int i = 1; i < v.length; i++) {//不处理第一行
            for (int j = 1; j < v[0].length; j++) {//不处理第一列
                if (weight[i - 1] > j) {//因为我们的程序i是从1开始的，因此原来的w[i]修改成w[i-1]
                    v[i][j] = v[i - 1][j];
                } else {
                    //说明：因为我们的i是从1开始的，因此公式需要调整成：
                    //v[i][j]=max{v[i-1][j],value[i-1]+v[i-1][j-w[i-1]]}
                    //v[i][j] = Math.max(v[i - 1][j], value[i - 1] + v[i - 1][j - weight[i - 1]]);
                    //为了记录商品存放到背包的情况，我们不能直接的使用上面的公式，需要使用 if-else 来体现公式
                    if (v[i - 1][j]<value[i - 1] + v[i - 1][j - weight[i - 1]]) {
                        v[i][j] = value[i - 1] + v[i - 1][j - weight[i - 1]];
                        //把当前结果记录到path
                        path[i][j]=1;
                    }else{
                        v[i][j]=v[i - 1][j];
                    }
                }
            }
        }

        //输出v
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }
        //输出最后我们是放入的哪些商品,这里不能正序遍历，而应该倒序进行遍历
        int i = path.length-1;
        int j = path[i].length-1;
        while (i>0&&j>0){
            if (path[i][j]==1){
                System.out.printf("第%d个商品放入到背包\n", i);
                //每放入一个物品，背包容量就要减去物品的容量,因为二维数组v和path是通过背包容量m+1和物品数量n+1得到
                //所以这里j应该减去weigh[i-1]的容量
                j-=weight[i-1];
            }
            i--;
        }
    }
}
