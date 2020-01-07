package com.diao.datastructures.linkedlist;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/3 14:24
 * @description:双向链表
 * @version: 1.0
 */
public class DoubleLinkedList {
    private DoubleNode headNode = new DoubleNode(0,"","");

    public DoubleNode getHeadNode() {
        return headNode;
    }

    /**
     * 遍历双向链表，和遍历单向链表一样
     * @param headNode
     */
    public void showDoubleLinkedList(DoubleNode headNode){
        if (headNode.next==null){
            System.out.println("链表为空");
            return;
        }
        DoubleNode cur = headNode.next;
        while(cur!=null){
            System.out.println(cur);
            cur=cur.next;
        }
    }
    /**
     * 向链表末尾添加节点
     * @param node
     */
    public void addDoubleNode(DoubleNode node){
        DoubleNode tem = headNode;
        //当退出该循环时，tem为尾部节点
        while (true) {
            if (tem.next == null) {
                break;
            }
            tem = tem.next;
        }
        tem.next = node;
        node.pre=tem;//增加一个pre指向原尾部节点tem
    }

    /**
     * 修改节点内容,和单向链表一样
     * @param node
     */
    public void updateDoubleNode(DoubleNode node){
        if (headNode.next == null) {
            System.out.println("链表为空");
            return;
        }
        //定义一个辅助变量，通过遍历找到指定no的节点，然后修改节点信息
        DoubleNode tem = headNode.next;
        boolean flag = false;//标记是否找到指定no的节点
        while (true) {
            if (tem == null) {
                break;//已遍历到链表末尾
            }
            if (tem.no == node.no) {
                flag = true;
                break;
            }
            tem = tem.next;
        }
        if (flag) {
            tem.nickname = node.nickname;
            tem.name = node.name;
            return;
        } else {
            System.out.println("没有找到指定编号的节点");
        }
    }

    /**
     * 根据编号删除双向链表中的节点
     * 思路：双向链表可以直接找到要删除的节点，而不用再找待删除节点的前一个节点；找到后进行自我删除
     * 步骤：
     * 1.遍历双向链表，找到指定编号的节点；
     * 2.将该节点的pre节点的next域指向该节点的next节点；
     * 3.将该节点的next节点的pre域指向该节点的pre节点
     * 注意：如果要删除的节点是最后一个节点，则不需要执行步骤3，因为最后一个节点的next为null
     * @param no
     */
    public void deleteDoubleNode(int no){
        if (headNode.next == null) {
            System.out.println("链表为空");
            return;
        }
        DoubleNode cur = headNode.next;
        boolean flag = false;
        while(cur!=null){
            if (cur.no==no){
                flag=true;
                break;
            }
            cur=cur.next;
        }
        if (flag){
            cur.pre.next=cur.next;//将该节点的pre节点的next域指向该节点的next节点
            if (cur.next!=null){//如果cur是最后一个节点，就不需要执行下面一步
                cur.next.pre=cur.pre;//将该节点的next节点的pre域指向该节点的pre节点,这个步骤有前提，就是cur节点不能是最后一个节点，否则出现空指针异常
            }
        }else{
            System.out.println("未找到编号为 "+no+" 的节点");
        }
    }

    public static void main(String[] args) {
        DoubleLinkedList list = new DoubleLinkedList();
        list.addDoubleNode(new DoubleNode(1, "songjianng", "jishiyu"));
        list.addDoubleNode(new DoubleNode(3, "wuyong", "zhiduoxing"));
        list.addDoubleNode(new DoubleNode(2, "lujunyi", "yuqilin"));
        list.showDoubleLinkedList(list.getHeadNode());
        list.deleteDoubleNode(3);
        System.out.println("----------------------------------------------------------");
        list.showDoubleLinkedList(list.getHeadNode());

    }
}

class DoubleNode{
    int no;
    String name;
    String nickname;
    DoubleNode pre;
    DoubleNode next;

    public DoubleNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickname = nickName;
    }

    @Override
    public String toString() {
        return "DoubleNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickName='" + nickname + '\'' +
                '}';
    }
}
