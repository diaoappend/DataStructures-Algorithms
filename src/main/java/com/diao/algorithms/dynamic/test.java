package com.diao.algorithms.dynamic;

/**
 * @author: chenzhidiao
 * @date: 2020/6/4 8:09
 * @description:
 * @version: 1.0
 */
public class test {


    public static void main(String[] args) {
        int[] weight = {1,4,3};
        int[] value={1500,3000,2000};
        int m = 4;//背包容量
        int n = value.length;//物品个数
        int[][] v = new int[n+1][m+1];
        int[][] path = new int[n+1][m+1];
        for(int i =0;i<n+1;i++){
            v[i][0]=0;
        }
        for (int j=0;j<m+1;j++){
            v[0][j]=0;
        }
        //根据前面的公式来动态规划处理
        for (int i=1;i<v.length;i++){
            for (int j=1;j<v[0].length;j++){
                if (weight[i-1]>j){
                    v[i][j]=v[i-1][j];
                }else{
                    if (v[i-1][j]>value[i-1]+v[i-1][j-weight[i-1]]){
                        v[i][j]=v[i-1][j];
                    }else{
                        v[i][j]=value[i-1]+v[i-1][j-weight[i-1]];
                        path[i][j]=1;
                    }
                }
            }
        }
        for(int i=0;i<v.length;i++){
            for (int j=0;j<v[i].length;j++){
                System.out.print(v[i][j]+" ");
            }
            System.out.println();
        }

        int i = path.length-1;
        int j = path[i].length-1;
        while (i>0&&j>0){
            if (path[i][j]==1){
                System.out.printf("第%d个物品放入背包\n",i);
                j-=weight[i-1];
            }
            i--;
        }
    }

}
