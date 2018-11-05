package com.example.materialtest;

/**
 * 水果实体类
 * 
 */

public class Fruit {
    private String name; //水果名称
    private int imageId; //水果图片ID

    public Fruit(String name,int imageId){
        this.name=name;
        this.imageId=imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
