package com.diao.datastructures.queue;

/**
 * @author: Chenzhidiao
 * @date: 2019/12/31 14:21
 * @description:
 * @version: 1.0
 */

/**
 * a)将尾指针后移：rear+1，当rear=front时表示队列为空
 * b)若尾指针rear小于队列的最大下标maxSize-1，表示队列未满，则将数据存入数组中下标为rear的位置，否则无法插入数据
 */
public class ArrayQueue {
    private int maxSize;//队列容量
    private int rear;//队尾指针
    private int front;//队首指针
    private Object[] arr;

    //构造一个数组，需要传入一个容量
    public ArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new Object[maxSize];
        front = -1;//指向队列头部数据的前一个位置
        rear = -1;//直接指向队列尾部数据
    }

    //判断队列是否满
    public boolean isFull() {
        return rear == maxSize - 1;
    }
    //判断队列是否为空
    public boolean isEmpty(){
        return front==rear;
    }

    public void addQueue(Object obj) {
        if (isFull()) {
            System.out.println("队列已满");
            return;
        }else {
            rear++;//队尾指针后移
            arr[rear]=obj;
        }
    }

    public Object getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列已空");
        }else{
            front++;//队首指针先后移
            return arr[front];//取数
        }
    }
    //显示队列全部数据
    public void showDatas(){
        if (isEmpty()){
            System.out.println("队列为空");
            return;
        }else {
            for (int i = front + 1; i <= rear; i++) {
                System.out.println(arr[i]);
            }
        }
    }
    //显示队列头数据
    public Object headQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列已空");
        }else {
            return arr[front+1];
        }

    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(10);
        queue.addQueue("元素1");
        queue.addQueue("元素2");
        queue.addQueue("元素3");
        queue.getQueue();
        queue.showDatas();
        System.out.println(queue.headQueue());

    }
}
