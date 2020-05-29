package com.diao.javacode;

/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/4/3 23:39
 * @description: 单例
 */
public class No1 {
    public static void main(String[] args) {

    }
}

/**
 * 饿汉式
 */
class Singleton1{
    private static final Singleton1 singleton = new Singleton1();
    private Singleton1(){}
    public static Singleton1 getInstance(){
        return singleton;
    }
}

/**
 * 懒汉式：静态内部类、
 * 外部类装载时内部类并不会装载
 * 当调用getInstance()方法时，静态内部类装载，而类（内部类）的静态属性只有
 * 在第一次装载类的时候初始化，JVM帮我们保证了线程的安全性，利用静态内部类特点实现延迟加载，效率高且线程安全
 */
class Singleton2{
    private Singleton2(){}

    private  static class SingletonInstance{
        private static final Singleton2 instance = new Singleton2();
    }
    public static Singleton2 getInstance(){
        return  SingletonInstance.instance;
    }
}

/**
 * 懒汉式：双重检查
 */
class Singleton3{
    private Singleton3(){}
    private static volatile Singleton3 instance;
    public static synchronized Singleton3 getInstance(){
        if(instance==null){
            synchronized (Singleton3.class){
                if (instance==null){
                    instance = new Singleton3();
                }
            }
        }
        return instance;
    }

}

enum Singleton4{
    instance;
    
}

class Singleton5{
    private Singleton5(){}
    private static volatile Singleton5 instance;
    public static synchronized Singleton5 getInstance(){
        if (instance==null){
            synchronized (Singleton5.class){
                if (instance==null){
                    instance = new Singleton5();
                }
            }
        }
        return instance;
    }
}