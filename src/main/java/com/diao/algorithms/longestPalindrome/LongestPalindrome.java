package com.diao.algorithms.longestPalindrome;

/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/3/30 22:18
 * @description: 获取给定字符串的最长回文子句
 * 回文子句：一个句子从前面读和从后面读一样，比如abcba，112232211……
 */
public class LongestPalindrome {

    /**
     * 解法1：暴力求解，时间复杂度O（n^3)
     *
     * @param s 给定字符串
     * @return 三层嵌套循环，第一层截取子串，第二层定义子串位置，第三次从两侧遍历对比字符
     */
    public String method1(String s) {
        if (s.length() <= 1) {//如果只有一个字符，最长回文子句就是本身，直接返回
            return s;
        }
        for (int i = s.length(); i > 0; i--) {//从最长子串（即字符串本身）开始循环，长度递减至1
            for (int j = 0; j <= s.length() - i; j++) {//子串的起始位置从0开始，直到母串长度-子串长度为止
                String sub = s.substring(j, j + i);//截取子串，从j开始，长度为i
                int count = 0;//计数，用来统计前后字符一致的个数
                for (int k = 0; k < sub.length() / 2; k++) {//k是子串的下标，从0开始
                    if (sub.charAt(k) == sub.charAt(sub.length() - 1 - k)) {//如果子串首尾距离一致时字符也一致，则计数器+1
                        count++;
                    }
                }
                if (count == sub.length() / 2) {//如果计数器=子串长度的一半，说明这个子串就是最长回文子句
                    return sub;
                }
            }
        }
        return "";
    }
}
