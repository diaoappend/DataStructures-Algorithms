package com.diao.datastructures.tree;

import java.util.*;

/**
 * @author: chenzhidiao
 * @date: 2020/5/29 21:01
 * @description: 赫夫曼编码
 * 赫夫曼编码实现压缩的步骤：
 * 1.创建节点类型Node{data{存放数据},weight{存放权值},left和right}
 * 2.得到待压缩数据对应的byte[]数组
 * 3.编写一个方法，将准备构建赫夫曼树的Node节点放到List集合中，节点的data就是每个字符，权值就是字符出现的次数
 * 4.根据List创建对应的赫夫曼树
 * 5.根据生成的赫夫曼树生成赫夫曼编码表
 * 6.根据赫夫曼编码表将源数据转成字节组成的字符串
 * 7.将字节字符串转为byte[]，即经过赫夫曼编码处理后最终的字节数组
 *
 * 赫夫曼解码（解压缩）步骤：
 * 1.将经过赫夫曼编码压缩的字节数组huffmanCodeBytes：[-88, -65, -56, -65, -56...转成经过赫夫曼编码对应的二进制字符串：1010100010111111110...
 * 2.赫夫曼编码对应的二进制字符串对照赫夫曼编码进行解码，还原原始数据
 * @version: 1.0
 */
public class HuffmanCode {

    public static void main(String[] args) {
        String context = "i like like like java do you like a java";
        byte[] huffmanCodeZip = zipHuffmanCode(context);
        System.out.println(Arrays.toString(huffmanCodeZip));
        byte[] bytes = unzipHuffmanCode(huffmanCodeZip, huffmanCodes);
        System.out.println(new String(bytes));

    }

    /**
     * 根据赫夫曼编码对压缩后的字节数组进行解码
     * @param huffmanCodeBytes 压缩后的字节数组
     * @param huffmanCodes 赫夫曼编码
     * @return 原数据对应的字节数组
     */
    public static byte[] unzipHuffmanCode(byte[] huffmanCodeBytes,Map<Byte,String> huffmanCodes){
        //用于拼接赫夫曼编码对应的二进制字符串,形式：1010100010111...
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组转成二进制的字符串
        for (int i = 0;i<huffmanCodeBytes.length;i++){
            //flag 标记该字节是否需要补高位，只有数组的最后一个元素不需要补高位
            boolean flag = (i==huffmanCodeBytes.length-1);
            stringBuilder.append(byteToBitString(!flag,huffmanCodeBytes[i]));
        }
        //把二进制字符串按照赫夫曼编码进行解码
        //将赫夫曼编码对应的map的key和value对换,因为后面要根据二进制字符串查找对应的字符，比如之前存的是a->100,现在根据100查找a
        Set<Byte> keys = huffmanCodes.keySet();
        HashMap<String, Byte> newMap = new HashMap<>();
        for (Byte key : keys) {
            newMap.put(huffmanCodes.get(key),key);
        }
        ArrayList<Byte> list = new ArrayList<>();
        for (int i = 0;i<stringBuilder.length();){
            int count = 1;
            boolean flag = true;//标识str对应的字符串是否为newMap中的key
            Byte b = null;
            while (flag){
                String str = stringBuilder.substring(i,i+count);//i不动，让count移动，截取长度依次增加
                 b = newMap.get(str);
                if (b!=null){//说明newMap中有str对应的key
                    flag=false;
                }else{
                    count++;
                }
            }
            list.add(b);
            i+=count;//i直接移动到count的位置，接着继续向后查找
        }
        //当for循环结束后，list中就存放了所有的字符："i like like like java do you like a java"
        //然后将list中的元素放到一个byte[]数组中
        byte[] sourceBytes = new byte[list.size()];
        for (int i = 0;i<sourceBytes.length;i++){
            sourceBytes[i] = list.get(i);
        }
        return sourceBytes;
    }

    /**
     * 将一个byte转成一个二进制的字符串，
     * @param flag 标记是否需要补高位，如果是true，表示需要补高位，否则不需要
     * @param b 传入的字节
     * @return 是该b 对应的二进制的字符串（注意是按补码返回）
     */
    public static String byteToBitString(boolean flag,byte b){
        int temp = b;
        if (flag){
            temp |= 256;//按位与256 1 0000 0000 | 0000 0001 => 1 0000 0001
        }
        String str = Integer.toBinaryString(temp);//返回的是temp对应的二进制的补码
        if (flag){
            return str.substring(str.length()-8);
        }else{
            return str;
        }
    }

    /**
     * 整理：只要传入原始内容，即可获取最终的byte[]数组
     * @param context
     * @return
     */
    public static byte[] zipHuffmanCode(String context){
        byte[] contextBytes = context.getBytes();
        List<Node1> nodes = getNodes(contextBytes);
        Node1 huffmanTree = createHuffmanTree(nodes);
        byte[] bytes = zip(contextBytes, getCodes(huffmanTree));
        return bytes;

    }
    /**
     * 步骤3：根据给定的字符数组，构建nodes集合
     * @param bytes
     * @return
     */
    public static List<Node1> getNodes(byte[] bytes){
        ArrayList<Node1> nodes = new ArrayList<>();
        //通过map来统计每个字符出现的次数
        HashMap<Byte, Integer> counts = new HashMap<>();
        for (byte aByte : bytes) {
            Integer count = counts.get(aByte);
            if (count==null){
                counts.put(aByte,1);
            }else{
                counts.put(aByte,count+1);
            }
        }
        //遍历map，构建nodes集合
        Set<Byte> bytes1 = counts.keySet();
        for (Byte aByte : bytes1) {
            nodes.add(new Node1(aByte, counts.get(aByte)));
        }
        return nodes;
    }

