package com.bc.bit.bean;

public class Imgbean {
    private int img;
    private String imgName;

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Imgbean(int img, String imgName) {
        this.img = img;
        this.imgName = imgName;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
