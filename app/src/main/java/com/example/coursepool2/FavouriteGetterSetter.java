package com.example.coursepool2;

public class FavouriteGetterSetter {

   String video_id;
    String course_id;
    String course_title;
    String video_title;
    String video_link;
    String image;


    public FavouriteGetterSetter()
    {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public FavouriteGetterSetter(String video_id, String course_id, String course_title, String video_title, String video_link, String image) {
        this.video_id = video_id;
        this.course_id = course_id;
        this.course_title = course_title;
        this.video_title = video_title;
        this.video_link = video_link;
        this.image = image;

    }



    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }





    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }
}

