package com.diao.datastructures.tree;

/**
 * @author: chenzhidiao
 * @date: 2020/5/30 21:23
 * @description: 二叉排序树，又叫二叉搜索树
 * 节点的删除：
 * 1.找到待删除的节点
 * 2.找到待删除的节点的父节点
 * <p>
 * 待删除节点分3种情况：
 * ①没有叶子节点：删除时直接将父节点指向待删除节点的指针置空
 * ②有1个叶子节点：删除时将父节点指向待删除节点的指针指向待删除节点的子节点
 * ③有2个叶子节点：
 * a.找到该节点的右子树中值最小的节点（从右子树开始递归查找其左子树，直到左子树为空，那个左子树为空的节点的值就是待删除节点右子树中的最小值
 * b.将这个最小值保存在临时变量中，然后删除最小值的节点
 * c.将待删除节点的值置为右子树的最小值。
 * @version: 1.0
 */
public class BinarySearchTree {
    Node2 root;

    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9};
        BinarySearchTree bst = new BinarySearchTree();
        for (int i : arr) {
            bst.add(new Node2(i));
        }
        bst.deleteNode(9);
        bst.infixOrder();
    }

    public void add(Node2 node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    public void infixOrder() {
        if (root == null) {
            System.out.println("该树为空");
        } else {
            root.infixOrder();
        }
    }

    public Node2 searchNode(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchNode(value);
        }
    }

    public Node2 searchParentNode(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParentNode(value);
        }
    }

    /**
     * 删除节点的方法
     *
     * @param value 待删除节点的值
     */
    public void deleteNode(int value) {
        if (root == null) {
            return;
        } else {
            Node2 targetNode = searchNode(value);
            //如果没有待删除节点，直接结束
            if (targetNode == null) {
                return;
            }
            //如果该树只有一个节点root，且它就是待删除节点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }
            Node2 targetNodeParent = searchParentNode(value);
            //如果待删除节点没有子节点
            if (targetNode.left == null && targetNode.right == null) {
                //判断待删除节点是父节点的哪个节点，将对应指针置空
                if (targetNodeParent.left != null && targetNodeParent.left == targetNode) {
                    targetNodeParent.left = null;
                } else if (targetNodeParent.right != null && targetNodeParent.right == targetNode) {
                    targetNodeParent.right = null;
                }

            } else if (targetNode.left != null && targetNode.right != null) {//如果待删除节点有2个子节点
                //a.找到该节点的右子树中值最小的节点（右子树中递归查找左子树，直到左子树为空，那么最后这个节点的值就是待删除节点右子树中的最小值）
                //b.将这个最小值保存在临时变量，然后将该最小值节点删除
                //c.将待删除的节点的值置为右子树的最小值
                int tmp = delRigtTreeMin(targetNode.right);
                targetNode.value=tmp;

            }else {//待删除节点有1个子节点
                //如果待删除节点有一个左子节点
                if (targetNode.left!=null){
                    if (targetNodeParent==null){
                        root=targetNode.left;
                    }else{
                        if (targetNodeParent.left.value==value){
                            targetNodeParent.left=targetNode.left;
                        }else{
                            targetNodeParent.right=targetNode.left;
                        }
                    }
                }else{//如果待删除节点有一个右子节点
                    if (targetNodeParent==null){
                        root=targetNode.right;
                    }else{
                        if (targetNodeParent.right.value==value){
                            targetNodeParent.right=targetNode.right;
                        }else{
                            targetNodeParent.left=targetNode.right;
                        }
                    }
                }
            }
        }
    }

    /**
     * 1.返回以node为根节点的二叉排序树的最小节点的值
     * 2.删除node为根节点的二叉排序树的最小节点
     * @param node 传入的节点（当做二叉排序树的根节点）
     * @return  返回的以node为根节点的二叉树的最小节点的值
     */
    public int delRigtTreeMin(Node2 node){
        Node2 target = node;
        while (target.left!=null){
            target=target.left;
        }
        deleteNode(target.value);
        return target.value;
    }
}

class Node2 {
    int value;
    Node2 left;
    Node2 right;

    public Node2(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node2{" +
                "value=" + value +
                '}';
    }

    /**
     * 添加节点的方法
     *
     * @param node
     */
    public void add(Node2 node) {
        if (node.value < this.value) {
            if (this.left == null) {
                this.left = node;
            } else {
                this.left.add(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
            } else {
                this.right.add(node);
            }
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this.toString());
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 根据给定的值查找待删除节点
     *
     * @param value
     * @return
     */
    public Node2 searchNode(int value) {
        if (this.value == value) {
            return this;
        } else if (this.value > value && this.left != null) {
            return this.left.searchNode(value);
        } else if (this.value < value && this.right != null) {
            return this.right.searchNode(value);
        } else {
            System.out.println("未找到待删除节点");
            return null;
        }
    }

    /**
     * 查找待删除节点的父节点
     *
     * @param value
     * @return
     */
    public Node2 searchParentNode(int value) {
        //如果当前节点的左子节点或右子节点的值为给定值，那当前节点就是父节点
        if (this.left.value == value || this.right.value == value) {
            return this;
        } else if (this.left != null && this.value > value) {
            return this.left.searchParentNode(value);
        } else if (this.right != null && this.value < value) {
            return this.right.searchParentNode(value);
        } else {
            System.out.println("未找到待删除节点的父节点");
            return null;
        }
    }


}
