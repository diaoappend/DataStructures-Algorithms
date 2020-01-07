package com.diao.datastructures.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/7 15:58
 * @description:计算后缀表达式（也叫逆波兰表达式）
 * @version: 1.0
 */
public class SuffixCalculator {
    public static void main(String[] args) {
        //中缀表达式：(3+4)*5-6，对应的后缀表达式(即逆波兰表达式)："3 4 + 5 * 6 -"(中间用了空格进行分割，便于使用)
        String suffixExpression = "3 4 + 5 * 6 -";
        /**
         * 逆波兰表达式的计算思路：
         * 1.扫描表达式，如果是数直接入栈；
         * 2.如果是运算符，从栈中弹出两个数进行运算，然后将结果入栈；
         * 3.最后栈中剩余的一个值即为表达式的值
         */
        List<String> list = getListString(suffixExpression);
        int result = calculator(list);
        System.out.println(result);

    }

    /**
     * 将逆波兰表达式转换为List集合
     * @param suffixExpression
     * @return
     */
    public static List<String > getListString(String suffixExpression){
        String[] items = suffixExpression.split(" ");
        ArrayList<String> ls = new ArrayList<>();
        for (String item : items) {
            ls.add(item);
        }
        return ls;
    }

    /**
     * 计算逆波兰表达式的值
     * @param ls
     * @return
     */
   public static int calculator(List<String> ls){
       Stack<String> stack = new Stack<>();
       //遍历ls
       for (String item : ls) {
           if (item.matches("\\d+")){//如果是数直接入栈
               stack.push(item);
           }else{
               int num2=Integer.parseInt(stack.pop());
               int num1=Integer.parseInt(stack.pop());
               int res=0;
               if (item.equals("+")){
                   res=num1+num2;
               }else if (item.equals("-")){//后缀表达式用后取出的数-先取出的数
                   res=num1-num2;
               }else if (item.equals("*")){
                   res=num1*num2;
               }else if(item.equals("/")){
                   res=num1/num2;
               }else{
                   throw new RuntimeException("运算符有误");
               }
               stack.push(res+"");
           }
       }
       //栈最后剩下的数即为运算结果
       return Integer.parseInt(stack.pop());
   }

}
