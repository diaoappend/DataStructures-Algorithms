package com.diao.datastructures.linkedlist;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/3 15:53
 * @description:单向环形链表及约瑟夫问题
 * @version: 1.0
 */
public class Josepfu {
    //创建一个first节点，用来指定链表开始位置，当前节点没有编号
    private Boy first = null;

    /**
     * 创建环形链表，给定节点数量
     * @param num
     */
    public void addBoy(int num) {
        //对num做数据检验
        if (num < 1) {
            System.out.println("num的值不正确");
            return;
        }
        Boy curBoy = null;//辅助指针，帮助构建环形链表
        for (int i = 1; i <= num; i++) {
            //根据编号创建节点
            Boy boy = new Boy(i);
            //创建第一个节点时，first指针先指向给定节点；然后first的next域指向自己形成环；最后curBoy节点指向第一个节点
            if (i == 1) {
                first = boy;
                first.setNext(first);
                curBoy = first;
            } else {
                //从插入第二个节点开始，先将curBoy节点的next域指向新节点；新节点的next域指向first；然后curBoy后移，指向新节点
                curBoy.setNext(boy);
                boy.setNext(first);
                curBoy = boy;//curBoy辅助指针后移
            }
        }
    }

    /**
     * 遍历环形链表
     */
    public void showCircleLinkedList() {
        //判断链表是否为空
        if (first == null) {
            System.out.println("链表为空");
            return;
        }
        //由于first不能动，需要创建一个辅助指针帮助遍历
        Boy curBoy = first;
        while(true){
            System.out.println("boy编号为"+curBoy.getNo());
            if (curBoy.getNext()==first){//说明已经遍历完毕
                break;
            }
            curBoy=curBoy.getNext();
        }
    }

    /**
     * 约瑟夫问题代码实现
     * @param startNo 开始数数的节点的no
     * @param countNum 每次数的次数
     * @param nums 圈里人数
     * 步骤说明：
     * 1.需要创建一个辅助指针helper，事先应该指向链表的最后一个节点（即first的前一个节点）
     * 2.报数前，先将first和helper指针移到k-1次(m即方法中的startNo)
     * 2.当小孩报数时，让first和helper指针同时移动m-1次(m即方法中的countNum)
     * 3.这是就可以将first指向的节点出圈，first=first.next;helper.next=first;原来first指向的节点没有引用后会被自动回收。
     * 代码实现
     */
    public void countBoy(int startNo,int countNum,int nums){
        if (first==null||startNo<1||startNo>nums){
            System.out.println("参数有误，请重新输入");
            return;
        }
        Boy helper = first;
        while (true){
            if (helper.getNext()==first){//当helper的next节点为first，说明到达指定位置
                break;
            }
            helper=helper.getNext();
        }
        //先将first和helper指针移到到startNo
        for (int i=1;i<=startNo-1;i++){
            first=first.getNext();
            helper=helper.getNext();
        }
        //报数时，让first和helper同时移动countNum-1次，然后first指向的节点出圈；循环直到圈中只剩1个节点
        while (true){
            if (helper==first){//first和helper指针指向同一个节点时说明圈中只剩1个节点
                break;
            }
            for (int j=0;j<countNum-1;j++){
                first=first.getNext();
                helper=helper.getNext();
            }
            //first指向的节点出圈
            System.out.printf("节点%d出圈\n",first.getNo());
            first=first.getNext();
            helper.setNext(first);
        }
        System.out.printf("最后留在圈中的节点为%d\n",first.getNo());
    }

    public static void main(String[] args) {
        Josepfu josepfu = new Josepfu();
        josepfu.addBoy(5);
        josepfu.countBoy(1,2,5);
    }
}

//创建节点类型Boy
class Boy {
    private int no;
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
