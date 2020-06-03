package com.diao.datastructures.graph;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author: chenzhidiao
 * @date: 2020/6/2 22:20
 * @description: 图
 * @version: 1.0
 */
public class Graph {
    private ArrayList<String> vertexList;//存储顶点集合
    private int[][] edges;//存储图对应的邻接矩阵
    private int numOfedges;//表示边的数目

    //定义一个数组Boolean[]，记录某个结点是否被访问过
    private boolean[] isVisited ;
    public static void main(String[] args) {
        int n = 5;
        String VertextValue[] ={"A","B","C","D","E"};
        Graph graph = new Graph(5);
        for (String s : VertextValue) {
            graph.insertVertex(s);
        }
        //添加边
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,2,1);
        graph.insertEdge(1,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);

        graph.showGraph();
    }

    public Graph(int n){
        //初始化矩阵和vertexList，有几个顶点就是几乘几的二维数组
        edges=new int[n][n];
        vertexList = new ArrayList<String>(n);
        numOfedges = 0;//默认为0
        isVisited = new boolean[n];
    }

    //插入顶点
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1 表示起始顶点的下标
     * @param v2 表示终止顶点的下标
     * @param weigh 两个顶点间的关系，有关系为1，没关系为0
     */
    public void insertEdge(int v1,int v2,int weigh){
        edges[v1][v2]=weigh;
        edges[v2][v1]=weigh;
        numOfedges++;
    }
    //返回顶点数量
    public int getVertexCount(){
        return vertexList.size();
    }
    //返回边的数量
    public int getEdgeCount(){
        return numOfedges;
    }

    //返回结点i（下标）对应的数据 0->"A" 1->"B"
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    //返回v1和v2的权值
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }
    //显示图对应的矩阵
    public void showGraph(){
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * [0, 1, 1, 0, 0]
     * [1, 0, 1, 1, 1]
     * [1, 1, 0, 0, 0]
     * [0, 1, 0, 0, 0]
     * [0, 1, 0, 0, 0]
     * 得到起始结点第一个邻接结点的下标：比如在上面的矩阵中，起始结点"A"在结点集合中下标为0，即第一列，然后j从0开始，当j=1时，edges[0][1]=1>0,返回1
     * 在结点集合中，下标为1的结点为"B"
     * @param index 起始结点的下标
     * @return 如果存在就返回对应的下标，不存在返回-1
     */
    public int getFirstNeighbor(int index){
        for (int j=0;j<vertexList.size();j++){
            if (edges[index][j]>0){
                return j;
            }
        }
        return -1;
    }

    //根据前一个邻接结点的下标来获取下一个邻接结点
    public int getNextNeighbor(int v1,int v2){
        for (int j=v2+1;j<vertexList.size();j++){
            if (edges[v1][j]>0){
                return j;
            }
        }
        return -1;
    }

    public void dfs(boolean[] isVisited,int i){

    }
}
