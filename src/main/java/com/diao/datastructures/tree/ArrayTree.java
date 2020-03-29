package com.diao.datastructures.tree;

/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/3/29 18:32
 * @description: 顺序存储二叉树
 * 例子：给定一个数组{1,2,3,4,5,6,7}，要求将这个数组按照二叉树前序遍历的方式进行遍历
 * 结果应是{1,2,4,5,3,6,7}
 * 作用：堆排序中用到
 */
public class ArrayTree {
    private int[] arr;

    public ArrayTree(int[] arr) {
        this.arr = arr;
    }
    /**说明：
     * ①顺序二叉树通常只考虑完全二叉树
     * ②第n个元素的左子节点为2*n+1
     * ③第n个元素的右子节点为2*n+2
     * ④第n个元素的父节点为（n-1）/2
     * ⑤n表示二叉树中的第几个元素，按0开始编号
     */
    /**
     * 将数组按照二叉树前序遍历的方式进行遍历
     *
     * @param index 数组开始下标
     */
    public void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空！");
        }
        System.out.println(arr[index]);
        if ((index * 2 + 1) < arr.length) {//
            preOrder(index * 2 + 1);
        }
        if ((index * 2 + 2) < arr.length) {
            preOrder(index * 2 + 2);
        }
    }

    /**
     * 中序遍历
     * @param index
     */
    public void infixOrder(int index){
        if (arr==null||arr.length==0){
            System.out.println("数组为空！");
        }
        if ((index * 2 + 1) < arr.length) {//
            infixOrder(index * 2 + 1);
        }
        System.out.println(arr[index]);
        if ((index * 2 + 2) < arr.length) {
            infixOrder(index * 2 + 2);
        }
    }

    /**
     * 后序遍历
     * @param index
     */
    public void postOrder(int index){
        if (arr==null||arr.length==0){
            System.out.println("数组为空！");
        }
        if ((index * 2 + 1) < arr.length) {//
            postOrder(index * 2 + 1);
        }
        if ((index * 2 + 2) < arr.length) {
            postOrder(index * 2 + 2);
        }
        System.out.println(arr[index]);
    }
    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5,6,7};
        ArrayTree arrayTree = new ArrayTree(arr);
        arrayTree.infixOrder(0);
    }
}

