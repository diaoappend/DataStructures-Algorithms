package com.diao.datastructures.sparsearray;

import java.io.*;

/**
 * @author: Chenzhidiao
 * @date: 2019/12/30 20:02
 * @description:
 * @version: 1.0
 */
public class SparseArray {
    public static void main(String[] args) throws Exception {
        //创建原始二维数组
        int chessArr[][] = new int[6][7];
        chessArr[0][3] = 22;
        chessArr[0][6] = 15;
        chessArr[1][1] = 11;
        chessArr[1][5] = 17;
        chessArr[2][3] = -6;
        chessArr[3][5] = 39;
        chessArr[4][0] = 91;
        chessArr[5][2] = 28;
        //输出原始二维数组
        for (int[] row : chessArr) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        //将二维数组 转 稀疏数组
        //1. 先遍历二维数组，得到非0数据的个数
        int sum = 0;
        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr[i].length; j++) {
                if (chessArr[i][j] != 0) {
                    sum += 1;
                }
            }
        }
        //2. 创建对应的稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        //给稀疏数组赋值
        //给第一行赋值
        sparseArr[0][0] = chessArr.length;
        sparseArr[0][1] = chessArr[0].length;
        sparseArr[0][2] = sum;
        //遍历二维数组，将非0的值存放到稀疏数组中
        int count = 0;//count用于记录是第几个非0数据
        for (int i = 0; i < chessArr.length; i++) {
            for (int j = 0; j < chessArr[i].length; j++) {
                if (chessArr[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];
                }
            }
        }
        //遍历稀疏数组
        //FileWriter writer = new FileWriter(new File("e:/file.txt"));
        BufferedWriter buf = new BufferedWriter(new FileWriter(new File("e:/file.txt")));
        for (int i = 0; i < sparseArr.length; i++) {

            System.out.printf("%d\t%d\t%d\t\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }
        System.out.println();

        for (int i = 0; i < sparseArr.length; i++) {
            for (int j = 0; j < 3; j++) {
                buf.write(sparseArr[i][j] + "\t");
            }
            buf.newLine();
        }
        buf.close();

        //将稀疏数组 转 二维数组
        /**
         * ①先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的chessArr2=int[6][7]
         * ②再读取稀疏数组后面几行的数据，并赋给原始的二维数组
         */
        //先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        int[][] chessArr1 = new int[sparseArr[0][0]][sparseArr[0][1]];
        //再读取稀疏数组后面几行的数据，并赋给原始的二维数组
        for (int i = 1; i < sparseArr.length; i++) {
            chessArr1[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        //遍历二维数组
        for (int[] row : chessArr1) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
    }
}
