package com.example.longjinbin.medcine;

/**
 * Created by longjinbin on 2018/5/23.
 */

public class Icon {
    private int iId;
    private String iName;
    private int bg;

    public Icon() {
    }

    public Icon(int iId, String iName,int bg) {
        this.iId = iId;
        this.iName = iName;
        this.bg=bg;
    }

    public int getiId() {
        return iId;
    }

    public String getiName() {
        return iName;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public void setiName(String iName) {
        this.iName = iName;
    }

    public int getiBg() {
        return bg;
    }

    public void setiBg(int bg) {
        this.bg = bg;
    }
}
