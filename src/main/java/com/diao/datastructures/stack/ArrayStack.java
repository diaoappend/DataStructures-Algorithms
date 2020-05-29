package com.diao.datastructures.stack;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/6 11:25
 * @description:基于数组实现栈
 * @version: 1.0
 */
public class ArrayStack {
    private int maxSize;//栈的大小
    private int[] stack;//数组模拟栈，数据存储在数组中
    private int top = -1;//top表示栈顶，初始化为-1

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    //判断栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    //判断栈空
    public boolean isEmpty() {
        return top == -1;
    }

    //入栈
    public void push(int data) {
        if (isFull()) {
            System.out.println("栈已满，无法入栈");
            return;
        }
        top++;
        stack[top] = data;
    }

    //出栈
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈为空");
        }
        int value = stack[top];
        top--;
        return value;
    }

    //遍历栈,需要从栈顶开始显示
    public void list() {
        if (isEmpty()) {
            System.out.println("栈为空");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.println(stack[i]);
        }
    }

    //返回运算符的优先级，优先级由程序员来确定，优先级使用数字表示，数字越大，优先级越高
    public int priority(int oper) {
        if (oper == '(' || oper == ')') {
            return 2;
        } else if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;//假定目前的表达式中只有+、-、*、/
        }
    }

    //判断扫描到的是不是一个运算符
    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/' || val == '(' || val == ')';
    }

    //查看栈顶元素的方法，只查看，不弹出
    public int peak() {
        return stack[top];
    }

    //计算两个值的方法，从数栈中取两个值，符号栈中取一个进行计算
    public int cal(int num1, int num2, int oper) {
        int result = 0;
        switch (oper) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num2 - num1;//注意顺序，要用第二个取出的数-diyige取出的数
                break;
            case '*':
                result = num2 * num1;
                break;
            case '/':
                result = num2 / num1;
                break;
            default:
                break;
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayStack stack = new ArrayStack(10);
        stack.push(34);
        stack.push(3);
        stack.push(7);
        stack.push(17);
        stack.push(23);
        stack.list();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.list();

    }
}


