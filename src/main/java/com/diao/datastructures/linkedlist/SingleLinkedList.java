package com.diao.datastructures.linkedlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/2 9:58
 * @description:单向链表
 * @version: 1.0
 */

public class SingleLinkedList {

    //初始化一个头结点
    private Node headNode = new Node(0);

    public Node getHeadNode() {
        return headNode;
    }

    public void setHeadNode(Node headNode) {
        this.headNode = headNode;
    }

    /**
     * 添加结点
     * 1.找到当前链表的尾部结点
     * 2.将尾部结点的next域指向添加的结点
     *
     * @param node
     */
    public void addNode(Node node) {
        // 由于head结点不能动，所以需要一个辅助结点帮助遍历
        Node tem = headNode;
        //当退出该循环时，tem为尾部结点
        while (true) {
            if (tem.next == null) {
                break;
            }
            tem = tem.next;
        }
        tem.next = node;
    }

    /**
     * 根据no属性按顺序增加结点
     * 1.找到新添加结点的位置，是通过辅助结点tem，通过遍历
     * 2.让新的结点的next域指向tem的next域
     * 3.tmp结点的next域指向新增加结点
     * 注意：由于是单链表，找到添加位置的tmp必须是新结点的前一个结点，否则添加不进去（因为单链表只知道下一个结点和本结点的排序值，不能获取前一个结点的）
     *
     * @param node
     */
    public void addNodeByNo(Node node) {
        Node tem = headNode;
        boolean flag = false;//标记添加的node是否存在，默认为false

        while (true) {
            if (tem.next == null) {//如果head结点的next域为null，直接找到添加位置
                break;
            }
            if (tem.next.no > node.no) {//如果tem下一个结点的no大于新加的的no，位置即为tem后面
                break;
            }
            if (tem.next.no == node.no) {
                flag = true;
                break;
            }
            tem = tem.next;
        }
        if (flag) {
            throw new RuntimeException("插入结点已存在");
        } else {
            //这里需要注意，要先将新增结点的next域指向tmp结点的next域，否则直接将tmp的next域指向新增结点会导致tmp的next域丢失
            node.next = tem.next;
            tem.next = node;
        }
    }

