package com.example.coursepool2;

public class UserHelpcentergettersetter {
    String Appreciation;
    String feedback;
    String Date;
    String name;

    public UserHelpcentergettersetter(){

    }


    public UserHelpcentergettersetter(String appreciation, String feedback, String date, String name) {
        Appreciation = appreciation;
        this.feedback = feedback;
        Date = date;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppreciation() {
        return Appreciation;
    }

    public void setAppreciation(String appreciation) {
        Appreciation = appreciation;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