    /**
     * 步骤4：根据节点集合创建对应的赫夫曼树
     * @param nodes
     * @return
     */
    public static Node1 createHuffmanTree(List<Node1> nodes){

        while (nodes.size()>1){
            //先对集合元素进行排序
            Collections.sort(nodes);
            Node1 leftNode = nodes.get(0);
            Node1 rightNode = nodes.get(1);
            //创建一课新的二叉树，它的根节点没有data属性，只有weight
            Node1 parentNode = new Node1(null,leftNode.weight + rightNode.weight);
            parentNode.left = leftNode;
            parentNode.right = rightNode;
            //移除前两个节点并将新创建的二叉树放入List
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parentNode);

        }
        return nodes.get(0);
    }

    public static void preOrder(Node1 node){
        if (node!=null){
            node.preOrder();
        }else{
            System.out.println("赫夫曼树为空");
        }
    }

    //将赫夫曼编码表存放在Map<Byte,String>中，形式 32->01 97->100 100->11000等(其中32,97,100是字符的Unicode码)
    static Map<Byte,String> huffmanCodes = new HashMap<Byte,String>();
    //在生成赫夫曼编码表时，需要去拼接路径，定义一个StringBuilder存储某个叶子节点的路径
    static StringBuilder stringBuilder = new StringBuilder();
    /**
     *步骤5：功能：将传入的node节点的所有叶子节点的赫夫曼编码得到，并放入到huffmanCodes集合
     * @param node 传入节点
     * @param code 路径：左子节点是0，右子节点是1
     * @param stringBuilder 用于拼接路径
     */
    public static void getCodes(Node1 node,String code,StringBuilder stringBuilder){
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        //将code 加入到StringBuilder2
        stringBuilder2.append(code);
        if (node!=null){//node为空不处理
            //判断当前node节点是叶子节点还是非叶子节点
            if (node.data==null){
                //data为null说明为非叶子节点,进行递归处理
                //向左递归
                getCodes(node.left,"0",stringBuilder2);
                //向右递归
                getCodes(node.right,"1",stringBuilder2);
            }else{
                //如果是叶子节点，就表示找到了某个叶子节点的路径，将其放入map,key为节点的data值，value就是拼接后的路径stringBuilder2
                huffmanCodes.put(node.data,stringBuilder2.toString());
            }
        }
    }

    /**
     * 重载生成赫夫曼编码的方法，只需要传入赫夫曼树的根节点即可
     * @param root
     * @return
     */
    public static Map<Byte,String> getCodes(Node1 root){
        if(root == null){
            return null;
        }
        //处理root的左子树
        getCodes(root.left,"0",stringBuilder);
        //处理root的右子树
        getCodes(root.right,"1",stringBuilder);
        return huffmanCodes;
    }

    /**
     * 步骤6.7：将原始字符串对应的byte[]数组，通过生成的赫夫曼编码，返回一个经过赫夫曼编码压缩后的字节数组byte[]
     * @param bytes 原始字符串对应的byte[]数组
     * @param huffmanCodes 赫夫曼编码表
     * @return 返回赫夫曼编码处理后生产的字节数组byte[]
     * 举例：源字符串"i like like like java do you like a java"；
     *      赫夫曼编码：{32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
     *      转成的二进制字符串："1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100"
     *      将上面字符串按8位一个转成字符数组：第一个8位：10101000(补码)->10100111(反码)[推导：补码 10101000-1 得到反码 10100111]->11011000(最终的字符)->-88
     */
    public static byte[] zip(byte[] bytes,Map<Byte,String> huffmanCodes){
        //用于拼接源字符数组通过赫夫曼编码转成的字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }
        //System.out.println(stringBuilder.toString());
        //将得到的字符串按8位一个转成字符数组
        int len;//上面字符串转为字符数组后的数组长度
        int index=0;//定义字符数组的下标
       /* if (stringBuilder.length()%8==0){
            len=stringBuilder.length()/8;
        }else{
            len = stringBuilder.length()/8 +1;
        }*/
       len = (stringBuilder.length()+7)/8;
       //创建一个存储最终结果的字符数组，长度为len
        byte[] huffmanCodeByte = new byte[len];

        //遍历上面的字符串，每8位转为1个字符放到字符数组中
        for (int i = 0;i< stringBuilder.length();i+=8){//步长为8
            String str ;
            if (i+8>stringBuilder.length()){
                str = stringBuilder.substring(i);
            }else{
                str = stringBuilder.substring(i,i+8);
            }
            huffmanCodeByte[index] = (byte)Integer.parseInt(str,2);
            index++;
        }

        return huffmanCodeByte;
    }
}

class Node1 implements Comparable<Node1>{
    Byte data;
    int weight;
    Node1 left;
    Node1 right;
    public Node1(Byte data,int weight){
        this.data = data;
        this.weight = weight;
    }

    public Node1(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node1{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Node1 o) {
        return this.weight - o.weight;

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
}
