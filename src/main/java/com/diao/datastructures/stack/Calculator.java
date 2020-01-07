package com.diao.datastructures.stack;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/6 14:56
 * @description:计算中缀表达式的值（不含括号）
 * @version: 1.0
 */
public class Calculator {
    public int CalculatorExpression(String expression) {
        ArrayStack numStack = new ArrayStack(10);
        ArrayStack operStack = new ArrayStack(10);
        int index = 0;//用于扫描
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int result = 0;
        char ch = ' ';//将每次扫描到的char保存到ch
        String keepNum = "";//用于拼接多位数
        while (true) {
            //依次得到expression的每一个字符
            ch = expression.substring(index, index + 1).charAt(0);
            //判断ch是否为字符
            if (operStack.isOper(ch)) {
                //再判断当前的符号栈是否为空
                if (!operStack.isEmpty()) {
                    //如果符号栈有操作符，就进行比较，如果当前的操作符的优先级小于或等于栈中的操作符，就需要从数栈中pop出两个数
                    //再从符号栈中pop出一个符号，进行运算，将得到的结果入数栈，然后操作符入符号栈；如果当前的操作符的优先级大于栈中的操作符，就直接入符号栈。
                    if (operStack.priority(ch) <= operStack.priority(operStack.peak())) {
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        result = numStack.cal(num1, num2, oper);
                        //运算结果入数栈
                        numStack.push(result);
                        //操作符入符号栈
                        operStack.push(ch);
                    } else {
                        operStack.push(ch);
                    }
                } else {
                    operStack.push(ch);//为空直接入栈
                }
            } else {
                //如果是数，则直接入数栈，这里需要注意的是，扫描到的数字为char类型，和真正的数字之间差48，需要将扫描到的数字-48
                /**
                 * 多位数处理思路：
                 * 1.当处理多位数时，不能发现是一个数就立即入栈，因为可能是多位数
                 * 2.在处理数时，需要向expression的表达式的index向后再看一位，如果是数则继续扫描，如果是符号则入栈
                 * 3.因此需要定义一个字符串变量用于拼接
                 */
                //numStack.push(ch - 48);
                //处理多位数
                keepNum += ch;
                //如果ch已经是expression的最后一位，则直接入栈，不需要看下一位
                if (index == expression.length() - 1) {
                    numStack.push(Integer.parseInt(keepNum));
                } else {
                    //判断下一个字符是不是数字，如果是则继续扫描，如果是运算符则入栈
                    if (operStack.isOper(expression.substring(index + 1, index + 2).charAt(0))) {
                        numStack.push(Integer.parseInt(keepNum));
                        //入栈后要清空keepNum，很重要，否则后面拼接的多位数会出现问题
                        keepNum = "";
                    }
                }
            }
            //让index+1，并判断是否扫描到expression的末尾
            index++;
            if (index >= expression.length()) {
                break;
            }
        }
        while (true) {
            //如果符号栈为空，则计算到最后的结果，数栈中只有一个数字
            if (operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            result = numStack.cal(num1, num2, oper);
            numStack.push(result);
        }
        return result;
    }

    public static void main(String[] args) {
        String expression = "700*2-5+1";//定义一个表达式
        Calculator calculator = new Calculator();
        int result = calculator.CalculatorExpression(expression);
        System.out.printf("表达式:%s=%d", expression, result);
    }
}

