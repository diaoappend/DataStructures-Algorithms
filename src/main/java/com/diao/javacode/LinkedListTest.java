package com.diao.javacode;

/**
 * @author: chenzhidiao
 * @date: 2020/7/15 8:22
 * @description:
 * @version: 1.0
 */
public class LinkedListTest {
    static Node first=null;

    /**
     * 约瑟夫问题详解
     * @param startNo 开始结点
     * @param num     每次数的结点数
     * @param count   圈中结点总数
     */
    public static void josepfu(int startNo,int num,int count){
        if(count<1){
            System.out.println("请输入正确的结点数量");
            return;
        }
        //定义curNode指针辅助构建环形链表
        Node curNode=null;
        for (int i=1;i<=count;i++){
            Node inNode = new Node(i);
            //创建第一个结点时，first指针先指向给定结点；然后first的next域指向自己形成环；最后curNode指针指向第一个结点
            if (i==1){
                first=inNode;
                first.next=first;
                curNode=first;
            }else{
                //从插入第二个结点开始，先将curNode指针的next域指向新结点（因为first结点不能动）；新结点的next域指向first；然后curNode后移，指向新结点
                curNode.next=inNode;
                inNode.next=first;
                curNode=inNode;
            }
        }
        //遍历单向环形链表，检查构建是否正确
        //定义helper指针协助遍历，且遍历完成后，helper指针正好指向环形链表的末尾结点
        Node helper=first;
        while (true){
            System.out.println("结点"+helper.no+";");
            //当cur指针的next域指向first时，说明cur是最后一个结点
            if (helper.next==first){
                break;
            }
            helper=helper.next;
        }
        System.out.println("-------------------------------------");

        //同时移动first和helper指针，让first指针指向startNo结点
        for(int j=1;j<=startNo-1;j++){
            first=first.next;
            helper=helper.next;
        }
        //从startNo结点开始，第num个结点出圈，那么指针实际移动num-1次
       while (true){
           //如果只剩一个结点，结束循环
           if (first==helper){
               break;
           }
           for (int k=1;k<=num-1;k++){
               first=first.next;
               helper=helper.next;
           }
           System.out.println(String.format("结点%d出圈",first.no));
           first=first.next;
           helper.next=first;
       }
        System.out.println(String.format("最后留在圈中的结点为%d",first.no));
    }

    public static void main(String[] args) {
        josepfu(2,2,5);
    }
}

class Node{
    int no;
    Node next;
    public Node(int no){
        this.no=no;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                '}';
    }
}
