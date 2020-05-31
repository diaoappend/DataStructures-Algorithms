package com.diao.datastructures.tree;

/**
 * @author: chenzhidiao
 * @date: 2020/5/31 8:07
 * @description:AVL树——平衡二叉树
 * @version: 1.0
 */
public class AVLTree {
    public static void main(String[] args) {
        int[] arr = {10, 11, 7, 6, 8, 9};
        AVLTree avlTree = new AVLTree();
        for (int i : arr) {
            avlTree.add(new Node3(i));
        }
        int height = avlTree.root.height();
        int leftHeight = avlTree.root.leftHeight();
        int rightHeight = avlTree.root.rightHeight();
        System.out.println("AVL树的高度=" + height + ";左子树高度=" + leftHeight + ";右子树高度=" + rightHeight);
    }

    Node3 root;

    public Node3 getRoot() {
        return root;
    }

    public void add(Node3 node) {
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

    public Node3 searchNode(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchNode(value);
        }
    }

    public Node3 searchParentNode(int value) {
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
            Node3 targetNode = searchNode(value);
            //如果没有待删除节点，直接结束
            if (targetNode == null) {
                return;
            }
            //如果该树只有一个节点root，且它就是待删除节点
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }
            Node3 targetNodeParent = searchParentNode(value);
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
                targetNode.value = tmp;

            } else {//待删除节点有1个子节点
                //如果待删除节点有一个左子节点
                if (targetNode.left != null) {
                    if (targetNodeParent == null) {
                        root = targetNode.left;
                    } else {
                        if (targetNodeParent.left.value == value) {
                            targetNodeParent.left = targetNode.left;
                        } else {
                            targetNodeParent.right = targetNode.left;
                        }
                    }
                } else {//如果待删除节点有一个右子节点
                    if (targetNodeParent == null) {
                        root = targetNode.right;
                    } else {
                        if (targetNodeParent.right.value == value) {
                            targetNodeParent.right = targetNode.right;
                        } else {
                            targetNodeParent.left = targetNode.right;
                        }
                    }
                }
            }
        }
    }

    /**
     * 1.返回以node为根节点的二叉排序树的最小节点的值
     * 2.删除node为根节点的二叉排序树的最小节点
     *
     * @param node 传入的节点（当做二叉排序树的根节点）
     * @return 返回的以node为根节点的二叉树的最小节点的值
     */
    public int delRigtTreeMin(Node3 node) {
        Node3 target = node;
        while (target.left != null) {
            target = target.left;
        }
        deleteNode(target.value);
        return target.value;
    }
}

class Node3 {
    int value;
    Node3 left;
    Node3 right;

    public Node3(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node3{" +
                "value=" + value +
                '}';
    }

    /**
     * 添加节点的方法
     *
     * @param node
     */
    public void add(Node3 node) {
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
        //在添加节点后，判断：(右子树的高度-左子树的高度)>1，如果成立则进行左旋转
        if (rightHeight() - leftHeight() > 1) {
            //如果当前节点的右子节点的左子树的高度大于右子树高度，先对当前节点的右子节点进行右旋转，然后对当前节点进行左旋转
            if (right != null && right.leftHeight() > right.rightHeight()) {
                right.rightRotate();
                leftRotate();
            } else {
                leftRotate();
            }
            return;//非常必要
        }
        //在添加节点后，判断：(左子树的高度-右子树的高度)>1，如果成立则进行右旋转
        if (leftHeight() - rightHeight() > 1) {
            //如果当前节点的左子节点的右子树的高度大于左子树高度，先对当前节点的左子节点进行左旋转，然后对当前节点进行右旋转
            if (left != null && left.rightHeight() > left.leftHeight()) {
                left.leftRotate();
                rightRotate();
            } else {
                rightRotate();
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
    public Node3 searchNode(int value) {
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
    public Node3 searchParentNode(int value) {
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

    /**
     * 返回当前节点的左子节点的高度
     *
     * @return
     */
    public int leftHeight() {
        return left == null ? 0 : left.height();
    }

    /**
     * 返回当前节点的右子节点的高度
     *
     * @return
     */
    public int rightHeight() {
        return right == null ? 0 : right.height();
    }

    /**
     * 返回以当前节点为艮节点的树的高度
     *
     * @return
     */
    public int height() {
        //当前节点的左子树和右子树高度的较大值然后+1就是当前节点的高度
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    /**
     * 左旋转
     * 1.创建1个新的节点，值等于当前根节点的值
     * 2.把新节点的左子树设置为当前节点的左子树
     * 3.把新节点的右子树设置为当前节点的右子树的左子树
     * 4.把当前节点的值换为当前节点右子节点的值
     * 5.把当前节点的右子树设置成右子树的右子树
     * 6.把当前节点的左子树设置为新节点
     */
    private void leftRotate() {
        Node3 temp = new Node3(value);
        temp.left = this.left;
        temp.right = this.right.left;
        this.value = this.right.value;
        this.right = this.right.right;
        this.left = temp;

    }

    /**
     * 右旋转：
     * 1.创建1个新的节点，值等于当前根节点的值
     * 2.把新节点的右子树设置为当前节点的右子树
     * 3.把新节点的左子树设置为当前节点的左子树的右子树
     * 4.把当前节点的值设置为当前节点左子树的值
     * 5.把当前节点的左子树设置为当前节点左子树的左子树
     * 6.把当前节点的右子树设置为新节点
     */
    private void rightRotate() {
        Node3 temp = new Node3(value);
        temp.right = right;
        temp.left = left.right;
        value = left.value;
        left = left.left;
        right = temp;

    }
}