    /**
     * 修改结点的信息，根据no来修改，no不能改变
     *
     * @param node
     */
    public void updateNode(Node node) {
        if (headNode.next == null) {
            System.out.println("链表为空");
            return;
        }
        //定义一个辅助变量，通过遍历找到指定no的结点，然后修改结点信息
        Node tem = headNode.next;
        boolean flag = false;//标记是否找到指定no的结点
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

            return;
        } else {
            System.out.println("没有找到指定编号的结点");
        }
    }

    /**
     * 删除结点
     * 1.通过辅助结点遍历找到待删除结点的前一个结点
     * 2.tem.next=tem.next.next
     * 3.待删除的结点没有引用后续被GC回收
     *
     * @param no
     */
    public void deleteNode(int no) {
        if (headNode.next == null) {
            System.out.println("链表为空");
            return;
        }
        boolean flag = false;//标记指定结点是否存在
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
            System.out.println("指定结点不存在");
        }
    }

    /**
     * 遍历链表
     */
    public static void showLinkedList(Node head) {
        if (head == null||head.next==null) {
            System.out.println("链表为空");
            return;
        }
        Node tem = head.next;
        while (true) {
            if (tem == null) {//必须是tem为null，如果判断条件为tem.next==null，最后一个结点无法遍历
                break;
            }
            System.out.println(tem.toString());
            //遍历结点后要后移，否则会死循环
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
     * 获取倒数第index个结点
     * 通过一次遍历获取
     * 定义两个指针fast和slow分别指向头结点，先让fast指针后移index次，然后两个指针同时向后移动，
     * 当fast指针指向null时，slow指向的结点就是倒数第index个结点
     *
     * @param headNode
     * @param index
     * @return
     */
    public static Node getLastIndexNode(Node headNode, int index) {
        if (headNode.next == null || index <= 0) {
            return null;
        }
        Node fast = headNode;
        Node slow = headNode;
        for (int i = 0; i < index; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 单链表反转
     *
     * @param oldHeadNode 思路：遍历原链表，每遍历到一个结点，就把这个结点插入到新的链表的头结点后面
     *                    步骤：
     *                    1.定义一个辅助结点next和新的头结点reverseHeadNode
     *                    2.将原头结点的next域定义为current结点
     *                    3.将current结点的下一个结点暂存到next结点
     *                    4.将current结点的下一个结点指向新头结点reverseHeadNode的下一个结点
     *                    5.将新的头结点的next结点指向current结点
     *                    6.current结点后移，即把暂存在next的结点置为current结点
     * @return
     */
    public static void reverseLinkedList(Node oldHeadNode) {
        //如果没有有效结点或者只有一个有效结点，直接返回不需要反转
        if (oldHeadNode.next == null || oldHeadNode.next.next == null) {
            return;
        }
        //初始化一个新的头结点reverseHeadNode,并定义当前结点current为旧链表第一个结点，定义辅助结点next为null
        Node reverseHeadNode = new Node(0);
        Node current = oldHeadNode.next;
        Node next = null;
        //遍历原来的链表，每遍历一个结点，将其取出，并放在新的链表reverseHead的最前端
        while (current != null) {
            next = current.next;//暂时保存当前结点的下一个结点
            current.next = reverseHeadNode.next;//将current的下一个结点指向新的头结点的最前端
            reverseHeadNode.next = current;//将新的头结点指向current结点
            current = next;//current结点后移
        }
        oldHeadNode.next = reverseHeadNode.next;//将原头结点的next域指向反转头结点的next域，即把原来的头结点放在反转后链表头部，反转完成
    }

    /**
     * 逆序打印单向链表
     * 思路：①将链表反转，然后遍历打印，但会破坏原链表的结构，不建议；②遍历链表将结点依次加入栈中，然后用栈先进后出的特点实现逆序打印
     *
     * @param headNode
     */
    public static void reversePrint(Node headNode) {
        if (null == headNode.next) {
            return;
        }
        Node current = headNode.next;
        Stack<Node> stack = new Stack<Node>();
        while (current != null) {
            stack.push(current);
            current = current.next;
        }
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
    }

    /**
     * 判断链表是否有环
     * 思路：采用两个指针，开始分别指向头结点，然后向后移动，第一个指针步长为1，第二个指针步长为2，如果有环，两个指针一定会相遇
     * 时间复杂度：O(n);空间复杂度：O(1)
     *
     * @param head
     * @return
     */
    public static boolean isCycle(Node head) {
        if (head == null || head.next == null) {
            return false;
        }
        Node slow = head;
        Node fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    /**
     * 如果链表有环，查找入环结点
     * 思路：假设从第一个结点到入环结点的距离为L，入环结点到两指针相遇结点的距离为X，环的周长为R，那么：
     * 快指针走的距离为：L+X+nR(n为转的圈数)
     * 慢指针走的距离为：L+X
     * 根据快指针的速度为慢指针速度的2倍，得快指针走的距离为慢指针距离的2倍，有：
     * 2(L+X)=L+X+nR 即：L=nR-X=(n-1)R+(R-X)
     * 从上面的结果来看：R-X正好是环的长度-入环点到相遇点的长度，那么：
     * 当两个指针相遇时，再加一个指针从起点开始向后移动，同时快指针或慢指针也向后移动，那么新加的指针和慢指针(快指针)相遇的点就是入环结点
     * 因为快指针(或慢指针)不管转了几圈，相对于相遇结点的位移值都是R-X
     */
    public static Node getInCycleNode(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {//第一次相遇，有环
                break;
            }
        }
        //增加一个指针，从head开始
        Node tmp = head;
        //slow和tmp指针继续前进
        while (slow != null) {
            slow = slow.next;
            tmp = tmp.next;
            if (slow == tmp) {//新增指针和slow指针相遇的地方就是入环点
                break;
            }
        }
        //如果没有环，slow最终为null，返回null；如果有环，slow最终为入环点
        return slow;
    }

    /**
     * 如果链表有环，求环的长度
     * 思路：当快慢指针第一次相遇时，证明有环，此时让两个指针继续向前走，当第二次相遇时，由于快指针每次比慢指针多移动1个距离，那么第二次相遇时
     * 快指针正好比慢指针多移动了一圈距离
     *
     * @param head
     * @return
     */
    public static int getCycleLength(Node head) {
        if (head == null || head.next == null) {
            return -1;
        }
        int len = -1;//环的长度，默认为-1
        int count = 0;//记录相遇次数
        Node p1 = head;
        Node p2 = head;
        while (p1.next != null && p1.next.next != null) {
            p1 = p1.next.next;
            p2 = p2.next;
            if (p1 == p2) {//第一次相遇
                count++;
                if (count == 1) {
                    len = 0;
                }
            }
            if (count == 1) {
                len++;
            } else if (count == 2) {//当第二次相遇时，跳出循环，len的值就是环的长度
                break;
            }
        }
        return len;
    }

    /**
     * 查找链表的倒数第k个结点
     * 思路：定义两个指针分别指向链表的头结点，让其中一个指针先遍历k个结点，然后另一个指针再开始遍历，
     * 这样先移动的指针遍历完链表结点时，后移动的指针正好指向链表的第k个结点。
     */
    public static Node getLastKNode(Node head, int k) {
        if (head == null || head.next == null) {
            return null;
        }
        Node p1 = head.next;
        Node p2 = head.next;
        for (int i = 0; i < k; i++) {
            if (p1 != null) {
                p1 = p1.next;
            } else {
                return null;
            }
        }
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        return p2;
    }

    /**
     * 删除链表的倒数第k个结点
     * 思路：和查找链表的倒数第k个结点思路类似，只不过要删除倒数第k个结点，需要找到倒数第k+1个结点，然后将其next域指向倒数第k个结点的next域​。
     */
    public static boolean deleteLastKNode(Node head, int k) {
        if (head == null || head.next == null) {
            return false;
        }
        Node p1 = head.next;
        Node p2 = head.next;
        //一个指针先遍历k+1个结点,这样当这个指针为null时，另一个指针指向的就是倒数第k+1个结点
        for (int i = 0; i < k + 1; i++) {

            if (p1 != null) {
                p1 = p1.next;
            } else {
                return false;
            }
        }
        //两个指针继续遍历，第一个指针为null时，第二个指针指向倒数第k+1个结点
        while (p1 != null) {
            p1 = p1.next;
            p2 = p2.next;
        }
        //将倒数第k+1个结点的next域指向倒数第k个结点的next域​，达到删除第k个结点的目的。
        p2.next = p2.next.next;
        return true;
    }


    /**
     * 如果链表结点数为奇数，查找链表的中间结点；如果链表结点数为偶数，返回第二个中间结点
     * 思路：定义两个快慢指针，快指针步长为2，慢指针步长为1
     * ①当快指针为null时，说明链表长度为奇数，slow指针所指就是链表的中间结点
     * ②当快指针的next域为null时，说明链表长度为偶数
     *
     * @param head
     * @return
     */
    public static Node getMidNode(Node head) {
        if (head == null || head.next == null) {
            return head;
        }
        Node fast = head;
        Node slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        //如果fast为null，说明链表长度为奇数，中间结点有1个，为slow
        //如果fast.next为null，说明链表长度为偶数，返回靠右一个结点
        return fast == null ? slow : slow.next;
    }

    /**
     * 合并两个有序链表（归并法）
     * 思路：
     * 1.定义两个指针分别指向两个链表的第一个结点
     * 2.新建一个头结点，用来保存合并后的链表
     * 3.然后遍历链表，比较结点数据值的大小，将较小的保存到新链表，然后指针后移
     * 4.当其中一个链表遍历完成后，将另一个链表的剩余结点一次性放到新链表的末尾
     * @param head1
     * @param head2
     */
    public static Node mergeSortedLinkedList(Node head1,Node head2){
        if(head1==null)return head2;
        if (head2==null)return head1;

        Node new_head = new Node(0);
        Node tmp=new_head;
        Node p1=head1.next;
        Node p2=head2.next;
        while(p1!=null&&p2!=null){
            if (p1.no<=p2.no){
                tmp.next=p1;
                p1=p1.next;
                tmp=tmp.next;
            }else{
                tmp.next=p2;
                p2=p2.next;
                tmp=tmp.next;
            }
        }
        //p1不为null说明第一个链表还有结点没遍历完，将剩余结点放在新链表的末尾
        if (p1!=null){
            tmp.next=p1;
        }
        if (p2!=null){
            tmp.next=p2;
        }
        return new_head;
    }

    /**
     * 合并两个有序单向链表（递归法）
     * 思路：比较两个链表的第一个结点的数据值，让较小的结点的next域指向该链表剩余结点和另一个链表合并后的链表，依次递归，直到其中一个链表遍历完
     * 已合并链表部分的next域指向另一个链表的剩余结点。
     *
     * @param head1
     * @param head2
     * @return
     */
    public static Node mergeSortedLinkedList2(Node head1,Node head2,boolean isHead){
        if(head1==null)return head2;
        if (head2==null)return head1;

       if(isHead){//如果是头结点，应该比较其next域对应结点的值
           if (head1.next.no<=head2.next.no){
               head1.next.next=mergeSortedLinkedList2(head1.next.next,head2.next,false);
               return head1;
           }else{
               head2.next.next=mergeSortedLinkedList2(head1.next,head2.next.next,false);
               return head2;
           }
       }else{//如果不是头结点，直接比较结点的值
           if (head1.no<=head2.no){
               head1.next=mergeSortedLinkedList2(head1.next,head2,false);
               return head1;
           }else{
               head2.next=mergeSortedLinkedList2(head1,head2.next,false);
               return head2;
           }
       }
    }

    public static void main(String[] args) {
        SingleLinkedList list1 = new SingleLinkedList();
        SingleLinkedList list2 = new SingleLinkedList();
        /*list.addNodeByNo(new Node(1));
        list.addNodeByNo(new Node(3));
        list.addNodeByNo(new Node(2));
        list.showLinkedList();*/
        //reversePrint(list.headNode);
        Node head1 = list1.headNode;
        Node head2 = list2.headNode;
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        head1.next = node1;
        node1.next = node4;
        node4.next = node7;
        head2.next = node2;
        node2.next = node5;
        node5.next = node6;
        Node head=mergeSortedLinkedList2(head1,head2,true);
        System.out.println(head.toString());
        showLinkedList(head);


    }
}

class Node {
    int no;
    Node next;

    public Node(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +
                '}';
    }
}
