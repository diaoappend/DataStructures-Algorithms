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

    public void add(Object obj){
        if(!isFull()){
            arr[rear]=obj;
            rear=(rear+1)%maxSize;
        }
    }

    public Object getQueue(){
        if(!isEmpty()){
            Object o = arr[front];
            front=(front+1)%maxSize;
            return o;
        } else{
            throw new RuntimeException("队列为空");
        }
    }
    public void showQueue(){
        for (int i=front;i<front+getCount();i++){
            System.out.println(arr[i]);
        }
    }
    public int getCount(){
        return (rear+maxSize-front)%maxSize;
    }
}
