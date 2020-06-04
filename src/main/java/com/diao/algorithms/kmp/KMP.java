package com.diao.algorithms.kmp;

/**
 * @author: chenzhidiao
 * @date: 2020/6/4 21:10
 * @description: KMP算法，是一个解决模式串在文本串是否出现过，如果出现过，最早出现的位置的经典算法
 * 部分匹配表就是前缀和后缀的最长的共有元素的长度
 * 以'ABCDABD'为例
 * 'A'的前缀和后缀都为空集，共有元素的长度为0
 * 'AB'的前缀为'A'，后缀为'B'，共有元素的长度为0
 * 。。。
 * 'ABCDA'的前缀有[A,AB,ABC,ABCD],后缀有[BCDA,CDA,DA,A],有1个共有元素'A'，而'A'的长度为1
 * 'ABCDAB'的前缀有[A,AB,ABC,ABCD,ABCDA],后缀有[BCDAB,CDAB,DAB,AB,B],有1个共有元素'AB'，所以长度为2
 * 。。。
 * 最终，模式串'ABCDABD'的部分匹配值表就是：
 * A B C D A B D
 * 0 0 0 0 1 2 0
 * @version: 1.0
 * <p>
 * KMP查找步骤：
 * 1。构建部分匹配表
 * 2。利用部分匹配表进行查找,这里高效查找的原理是：在遇到不匹配的字符时，不是每次都回到第一次匹配字符的后一个位置，而是通过部分匹配表，来计算回到的位置
 *      具体回到初次匹配字符的后几位，要看本次匹配的位数和部分匹配表对应位置值的差值。比如，匹配了ABCDAB 6位，而部分匹配表中第6位对应值位2，那么就回到
 *      第一次匹配字符的后面4位
 */
public class KMP {

    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";

        int[] next = kmpNext(str2);
        int index = kmpSearch(str1, str2, next);
        System.out.println(index);
    }

    /**
     * 查找原字符串中是否包含子串
     * @param str1 原字符串
     * @param str2 子串
     * @param next 部分匹配表
     * @return -1表示没有匹配到，否则返回第一个匹配的位置
     */
    public static int kmpSearch(String str1,String str2,int[] next){
        for (int i=0,j=0;i<str1.length();i++){
            //需要处理 str1.charAt(i) != str2.charAt(j) ，去重新调整j
            //KMP的核心点
            while (j>0&&str1.charAt(i) != str2.charAt(j)){
                j=next[j-1];
            }

            if (str1.charAt(i)==str2.charAt(j)){
                j++;
            }
            if (j==str2.length()){//说明找到了子串
                return i-j+1;
            }
        }
        return -1;
    }
    /**
     * 获取子串的部分匹配表
     * @param dest 子串
     * @return
     */
    public static int[] kmpNext(String dest) {
        //构建一个数组用了保持部分匹配表
        int[] next = new int[dest.length()];
        next[0] = 0;//如果字符串长度为1（下标为0），没有前缀和后缀，部分匹配值就是0

        for (int i = 1, j = 0; i < dest.length(); i++) {
            //当 dest.charAt(i) != dest.charAt(j) ，我们需要从 next[j-1]获取新的 j
            //直到我们发现 有 dest.charAt(i) == dest.charAt(j)成立才退出
            //这是 kmp 算法的核心点
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }
            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
