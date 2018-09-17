package com.example.longjinbin.medcine;

import android.graphics.Bitmap;

/**
 * Created by longjinbin on 2018/5/2.
 */

public class Goods {
    private String id;
    private String img;
    private String name;
    private String price;
    private String title;
    private String number;
    private String goodsType;
    private String shopId;
    private String shopName;
    private String details;

    public Goods() {
    }

    public Goods(String _id,String _img,String _name, String _price,String _title,String _number,String _goodsType,String _shopId,String _shopName,String _details) {
        id=_id;
        img=_img;
        name = _name;
        price = _price;
        title=_title;
        number=_number;
        goodsType=_goodsType;
        shopId=_shopId;
        shopName=_shopName;
        details=_details;

    }
    public String getId() {
        return id;
    }

    public void setId(String _id){
        this.id=_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String _img){
        this.img=_img;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name){
        this.name=_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String _pric){
        this.price=_pric;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String _title){
        this.title=_title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String _number){
        this.number=_number;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String _goodsType){
        this.goodsType=_goodsType;
    }

    public void setShopId(String _shopId){
        this.shopId=_shopId;
    }

    public String getShopId() {
        return shopId;
    }


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String _shopName){
        this.shopName=_shopName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String _details){
        this.details=_details;
    }


}
