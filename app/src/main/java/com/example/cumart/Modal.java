package com.example.cumart;

public class Modal {
    private String title,desc,price,meet,image,imageid,currentuserid,tg,mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTitle() {
        return title;
    }

    public String getTg() {
        return tg;
    }

    public void setTg(String tg) {
        this.tg = tg;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMeet() {
        return meet;
    }

    public void setMeet(String meet) {
        this.meet = meet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }

    public String getCurrentuserid() {
        return currentuserid;
    }

    public void setCurrentuserid(String currentuserid) {
        this.currentuserid = currentuserid;
    }

    public Modal(String title, String price, String image,String imageid,String tg,String desc,String mobile) {
        this.title = title;
        this.price = price;
        this.image = image;
        this.imageid=imageid;
        this.tg=tg;
        this.desc=desc;
        this.mobile=mobile;
    }

    public Modal(String title, String desc, String price, String meet, String image, String imageid, String currentuserid, String tg, String mobile) {
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.meet = meet;
        this.image = image;
        this.imageid = imageid;
        this.currentuserid = currentuserid;
        this.tg=tg;
        this.mobile=mobile;
    }

    public Modal() {
    }
}
