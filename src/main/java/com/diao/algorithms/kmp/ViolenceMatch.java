package com.diao.algorithms.kmp;

/**
 * @author: chenzhidiao
 * @date: 2020/6/4 20:46
 * @description: 暴力匹配，一次匹配不成功，整体回溯，效率比较低下
 * @version: 1.0
 */
public class ViolenceMatch {

    public static void main(String[] args) {
        String str1 = "山山虎 上山虎你上山 上山虎你上山虎你上山你好";
        String str2 = "上山虎你上山你";
        int index = violenceMatch(str1, str2);
        System.out.println(index);
    }

    public static int violenceMatch(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int str1Len = s1.length;
        int str2Len = s2.length;
        int i = 0;//字符数组s1的下标
        int j = 0;//字符数组s2的下标
        while (i < str1Len && j < str2Len) {
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                i = i - (j - 1);//如果i和j对应的字符不匹配，下标i回到第一次和j匹配位置的后一位的位置，因为一共移动了j位，所以回退j-1位
                j = 0;//j归零，然后str1从当前位置下标i开始继续和str2匹配
            }
        }
        if (j == str2Len) {//如果str1中有和str2完全匹配的字符串，那么j会从0一直增加到字符数组s2的长度
            return i - j;//str1中匹配str2子串的开始位置就是当前i的值减去j（由于j表示str2下标，当完全匹配时，j的值为str2长度-1，i-j的值就是开始下标，而不用再加1）
        } else {
            return -1;
        }
    }


}
