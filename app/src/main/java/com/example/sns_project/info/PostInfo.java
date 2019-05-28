package com.example.sns_project.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostInfo implements Serializable { //PostAdapter에서 putExtra를 쓰기 위해

    private String title; //게시글 제목
    private String guidePay; //가이드 가격
    private String guideHour; //가이드 시간
    private String car; //차 소유여부
    private ArrayList<String> contents; //내용
    private String publisher; //게시글 작성자
    private Date createdAt; //생성 날짜
    private String postId; //게시물 ID


    //생성자
    public PostInfo(String title,String guidePay, String guideHour,String car,ArrayList<String> contents, String publisher, Date createAt, String postId) {
        this.title = title;
        this.guidePay = guidePay;
        this.guideHour = guideHour;
        this.car = car;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createAt;
        this.postId = postId;
    }

    public PostInfo(String title,String guidePay, String guideHour,String car, ArrayList<String> contents, String publisher, Date createAt) {
        this.title = title;
        this.guidePay = guidePay;
        this.guideHour = guideHour;
        this.car = car;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createAt;
    }

    public Map<String, Object> getPostInfo(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("title", title);
        docData.put("guidePay", guidePay);
        docData.put("guideHour", guideHour);
        docData.put("car", car);
        docData.put("contents", contents);
        docData.put("publisher", publisher);
        docData.put("createdAt", createdAt);

        return docData;
    }

    //getter & setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getContents() {
        return contents;
    }

    public void setContents(ArrayList<String> contents) {
        this.contents = contents;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createAt) {
        this.createdAt = createAt;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getGuidePay() {
        return guidePay;
    }

    public void setGuidePay(String guidePay) {
        this.guidePay = guidePay;
    }

    public String getGuideHour() {
        return guideHour;
    }

    public void setGuideHour(String guideHour) {
        this.guideHour = guideHour;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }
}
