package com.bc.ywj.yjshop.entity;

/**
 * Created by Administrator on 2017/2/15 0015.
 */
public class HomeCategory extends Category {
    private int imgBig;
    private int imgSamllTop;
    private int imgSmallBottom;

    public int getImgBig() {
        return imgBig;
    }

    public HomeCategory(String name, int imgBig, int imgSamllTop, int imgSmallBottom) {
        super(name);
        this.imgBig = imgBig;
        this.imgSamllTop = imgSamllTop;
        this.imgSmallBottom = imgSmallBottom;
    }

    public void setImgBig(int imgBig) {
        this.imgBig = imgBig;
    }

    public int getImgSamllTop() {
        return imgSamllTop;
    }

    public void setImgSamllTop(int imgSamllTop) {
        this.imgSamllTop = imgSamllTop;
    }

    public int getImgSmallBottom() {
        return imgSmallBottom;
    }

    public void setImgSmallBottom(int imgSmallBottom) {
        this.imgSmallBottom = imgSmallBottom;
    }

}
