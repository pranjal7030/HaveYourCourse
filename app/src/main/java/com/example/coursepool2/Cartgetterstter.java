package com.example.coursepool2;

public class Cartgetterstter {

    String id;
    String image;
    String title;
    String price;


    public Cartgetterstter()
    {

    }

    public Cartgetterstter(String id, String image, String title, String price) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.price = price;

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



}
