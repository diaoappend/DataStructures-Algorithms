package com.diao.algorithms.Hanoitower;

/**
 * @author: chenzhidiao
 * @date: 2020/6/3 21:14
 * @description: 汉诺塔
 * 思路分析：
 * 1) 如果只有一个盘， A->C
 * 如果我们有 n >= 2 情况，我们总是可以看做是两个盘 1.最下边的盘 2. 上面所有的盘
 * 2) 先把最上面的所有盘从A->B
 * 3) 把最下边的盘 A->C
 * 4) 把B塔的所有盘从B->C
 * @version: 1.0
 */
public class HanoiTower {
    public static void main(String[] args) {
        hanoiTower(10, 'A', 'B', 'C');
    }

    /**
     * @param num 盘的数量
     * @param a   最开始放盘的柱子
     * @param b   辅助柱子
     * @param c   最终放盘的柱子
     */
    public static void hanoiTower(int num, char a, char b, char c) {
        if (num == 1) {
            System.out.println("第1个盘" + "从" + a + "->" + c);
        } else {
            //把最上面的所有盘移动到b柱
            hanoiTower(num - 1, a, c, b);
            //把最下面的盘从a移动到c
            System.out.println("第" + num + "个盘从" + a + "->" + c);
            //把辅助柱子b的所有盘移动到c
            hanoiTower(num - 1, b, a, c);
        }
    }
}
