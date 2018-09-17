package com.example.longjinbin.medcine;

/**
 * Created by longjinbin on 2018/5/2.
 */

public class TuiJian {
    private String id;
    private String img;
    private String name;
    private String title;


    public TuiJian() {
    }

    public TuiJian(String _id, String _img, String _name,  String _title) {
        id=_id;
        img=_img;
        name = _name;
        title=_title;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String _title){
        this.title=_title;
    }


}
