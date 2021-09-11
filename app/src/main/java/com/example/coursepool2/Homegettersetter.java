package com.example.coursepool2;
/*aboutcourse,courseincludes,prices,courseupdatenotify,teachername*/
public class Homegettersetter {
    String id;
    String image;
    String title;
    String aboutcourse;
    String courseincludes;
    String pices;
    String courseupdatenotify;


    public Homegettersetter()
    {

    }

    public Homegettersetter(String id, String image, String title, String aboutcourse,String courseincludes,String pices,String courseupdatenotify) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.aboutcourse=aboutcourse;
        this.courseincludes=courseincludes;
        this.pices=pices;
        this.courseupdatenotify=courseupdatenotify;

    }

    public String getAboutcourse() {
        return aboutcourse;
    }

    public void setAboutcourse(String aboutcourse) {
        this.aboutcourse = aboutcourse;
    }

    public String getCourseincludes() {
        return courseincludes;
    }

    public void setCourseincludes(String courseincludes) {
        this.courseincludes = courseincludes;
    }

    public String getPices() {
        return pices;
    }

    public void setPices(String pices) {
        this.pices = pices;
    }

    public String getCourseupdatenotify() {
        return courseupdatenotify;
    }

    public void setCourseupdatenotify(String courseupdatenotify) {
        this.courseupdatenotify = courseupdatenotify;
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
