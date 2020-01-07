package com.diao.datastructures.queue;

/**
 * @author: Chenzhidiao
 * @date: 2019/12/31 15:55
 * @description:环形队列
 * @version: 1.0
 */
public class CircleArrayQueue {
    private int maxSize;//队列容量
    private int rear;//队尾指针
    private int front;//队首指针
    private Object[] arr;
    //构造一个数组，需要传入一个容量
    public CircleArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new Object[maxSize];
        front = 0;//指向队列头部数据的前一个位置
        rear = 0;//直接指向队列尾部数据
    }
    //判断队列是否满
    public boolean isFull() {
        return (rear+1)%maxSize == front;
    }
    //判断队列是否为空
    public boolean isEmpty(){
        return front==rear;
    }

    public void addQueue(Object obj) {
        if (isFull()) {
            System.out.println("队列已满，无法添加"+obj);
            return;
        }else {
            arr[rear]=obj;
            rear=(rear+1)%maxSize;//队尾指针后移
        }
    }

    public Object getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列已空");
        }else{
            Object obj =arr[front];//取数
            front=(front+1)%maxSize;//队首指针后移
            return obj;
        }
    }

    //显示队列全部数据
    public void showDatas(){
        if (isEmpty()){
            System.out.println("队列为空");
            return;
        }else {
            for (int i = front; i < front+getCount(); i++) {
                System.out.println(arr[i%maxSize]);//这里由于是环形队列，front加上有效数据个数可能会重复写入取出数据的空间，如果不取模，会出现下标越界
            }
        }
    }
    //求出当前队列有效数据个数
    public int getCount(){
        return (rear+maxSize-front)%maxSize;
    }
    //显示队列头数据
    public Object headQueue(){
        if (isEmpty()){
            throw new RuntimeException("队列已空");
        }else {
            return arr[front];
        }
    }

    public static void main(String[] args) {
        CircleArrayQueue queue = new CircleArrayQueue(4);
        queue.addQueue("元素1");
        queue.addQueue("元素2");
        queue.addQueue("元素3");
        queue.getQueue();
        queue.getQueue();
        queue.getQueue();
        queue.addQueue("元素4");
        queue.addQueue("元素5");
        queue.addQueue("元素6");
        System.out.println(queue.rear);
        System.out.println(queue.front);
        /*queue.getQueue();
        queue.getQueue();
        queue.addQueue("元素4");
        queue.addQueue("元素5");*/
        queue.showDatas();
        System.out.println(queue.headQueue());

    }
}
