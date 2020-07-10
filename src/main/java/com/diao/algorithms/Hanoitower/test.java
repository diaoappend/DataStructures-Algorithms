package com.diao.algorithms.Hanoitower;

/**
 * @author: chenzhidiao
 * @date: 2020/6/4 8:38
 * @description:
 * @version: 1.0
 */
public class test {
    private static int count =0;
    public static void main(String[] args) {
        hanoiTower(20,'A','B','C');
        System.out.println(count);
    }
    public static void hanoiTower(int num,char a,char b,char c){
        if (num==1){
            System.out.println("第"+num+"个盘："+a+"->"+c);
            count++;
        }else{
            hanoiTower(num-1,a,c,b);
            System.out.println("第"+num+"个盘："+a+"->"+c);
            count++;
            hanoiTower(num-1,b,a,c);
        }
    }

}
