package com.diao.javacode;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/4/4 18:42
 * @description:
 */

/**
 * 停车场景，有车位可以停，没车位等待直到有车位
 * 1）事先创建指定的锁对象（Semaphore），并指定资源数量
 * 2）业务对象执行逻辑时抢占资源（ semaphore.acquire）
 * 3) 如果资源不够，其他对象需要等待，直到之前的对象释放资源（ semaphore.release），才能继续抢占
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
       long start = System.currentTimeMillis();
       method2();
       long end = System.currentTimeMillis();
        System.out.println(String.format("花费时间%d毫秒",end-start));
    }
    public static void method1(){
        String src = "";
        for (int i = 0;i< 10000;i++){
            src = src + "a";
        }
    }
    public static void method2(){
        StringBuilder src = new StringBuilder();
        for (int i = 0;i< 10000;i++){
            src = src.append("a");
        }
    }
}

class Order{
    static  int aa ;
    static final String bb=null;

}

/**
 * 线程闭锁
 * 模仿打扫教室，只有当教室中所有学生打扫完教室后，老师锁门
 * 这里不用线程间join的方法，因为join是有序的，而实际打扫是无序的
 */
class CleanClassroom {

}


class Student {
    public AtomicInteger count = new AtomicInteger(0);

    public void product() {
        count.getAndIncrement();
    }

    public void consume() {
        count.getAndDecrement();
    }
}

class A {
    int count = 0;
    private final ReentrantLock lock = new ReentrantLock();  //  ReentrantLock可重入锁
    private Condition cond = lock.newCondition();//添加了一个条件对象

    public void product() {
        //加锁
        lock.lock();
        try {
            //保证线程安全的代码;
            while (count != 0) {
                cond.await();//相当于调用了this.wait()方法
            }
            count++;
            System.out.println("生产了产品= " + count);
            cond.signalAll();//相当于this.notifyAll()方法
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }

    public void consume() {
        //加锁
        lock.lock();
        try {
            //保证线程安全的代码;
            while (count != 1) {
                cond.await();//相当于调用了this.wait()方法
            }
            count--;
            System.out.println("消费了产品= " + count);
            cond.signalAll();//相当于this.notifyAll()方法
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}

class Thread1 {
    int flag = 1;//定义一个标记，用来确定执行哪个线程
    private final ReentrantLock lock = new ReentrantLock();
    //定义三个条件对象，完成对线程的定制调度
    private Condition cond1 = lock.newCondition();
    private Condition cond2 = lock.newCondition();
    private Condition cond3 = lock.newCondition();

    public void print10() {
        lock.lock();
        try {
            while (flag != 1) {
                cond1.await();//如果flag！=1，通过cond1对象让线程1等待
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println("i= " + i);
            }
            flag = 2; //打印完成修改flag=2,让线程2接着打印
            cond2.signal();//通过cond2对象定制唤醒线程2，而不去唤醒其他线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print20() {
        lock.lock();
        try {
            while (flag != 2) {
                cond2.await();
            }
            Thread.sleep(4000);
            for (int i = 11; i <= 20; i++) {
                System.err.println("i= " + i);
            }
            flag = 3;
            cond3.signal();//定制唤醒线程3
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print30() {
        lock.lock();
        try {
            while (flag != 3) {
                cond3.await();
            }
            for (int i = 21; i <= 30; i++) {
                System.out.println("i= " + i);
            }
            flag = 1;
            cond1.signal();//线程3打印完成定制唤醒线程1
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

class ReadAndWrite {
    private String content;
    //读写锁采用ReentrantReadWriteLock对象
    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    public String read() {
        lock.readLock().lock();//读锁
        try {
            System.out.println("学生" + Thread.currentThread().getName() + "获取了数据= " + content);
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
        return "";
    }

    public void write(String content) {
        lock.writeLock().lock();//写锁
        try {
            this.content = content;
            System.out.println("老师添加了数据= " + content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
