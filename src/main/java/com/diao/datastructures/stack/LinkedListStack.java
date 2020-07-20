package com.diao.datastructures.stack;

/**
 * @author: chenzhidiao
 * @date: 2020/7/16 8:06
 * @description:用链表实现栈(头插法)
 * @version: 1.0
 */
public class LinkedListStack {
    //定义一个top指针指向栈顶元素
    Node top = null;

    /**
     * 判断栈是否为空
     * @return
     */
    public boolean isEmpty(){
        return top==null;
    }

    /**
     * 入栈
     * @param data
     * @return
     */
    public boolean headPush(Object data){
        Node node = new Node(data);
        //如果top为null，top=node；
        if (top==null){
            top=node;
        }else{//top不为null，在链表头部插入结点
            node.next=top;
            top=node;
        }
        return true;
    }

    /**
     * 出栈
     * @return
     */
    public Node headPop(){
        if (isEmpty()){
            throw new RuntimeException("栈为空！");
        }
        //需要将top所指的结点保存在临时变量，然后再移动top指针，否则会丢失结点
        Node tmp = top;
        top=top.next;
        return tmp;
    }

    /**
     * 查看栈顶元素，只查看，不出栈
     * @return
     */
    public Node peak(){
        return top;
    }

    /**
     * 遍历栈元素，不出栈
     */
    public void list(){
        if (top==null){
            System.out.println("栈为空！");
            return;
        }
        //由于top指针不能动，需要定义一个tmp指针协助遍历
        Node tmp = top;
        while (tmp!=null){
            System.out.println(tmp.toString());
            tmp=tmp.next;
        }
    }

    public static void main(String[] args) {
        LinkedListStack linkedListStack = new LinkedListStack();
        linkedListStack.headPush("1");
        linkedListStack.headPush("2");
        linkedListStack.headPush("3");
        linkedListStack.list();
        System.out.println(linkedListStack.headPop().toString());
        System.out.println(linkedListStack.headPop().toString());
        System.out.println(linkedListStack.headPop().toString());
        System.out.println(linkedListStack.headPop().toString());

    }
}

class Node{
    Object data;
    Node next;

    public Node(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}
