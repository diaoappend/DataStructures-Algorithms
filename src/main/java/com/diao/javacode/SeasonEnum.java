package com.diao.javacode;

/**
 * @author chenzhidiao
 * @version 1.0
 * @date 2020/4/4 17:58
 * @description: 枚举
 */
enum SeasonEnum {
    SPRING(1),SUMMER(2),AUTUMN(3),WINTER(4);
    private int index;
    private SeasonEnum(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

class EnumTest{
    public static void main(String[] args) {
        //获取枚举中的所有对象的数组
        SeasonEnum[] values = SeasonEnum.values();
        System.out.println(values[2]);
        //toSring()方法
        System.out.println(SeasonEnum.SPRING.toString());
        //valueOf()方法
        System.out.println(SeasonEnum.valueOf("SPRING"));
        //ordinal()方法
        System.out.println(SeasonEnum.SPRING.ordinal());
        //获取枚举对象的属性值
        System.out.println(SeasonEnum.SPRING.getIndex());
    }
}
