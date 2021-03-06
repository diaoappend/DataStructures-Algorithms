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
        maxSize = arrMaxSize+1;//实际存储数组的容量要比传入的容量大1，
        arr = new Object[maxSize];
        front = 0;//指向队列头部数据
        rear = 0;//指向队列尾部数据的后一个位置
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
            rear=(rear+1)%maxSize;//队尾指针后移，对maxSize取余保证rear的值永远小于maxSize
        }
    }

    public Object getQueue(){
        if(isEmpty()){
            throw new RuntimeException("队列已空");
        }else{
            Object obj =arr[front];//取数
            front=(front+1)%maxSize;//队首指针后移，对maxSize取余保证front的值永远小于maxSize
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
    //求出当前队列有效数据个数,因为是循环队列，rear的值可能比front小，rear+maxSize保证rear一定以front大
    //然后对maxSize取余就是元素数量
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
        CircleArrayQueue queue = new CircleArrayQueue(3);
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
        queue.getQueue();
        queue.getQueue();
        queue.addQueue("元素4");
        queue.addQueue("元素5");
        System.out.println(queue.getCount());;
        queue.showDatas();
        System.out.println(queue.headQueue());

    }
}


