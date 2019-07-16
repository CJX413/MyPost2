package com.email.post.Model;

public class PostModel {
    Integer id;
    String sender;
    String receiver;
    String topical;
    String content;
    public Integer getID(){ return this.id; }
    public String getSender(){ return this.sender; }
    public String getReceiver(){
        return this.receiver;
    }
    public String getTopical(){
        return this.topical;
    }
    public String getContent(){
        return this.content;
    }

    public void setID(Integer id) { this.id = id; }
    public void setSender(String sender) { this.sender = sender; }
    public void setContent(String content) {
        this.content = content;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public void setTopical(String topical) {
        this.topical = topical;
    }
}
