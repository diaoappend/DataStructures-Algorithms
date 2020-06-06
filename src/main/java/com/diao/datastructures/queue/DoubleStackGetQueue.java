package com.diao.datastructures.queue;

import java.util.LinkedList;

/**
 * @author: chenzhidiao
 * @date: 2020/6/5 23:27
 * @description: 两个栈实现队列
 * 一个主栈和一个辅助栈：添加元素时，直接加入主栈；删除元素时，先将主栈的元素依次弹出压入辅助栈，实现逆序，然后从辅助栈顶开始输出元素
 * @version: 1.0
 */
public class DoubleStackGetQueue {
    LinkedList<Integer> stack1;//主栈
    LinkedList<Integer> stack2;//辅助栈

    public DoubleStackGetQueue(){
        stack1 = new LinkedList<>();
        stack2 = new LinkedList<>();
    }
    //添加元素直接压入主栈
    public void add(int value){
        stack1.add(value);
    }
    //删除元素时，先看辅助栈是否有元素，有直接弹出；如果没有，看主栈是否有元素，如果主栈也没有，返回-1；如果主栈有元素，把主栈的元素依次弹出并压入
    //辅助栈，然后在从辅助栈的栈顶开始弹出元素
    public int delete(){
        if (stack2.isEmpty()){
            if (stack1.isEmpty()) return -1;
            while (!stack1.isEmpty()){
                stack2.add(stack1.pop());
            }
            return stack2.pop();
        }else{
            return  stack2.pop();
        }
    }

    public static void main(String[] args) {
        int[] arr={};

        DoubleStackGetQueue queue = new DoubleStackGetQueue();
        queue.add(1);
        queue.add(4);
        queue.add(6);
        queue.add(11);
        System.out.println(queue.delete());
        System.out.println(queue.delete());
        System.out.println(queue.delete());

    }
    public int search(int[] nums,int begin,int end,int target){
        if(begin<end){
            int mid = (begin+end)/2;
            if(nums[mid]==target){
                return mid;
            }
            if(nums[mid]>target){
                search(nums,begin,mid-1,target);
            }else{
                search(nums,mid+1,end,target);
            }
        }
            return -1;

    }
}
