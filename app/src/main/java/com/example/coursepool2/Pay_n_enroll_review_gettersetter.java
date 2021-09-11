package com.example.coursepool2;

public class Pay_n_enroll_review_gettersetter {
    String name;
    String review;
    String rate;
    String date;

    public Pay_n_enroll_review_gettersetter() {
    }

    public Pay_n_enroll_review_gettersetter(String name, String review, String rate, String date) {
        this.name = name;
        this.review = review;
        this.rate = rate;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
