package com.diao.algorithms.recursion;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/8 9:01
 * @description:阶乘
 * @version: 1.0
 */
public class RecursionDemo {
    public static void main(String[] args) {
        test(5);
    }

    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        }
        System.out.println("n: " + n);
    }

    /**
     * 阶乘问题
     *
     * @param n
     * @return
     */
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;
        }
    }
}
