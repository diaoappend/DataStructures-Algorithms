package com.diao.datastructures.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/7 9:03
 * @description:中缀表达式转后缀表达式
 * @version: 1.0
 */
public class Mid2SuffixExpression {
    /**
     * 中缀表达式转后缀表达式步骤分析：
     * 1.初始化两个栈：运算符栈s1和中间结果栈s2；
     * 2.从左到右扫描中缀表达式；
     * 3.遇到操作数时，将其压入s2；
     * 4.遇到运算符时，比较其与s1栈顶运算符的优先级；
     * 4.1.如果s1为空，或运算符栈顶为左括号“（”，则直接将此运算符入栈；
     * 4.2.否则，若优先级比栈顶运算符的高，也将运算符压入s1（注意必须是高，等于或低于都不行）
     * 4.3.否则，将s1栈顶的运算符弹出并压入s2中，再次转到4.1与s1中新的栈顶运算符进行比较；
     * 5.遇到括号时：
     * 5.1.如果是左括号“（”，则直接压入s1；
     * 5.2.如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃；
     * 6.重复步骤2-5，直到表达式的最右边；
     * 7.将s1中剩余的运算符依次弹出兵压入s2；
     * 8.依次弹出s2中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式
     */
    public static void main(String[] args) {

        String infixExpression = "1+2+(3*(4+5-(6+7)+8)-9)+10";
        List<String> list = infixExpression2List(infixExpression);
        System.out.println(list);
        List<String> suffixList = infix2SuffixExpressionList(list);
        System.out.println(suffixList);

    }

    /**
     * 将中缀表达式转为对应的ArrayList集合
     * @param infixExpression 中缀表达式
     * @return
     */
    public static List<String> infixExpression2List(String infixExpression) {
        ArrayList<String> ls = new ArrayList<String>();
        int i = 0;
        String str;//对多位数进行拼接
        char ch;//每遍历到一个字符，就放入ch
        do {
            //如果ch是一个非数字，加入ls
            if ((ch = infixExpression.charAt(i)) < 48 || (ch = infixExpression.charAt(i)) > 57) {
                ls.add("" + ch);
                i++;
            } else {
                str = "";//先将str置空
                //这里判断i的范围是因为内层循环结束后才会判断外层循环条件，如果不加会在内层循环时出现下标越界
                while (i < infixExpression.length() && (ch = infixExpression.charAt(i)) >= 48 && (ch = infixExpression.charAt(i)) <= 57) {
                    str += ch;//拼接多位数
                    i++;
                }
                ls.add(str);
            }
        } while (i < infixExpression.length());
        return ls;
    }

    public static List<String> infix2SuffixExpressionList(List<String> infixList) {
        Stack<String> s1 = new Stack<String>();//操作符栈
        //由于在整个转换过程中s2栈都没有pop操作，而且后面需要逆序输出，为了简化，可以不用栈结构，改用List<String> s2代替
        List<String> s2 = new ArrayList<String>();

        //遍历infixList
        for (String item : infixList) {
            //如果是一个数，则入s2
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if (item.equals("(")) {//如果是左括号，直接入s1栈
                s1.push(item);
            } else if (item.equals(")")) {//如果是右括号，则依次弹出s1栈顶的运算符，并加入s2，直到遇到左括号为止，此时将这一对括号丢弃；
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                s1.pop();//去除s1中对应的左括号，很重要！！！
            } else {
                //如果是运算符，比较其与s1栈顶运算符的优先级：
                //1.如果s1为空，或运算符栈顶为左括号“（”，则直接将此运算符入栈；
                //2.否则，若优先级比栈顶运算符的高，也将运算符压入s1（注意必须是高，等于或低于都不行）
                //3.否则，将s1栈顶的运算符弹出并压入s2中，再次转到4.1与s1中新的栈顶运算符进行比较；
                //当item的优先级小于等于s1栈顶的优先级时，将s1栈顶的运算符弹出并加入s2中，再次转到1与s1中新的栈顶运算符进行比较
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    s2.add(s1.pop());
                }
                //将item入栈s1
                s1.push(item);
            }
        }
        //将s1中的剩余运算符弹出并加入s2
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        return s2;//由于加入的是一个List，本身有序，所以正常输出即为后缀表达式而不必逆序
    }
}

class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在的运算符");
                break;
        }
        return result;
    }
}
