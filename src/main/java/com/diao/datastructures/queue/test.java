package com.diao.datastructures.queue;

import java.lang.reflect.Array;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/2 8:32
 * @description:
 * @version: 1.0
 */
public class test {
    private int rear;
    private int front;
    private int maxSize;
    private Object[] arr;

    public test(int maxArrSize) {
        this.maxSize = maxArrSize;
        front = 0;
        rear=0;
        arr = new Object[maxSize];
    }

    public boolean isFull(){
        return (rear+1)%maxSize==front;
    }

    public boolean isEmpty(){
        return rear==front;
    }
    public void add(Object o){
        if (!isFull()){
            arr[rear]=0;
            rear=(rear+1)%maxSize;
        }else{
            throw new RuntimeException("队列已满");
        }
    }

    public Object get(){
        if (!isEmpty()){
            Object tmp = arr[front];
            front=(front+1)%maxSize;
            return tmp;
        }else{
            throw new RuntimeException("队列为空");
        }
    }

    public int getCount(){
        return (rear+maxSize-front)%maxSize;
    }

    public void showQueue(){
        for (int i=front;i<front+getCount();i++){
            System.out.println(arr[i%maxSize]);
        }
    }

    public Object getHead(){
        if (!isEmpty()){
            return arr[front];
        }else{
            throw new RuntimeException("空");
        }
    }
}
