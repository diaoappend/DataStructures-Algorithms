package com.diao.algorithms.recursion;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/8 11:18
 * @description:八皇后问题（92种）
 * @version: 1.0
 * 问题描述：8*8格的国际象棋盘上摆8个皇后，使其不能相互攻击，即：任意两个皇后都不能处于同一行、同一列或同一斜线上，问有多少中摆法
 * 思路分析：
 * 1.第一个皇后先放第一行第一列
 * 2.第二个皇后放在第二行第一列，然后判断是否OK，如果不OK，继续放在第二列，第三列...依次把所有列都放完，找到一个合适的位置
 * 3.继续放第三个皇后，还是第一列，第二列...直到第八个皇后也能放在一个不冲突的位置，算是找到了一个正确解
 * 4.当得到一个正确解时，在栈回退到上一个栈时，就会开始回溯，即将第一个皇后，放到第一列的所有正确解，全部得到。
 * 5.然后回头继续第一个皇后放第二列，后面继续循环执行1,2,3,4步骤
 * <p>
 * 说明：理论上应该创建一个二维数组来表示棋盘，但是实际上可以通过算法，用一个一维数组即可解决问题。
 * 比如：arr[8]={0,4,7,5,2,6,1,3},对应arr下标表示第几行，即第几个皇后，arr[i]=val，val表示第i+1个皇后，放在第i+1行的第val+1列
 */
public class EightQueens {
    int max = 8;//皇后的数量
    int[] arr = new int[max];
    int count=0;
    int judgeCount=0;

    public static void main(String[] args) {
        EightQueens eightQueens = new EightQueens();
        eightQueens.check(0);
        System.out.println("count: "+eightQueens.count);
        System.out.println("judgeCount: "+eightQueens.judgeCount);
    }

    /**
     * 放置第n个皇后
     * 特别注意：check 是每一次递归时，进入到check中都有 for (int i = 0; i < max; i++) ，因此会有回溯
     * @param n 第几个皇后，也就是第几行
     */
    public void check(int n) {
        if (n == max) {  //当n=8时，其实已经在放第9个了，说明前8个已经放置完毕
            print();
            return;
        }
        //依次放入皇后，并判断是否冲突   <关键代码就是这个for循环>
        //如何做到回溯：比如放置了前三个皇后，放第四个时放到所有列都冲突，这时就要回溯调整第三个皇后到下一列，然后继续判断是否冲突，直到不冲突再放第四个
        //假设当n=3时表示放第四个，如果i从0-7第四个都冲突，那么就会返回上层调用，即n=2，然后让i+1，再判断是否冲突，不冲突再放第四个
        for (int i = 0; i < max; i++) {
            //先把当前这个皇后n,放在该行的第一列，这里的i表示第i+1列
            arr[n] = i;
            //检查是否冲突,judge()返回true表示不冲突
            if (judge(n)) {
                //接着放n+1，开始递归
                check(n + 1);
            }
            //如果冲突则继续执行arr[n]=i;即将第n个皇后，放置在本行的 后移一个位置
        }
    }

    /**
     * 查看当前放置的第n个皇后，就去检测该皇后是否和已经摆放的皇后冲突
     * @param n
     * @return
     */
    public boolean judge(int n) {
        judgeCount++;
        for (int i = 0; i < n; i++) {//i<n说明肯定不在同一行
            //arr[i]==arr[n]说明在同一列；
            //Math.abs(n-i)==Math.abs(arr[n]-arr[i])说明在同一斜线，abs()取绝对值，如果两个皇后相距n行，同时他们相距n列，则表示在同一斜线
            if (arr[i] == arr[n] || Math.abs(n - i) == Math.abs(arr[n] - arr[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将皇后摆放的位置输出
     */
    public void print() {
        count++;
        for (int i : arr) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
