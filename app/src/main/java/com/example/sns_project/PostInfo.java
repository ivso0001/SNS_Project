package com.example.sns_project;

import java.util.ArrayList;
import java.util.Date;

public class PostInfo {
    private String title;
    private ArrayList<String> contents;
    private String publisher;
    private Date createdAt;
    private String id;

    public PostInfo(String title, ArrayList<String> contents, String publisher, Date createdAt, String id){
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.id = id;
    }

    public PostInfo(String title, ArrayList<String> contents, String publisher, Date createdAt){
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public ArrayList<String> getContents(){
        return this.contents;
    }
    public void setContents(ArrayList<String> contents){
        this.contents = contents;
    }
    public String getPublisher(){
        return this.publisher;
    }
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }
    public Date getCreatedAt(){
        return this.createdAt;
    }
    public void setCreatedAt(Date createdAt){
        this.createdAt = createdAt;
    }
    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id = id;
    }
}
