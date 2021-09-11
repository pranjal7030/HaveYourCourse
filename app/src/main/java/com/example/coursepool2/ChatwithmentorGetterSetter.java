package com.example.coursepool2;

public class ChatwithmentorGetterSetter {
    String message;
    String date;
    String type;
    public ChatwithmentorGetterSetter(){

    }

    public ChatwithmentorGetterSetter(String message, String date,String type) {
        this.message = message;
        this.date = date;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
