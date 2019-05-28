package com.example.sns_project.adapter;

import android.graphics.drawable.Drawable;

public class TourlistItem {
    private Drawable image;
    private String contitle;
    private String tourcontent;
    private String contId;
    private Double v;
    private Double v1;
    private String contypeid;
    private String addr;

    public void setimage(Drawable img) {
        image = img ;
    }
    public void setTitle(String title) {
        contitle = title ;
    }
    public void setTourcontent(String con) {
        tourcontent = con ;
    }
    public void setContId(String id) {
        contId = id ;
    }
    public void setV(Double mapx) {
        v = mapx ;
    }
    public void setV1(Double mapy) {
        v1 = mapy ;
    }
    public void setContypeid(String contyp) {
        contypeid = contyp ;
    }
    public void setAddr(String ad) {
        addr = ad ;
    }
    public Drawable getimage() {
        return this.image;
    }
    public String getContitle() {
        return this.contitle;
    }
    public String getTourcontent() {
        return this.tourcontent;
    }
    public String getContId() {
        return this.contId;
    }
    public Double getV() {
        return this.v;
    }
    public Double getV1() {
        return this.v1;
    }
    public String getContypeid() {
        return this.contypeid;
    }
    public String getAddr() {
        return this.addr;
    }

}
