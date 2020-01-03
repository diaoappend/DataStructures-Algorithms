package com.diao.datastructures.linkedlist;

import java.util.Stack;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/2 9:58
 * @description:
 * @version: 1.0
 */

public class SingleLinkedList {

    //初始化一个头节点
    private Node headNode = new Node(0, "", "");

    public Node getHeadNode() {
        return headNode;
    }

    public void setHeadNode(Node headNode) {
        this.headNode = headNode;
    }

    /**
     * 添加节点
     * 1.找到当前链表的尾部节点
     * 2.将尾部节点的next域指向添加的节点
     *
     * @param node
     */
    public void addNode(Node node) {
        // 由于head节点不能动，所以需要一个辅助节点帮助遍历
        Node tem = headNode;
        //当退出该循环时，tem为尾部节点
        while (true) {
            if (tem.next == null) {
                break;
            }
            tem = tem.next;
        }
        tem.next = node;
    }

    /**
     * 根据no属性按顺序增加节点
     * 1.找到新添加节点的位置，是通过辅助节点tem，通过遍历
     * 2.让新的节点的next域指向tem的next域
     * 3.tmp节点的next域指向新增加节点
     * 注意：由于是单链表，找到添加位置的tmp必须是新节点的前一个节点，否则添加不进去（因为单链表只知道下一个节点和本节点的排序值，不能获取前一个节点的）
     *
     * @param node
     */
    public void addNodeByNo(Node node) {
        Node tem = headNode;
        boolean flag = false;//标记添加的node是否存在，默认为false

        while (true) {
            if (tem.next == null) {//如果head节点的next域为null，直接找到添加位置
                break;
            }
            if (tem.next.no > node.no) {//如果tem下一个节点的no大于新加的的no，位置即为tem后面
                break;
            }
            if (tem.next.no == node.no) {
                flag = true;
                break;
            }
            tem = tem.next;
        }
        if (flag) {
            throw new RuntimeException("插入节点已存在");
        } else {
            node.next = tem.next;
            tem.next = node;
        }
    }

    /**
     * 修改节点的信息，根据no来修改，no不能改变
     *
     * @param node
     */
    public void updateNode(Node node) {
        if (headNode.next == null) {
            System.out.println("链表为空");
            return;
        }
        //定义一个辅助变量，通过遍历找到指定no的节点，然后修改节点信息
        Node tem = headNode.next;
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
     * 删除节点
     * 1.通过辅助节点遍历找到待删除节点的前一个节点
     * 2.tem.next=tem.next.next
     * 3.待删除的节点没有引用后续被GC回收
     *
     * @param no
     */
    public void deleteNode(int no) {
        if (headNode.next == null) {
            System.out.println("链表为空");
            return;
        }
        boolean flag = false;//标记指定节点是否存在
        Node tem = headNode.next;
        while (true) {
            if (tem.next.no == no) {
                flag = true;
                break;
            }
            tem = tem.next;
        }
        if (flag) {
            tem.next = tem.next.next;
        } else {
            System.out.println("指定节点不存在");
        }
    }

    /**
     * 遍历链表
     */
    public void showLinkedList() {
        if (headNode.next == null) {
            System.out.println("链表为空");
            return;
        }
        Node tem = headNode.next;
        while (true) {
            if (tem == null) {
                break;
            }
            System.out.println(tem.toString());
            //遍历节点后要后移，否则会死循环
            tem = tem.next;
        }
    }

    /**
     * 获取链表长度
     *
     * @param headNode
     * @return
     */
    public static int getLength(Node headNode) {
        if (headNode.next == null) {
            return 0;
        }
        int length = 0;
        Node tem = headNode;
        while (tem.next != null) {
            length++;
            tem = tem.next;
        }
        return length;
    }

    /**
     * 获取倒数第index个节点
     *
     * @param headNode
     * @param index
     * @return
     */
    public static Node getLastIndexNode(Node headNode, int index) {
        if (headNode.next == null) {
            return null;
        }
        int length = getLength(headNode);
        //index校验
        if (index <= 0 || index > length) {
            return null;
        } else {
            Node current = headNode.next;
            for (int i = 0; i < length - index; i++) {
                current = current.next;
            }
            return current;
        }
    }

    /**
     * 单链表反转
     *
     * @param oldHeadNode 思路：遍历原链表，每遍历到一个节点，就把这个节点插入到新的链表的头节点后面
     *                    步骤：
     *                    1.定义一个辅助节点next和新的头节点reverseHeadNode
     *                    2.将原头节点的next域定义为current节点
     *                    3.将current节点的下一个节点暂存到next节点
     *                    4.将current节点的下一个节点指向新头节点reverseHeadNode的下一个节点
     *                    5.将新的头节点的next节点指向current节点
     *                    6.current节点后移，即把暂存在next的节点置为current节点
     * @return
     */
    public static void reverseLinkedList(Node oldHeadNode) {
        //如果没有有效节点或者只有一个有效节点，直接返回不需要反转
        if (oldHeadNode.next == null || oldHeadNode.next.next == null) {
            return;
        }
        //初始化一个新的头节点reverseHeadNode,并定义当前节点current和其下一个节点next
        Node reverseHeadNode = new Node(0, "", "");
        Node current = oldHeadNode.next;
        Node pre = null;
        Node next = null;
        //遍历原来的链表，每遍历一个节点，将其取出，并放在新的链表reverseHead的最前端
        while (current != null) {
            next = current.next;//暂时保存当前节点的下一个节点
            current.next = reverseHeadNode.next;//将current的下一个节点指向新的头节点的最前端
            reverseHeadNode.next = current;//将新的头节点指向current节点
            current = next;//current节点后移
        }
        oldHeadNode.next = reverseHeadNode.next;//将原头节点的next域指向反转头节点的next域，即把原来的头节点放在反转后链表头部，反转完成
    }

    /**
     * 逆序打印单向链表
     * 思路：①将链表反转，然后遍历打印，但会破坏原链表的结构，不建议；②遍历链表将节点依次加入栈中，然后用栈先进后出的特点实现逆序打印
     * @param headNode
     */
    public static void reversePrint(Node headNode) {
        if (null == headNode.next ) {
            return;
        }
        Node current = headNode.next;
        Stack<Node> stack = new Stack<Node>();
        while (current != null){
           stack.push(current);
           current=current.next;
        }
        while(stack.size()>0){
            System.out.println(stack.pop());
        }
    }

    public static void main(String[] args) {
        SingleLinkedList list = new SingleLinkedList();
        list.addNodeByNo(new Node(1, "songjianng", "jishiyu"));
        list.addNodeByNo(new Node(3, "wuyong", "zhiduoxing"));
        list.addNodeByNo(new Node(2, "lujunyi", "yuqilin"));
        list.showLinkedList();
        reversePrint(list.headNode);
    }
}

class Node {
    int no;
    String name;
    String nickname;
    Node next;

    public Node(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
