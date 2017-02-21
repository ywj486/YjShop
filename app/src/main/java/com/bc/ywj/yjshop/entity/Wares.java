package com.bc.ywj.yjshop.entity;

/**
 * Created by Administrator on 2017/2/20 0020.
 */
public class Wares extends BaseBean {
    private String imgUrl;
    private String name;

    public Wares(String imgUrl, String name, String price) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.price = price;
    }

    private String price;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
