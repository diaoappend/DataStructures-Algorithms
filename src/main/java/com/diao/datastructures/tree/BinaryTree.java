package com.diao.datastructures.tree;

/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/3/13 10:58
 * @description:二叉树
 */
public class BinaryTree {
    HeroNode root;

    public static void main(String[] args) {
        HeroNode hero1 = new HeroNode(1,"aa");
        HeroNode hero2 = new HeroNode(2,"bb");
        HeroNode hero3 = new HeroNode(3,"cc");
        HeroNode hero4 = new HeroNode(4,"dd");
        hero1.setLeft(hero2);
        hero1.setRight(hero3);
        hero2.setLeft(hero4);

        BinaryTree binaryTree = new BinaryTree();
        binaryTree.root=hero1;
        binaryTree.preOrder();
        binaryTree.delNode(4);
        System.out.println("删除后的树");
        binaryTree.preOrder();

    }


    /**
     * 二叉树的前序遍历调用节点的前序遍历，其他方法类似
     */
    public void preOrder(){
        if (root!=null){
            root.preOrder();
        }else{
            System.out.println("该二叉树为空");
        }
    }

    /**
     * 二叉树的前序查找调用节点的前序查找
     * @param no
     * @return
     */
    public HeroNode preSearch(int no){
        if (root!=null){
            return root.preSearch(no);
        }else{
            throw new RuntimeException("该二叉树为空");
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder(){
        if (root!=null){
            root.infixOrder();
        }else{
            System.out.println("该二叉树为空");
        }
    }

    /**
     * 中序查找
     * @param no
     * @return
     */
    public HeroNode infixSearch(int no){
        if (root!=null){
            return root.infixSearch(no);
        }else{
            throw new RuntimeException("该二叉树为空");
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder(){
        if (root!=null){
            root.postOrder();
        }else{
            System.out.println("该二叉树为空");
        }
    }

    public HeroNode postSearch(int no){
        if (root!=null){
            return root.postSearch(no);
        }else{
            throw new RuntimeException("该二叉树为空");
        }
    }
    /**
     * 删除节点的方法
     * BinaryTree中进行：
     * 1.判断是否为空树，或者只有一个root节点，当节点的hNo=no时，即将root节点置空，也就是将树置空
     * HeroNode节点中进行：（判断当前节点的子节点是否为待删除节点，而不能判断当前节点是否为待删除节点）
     * 2.如果当前节点的左子节点不为空，且就是要删除的节点，将this.left=null；并且返回（结束递归删除）
     * 3.如果当前节点的右子节点不为空，且就是要删除的节点，将this.right=null；并且返回（结束递归删除）
     * 4.如果第2和第3步没有删除待删除节点，那么就向左子树递归
     * 5.如果第4步也没有删除待删除节点，那么就向右子树递归
     * @param no
     */
    public void delNode(int no){
        if(root!=null){
            if (root.gethNo()==no){
                root = null;
            }else{
                root.delNode(no);
            }
        }else {
            System.out.println("该树为空！");
        }
    }
}

/**
 * 定义节点
 */
class HeroNode {
    private int hNo;
    private String hName;
    private HeroNode left;
    private HeroNode right;

    public HeroNode(int hNo, String hName) {
        this.hNo = hNo;
        this.hName = hName;
    }

    public HeroNode() {
    }

    public int gethNo() {
        return hNo;
    }

    public void sethNo(int hNo) {
        this.hNo = hNo;
    }

    public String gethName() {
        return hName;
    }

    public void sethName(String hName) {
        this.hName = hName;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "hNo=" + hNo +
                ", hName='" + hName + '\'' +
                '}';
    }

    /**
     * 前序遍历：先遍历父节点，再遍历左子节点，最后遍历右子节点
     */
    public void preOrder() {
        System.out.println(this.toString());
        if (this.left!=null){
            this.left.preOrder();
        }
        if (this.right!=null){
            this.right.preOrder();
        }
    }

    /**
     * 前序查找
     * @param no
     * @return
     */
    public HeroNode preSearch(int no){
        if (this.hNo==no){
            return this;
        }
        HeroNode resNode = null;
        if (this.left!=null){
            resNode=this.left.preSearch(no);
        }
        if (resNode!=null){
            return resNode;
        }else{
            resNode = this.right.preSearch(no);
            return resNode;
        }
    }
    /**
     * 中序遍历：先遍历左子节点，再遍历父节点，最后遍历右子节点
     */
    public void infixOrder(){
        if (this.left!=null){
            this.left.preOrder();
        }
        System.out.println(this.toString());
        if (this.right!=null){
            this.right.preOrder();
        }
    }

    /**
     * 中序查找
     * @param no
     * @return
     */
    public HeroNode infixSearch(int no){
        HeroNode resNode = null;
        if (this.left!=null){
            resNode=this.left.infixSearch(no);
        }
        if (resNode!=null){
            return resNode;
        }
        if (this.hNo==no){
            return this;
        }
        if (this.right!=null){
            resNode=this.right.infixSearch(no);
        }
        return resNode;
    }

    /**
     * 后序遍历：先遍历左子节点，再遍历右子节点，最后遍历父节点
     */
    public void postOrder(){
        if (this.left!=null){
            this.left.preOrder();
        }
        if (this.right!=null){
            this.right.preOrder();
        }
        System.out.println(this.toString());
    }

    public HeroNode postSearch(int no){
        HeroNode resNode = null;
        if (this.left!=null){
            resNode= this.left.postSearch(no);
        }
        if (resNode!=null){
            return resNode;
        }
        if (this.right!=null){
            resNode = this.right.postSearch(no);
        }
        if (resNode!=null){
            return resNode;
        }
        if (this.hNo==no){
            return this;
        }else{
            return resNode;
        }
    }

    /**
     * 删除节点
     * @param no 节点编号
     */
    public void delNode(int no){
        if (this.left!=null&&this.left.hNo==no){
            this.left=null;
            return;
        }
        if (this.right!=null&&this.right.hNo==no){
            this.right=null;
            return;
        }
        if (this.left!=null){
            this.left.delNode(no);
        }
        if (this.right!=null){
            this.right.delNode(no);
        }
    }
}
