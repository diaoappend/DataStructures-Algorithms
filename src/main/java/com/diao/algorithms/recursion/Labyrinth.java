package com.diao.algorithms.recursion;

/**
 * @author: Chenzhidiao
 * @date: 2020/1/8 9:26
 * @description:迷宫问题
 * @version: 1.0
 */
public class Labyrinth {
    public static void main(String[] args) {
        //创建一个二维数组，模拟迷宫
        int[][] map = new int[8][7];
        //使用1表示墙
        //将上下两行全部置为1
        for (int i =0;i<7;i++){
            map[0][i]=1;
            map[7][i]=1;
        }
        for (int j = 1;j<8-1;j++){
            map[j][0]=1;
            map[j][6]=1;
        }
        map[3][1]=1;
        map[3][2]=1;

        for (int[] row : map) {
            for (int i : row) {
                System.out.print(i+" ");
            }
            System.out.println();
        }
        boolean way = setWay(map, 1, 1, 6, 5);
        System.out.println(way);
        for (int[] row : map) {
            for (int i : row) {
                System.out.print(i+" ");
            }
            System.out.println();
        }
    }

    /**
     * 使用递归回溯来给小球找路
     * @param map 地图（二维数组）
     * @param i 起始点横坐标
     * @param j 起始点纵坐标
     * @param p 终点横坐标
     * @param q 终点纵坐标
     * @return 返回true表示找到路径，否则没找到
     */
    /**
     *说明：
     * 1.map 表示地图
     * 2.i,j 表示从地图的哪个位置出发（1,1）
     * 3.p,q 表示到地图的哪个位置（6,5）
     * 4.约定：当map[i][j]为0表示该点没有走过，当为1表示墙，2表示通路可以走，3表示该点已经走过，但是走不通
     * 5.在走迷宫时，需要确定一个策略（很重要），我们按照下->右->上->左的方式走，如果该点走不通，再回溯
     */

    /**
     * 如果想求路径最短问题，就统计各种策略的结果二维数组中2的个数，数量最少的也就是路径最短
     */
    public static boolean setWay(int[][] map,int i,int j,int p,int q){
        if (map[p][q]==2){//通路已经找到
            return true;
        }else {
            if (map[i][j]==0){//如果当前这个起始点还没走过
                //按照策略：下->右->上->左的方式走
                map[i][j]=2;//假定该点可以走通,先置为2
                if (setWay(map,i+1,j,p,q)){//向下走
                    return true;
                }else if (setWay(map,i,j+1,p,q)){//向右走
                    return true;
                }else if(setWay(map,i-1,j,p,q)){//向上走
                    return true;
                }else if(setWay(map,i,j-1,p,q)){//向左走
                    return true;
                }else {
                    map[i][j]=3;
                    return false;
                }
            }else {//如果map[i][j]！=0，可能是1,2,3
                return false;
            }
        }
    }

    /**
     * 修改策略为上->右->下->左
     * @param map
     * @param i
     * @param j
     * @param p
     * @param q
     * @return
     */
    public static boolean setWay1(int[][] map,int i,int j,int p,int q){
        if (map[p][q]==2){//通路已经找到
            return true;
        }else {
            if (map[i][j]==0){//如果当前这个起始点还没走过
                //按照策略：上->右->下->左的方式走
                map[i][j]=2;//假定该点可以走通,先置为2
                if (setWay1(map,i-1,j,p,q)){//向上走(注意递归调用时调用新策略的方法getWay1)
                    return true;
                }else if (setWay1(map,i,j+1,p,q)){//向右走
                    return true;
                }else if(setWay1(map,i+1,j,p,q)){//向下走
                    return true;
                }else if(setWay1(map,i,j-1,p,q)){//向左走
                    return true;
                }else {
                    map[i][j]=3;
                    return false;
                }
            }else {//如果map[i][j]！=0，可能是1,2,3
                return false;
            }
        }
    }
}
