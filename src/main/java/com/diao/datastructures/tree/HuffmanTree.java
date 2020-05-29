package com.diao.datastructures.tree;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author: chenzhidiao
 * @date: 2020/5/28 22:28
 * @description: 赫夫曼树
 * 构成赫夫曼树的步骤：
 * 1.从小到大进行排序，每个数据都是一个节点，每个节点可以看成是一颗最简单的二叉树（没有left和right节点）
 * 2.取出根节点权值最小的两颗二叉树（即数列中值最小的两个元素）
 * 3.组成一棵新的二叉树，该新的二叉树的根节点的权值是前面两颗二叉树根节点权值的和
 * 4.再将这棵新的二叉树，以根节点的权值大小和数列剩余元素进行排序，不断重复1-2-3-4步骤，直到数列中，所有的数据都被处理，就得到一颗赫夫曼树
 * @version: 1.0
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {1,3,6,7,8,13,29};
        Node huffmanTree = createHuffmanTree(arr);
        preOrder(huffmanTree);

    }

    /**
     * 前序遍历赫夫曼树
     * @param root 根节点
     */
    public static void preOrder(Node root){
        if (root!=null){
            root.preOrder();
        }else{
            System.out.println("该树为空");
        }
    }

    /**
     * 数列构建赫夫曼树
     * @param arr 原始数列
     * @return  赫夫曼树的根节点
     */
    public static Node createHuffmanTree(int[] arr){
        //第一步为了操作方便，将数列中元素依次构建Node对象，并放入ArrayList中
        ArrayList<Node> nodes = new ArrayList<>();
        for(int value:arr){
            nodes.add(new Node(value));
        }
        //是一个循环的过程，直到集合中只剩一个节点，就是最终赫夫曼树的root节点
        while (nodes.size()>1) {
            //从小到大排序，由于实现了Comparable接口，可以用Collections.sort()进行排序
            Collections.sort(nodes);

            System.out.println("nodes="+nodes);
            //取出根节点权值最小的两颗二叉树,即集合中的前两个元素
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            //组成一棵新的二叉树，该新的二叉树的根节点的权值是前面两颗二叉树根节点权值的和
            Node parentNode = new Node(leftNode.value + rightNode.value);
            parentNode.left=leftNode;
            parentNode.right=rightNode;
            //将集合中权值最小的两棵二叉树移除
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将新组成的二叉树的root节点放入集合
            nodes.add(parentNode);
        }
        //返回赫夫曼树的root节点
        return nodes.get(0);
    }
}

class Node implements Comparable<Node>{
    int value;
    Node left;
    Node right;
    public Node(int value){
        this.value=value;
    }

    public void preOrder(){
        System.out.println(this.toString());
        if (this.left!=null){
            this.left.preOrder();
        }
        if (this.right!=null){
            this.right.preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.value-o.value;
    }
}