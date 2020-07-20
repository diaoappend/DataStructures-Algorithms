package com.diao.datastructures.linkedlist;

/**
 * @author: chenzhidiao
 * @date: 2020/7/16 16:33
 * @description:
 * @version: 1.0
 */
public class LRU {
    LRUNode first = null;//头部结点
    LRUNode tail = null;//尾部结点
    int capacity;//缓存容量
    int count = 0;//缓存已使用空间

    public LRU(int capacity) {
        this.capacity = capacity;
    }

    //判断是否为空
    public Boolean isEmpty() {
        return first == null;
    }

    //判断是否已满
    public Boolean isFull() {
        return count == capacity;
    }

    //查看或添加数据
    public void put(int key,Object value) {
        LRUNode node = new LRUNode(key,value);
        //如果链表为空，直接将first和tail指向新增结点
        if (isEmpty()) {
            first = node;
            tail = node;
            count++;
        } else {
            //查看结点是否存在，如果存在，放到链表末尾并return
            Object obj = get(key);
            if (obj != null) {
                return;
            } else {//如果不存在
                if (isFull()) {//如果已满，删除链表头部结点，将新增结点放在链表尾部
                    first = first.next;
                    tail.next = node;
                    tail = tail.next;
                } else {//如果未满，直接在尾部添加
                    tail.next = node;
                    tail = tail.next;
                    count++;
                }
            }
        }
    }


    //查看给定数据的结点是否存在，不存在返回null，如果存在将该结点放在链表尾部并返回
    public Object get(int key) {
        if(isEmpty()){
            return null;
        }
        LRUNode node = new LRUNode(key);
        Boolean flag = false;
        LRUNode tmp = first;
        //如果first就是查找的结点，将其放在链表末尾，然后返回
        if (first.key == node.key) {
            node.value=first.value;
            tail.next = node;
            tail = tail.next;
            first = first.next;
            return tail.value;
        }
        //如果first不是要查找结点，向后遍历
        while (tmp.next != null) {
            if (tmp.next.key == node.key) {
                flag = true;
                break;
            }
            tmp = tmp.next;
        }
        if (flag) {
            //如果存在，将该结点移动到链表末尾，然后返回该结点\
            node.value=tmp.next.value;
            tail.next = node;
            tail = tail.next;
            tmp.next = tmp.next.next;
            return tmp.next.value;
        } else {
            return null;
        }
    }

    /**
     * 遍历缓存
     */
    public void list() {
        LRUNode tmp = first;
        while (true) {
            if (tmp == null) {
                break;
            }
            System.out.println(tmp.toString());
            tmp = tmp.next;
        }
    }

    public static void main(String[] args) {
        LRU lru = new LRU(5);
        lru.put(1,"a");
        lru.put(2,"b");
        lru.put(1,"a");
        lru.get(2);
        lru.put(3,"c");
        lru.put(5,"e");
        lru.put(7,"g");
        lru.put(3,"c");
        lru.put(3,"c");
        System.out.println(lru.get(3));


        lru.list();
    }
}

class LRUNode{
    int key;
    Object value;
    LRUNode next;

    public LRUNode(int key) {
        this.key = key;
    }

    public LRUNode(int key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "LRUNode{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}


