package com.example.coursepool2;

public class Videogettersetter extends Homegettersetter {
    String video_id;
    String Video_title;
    String Video_link;
    String Course_id;
    String Course_title;
    String thumbnail;
    String vdeosub;
    String complete;

    public Videogettersetter()
    {

    }

    public Videogettersetter(String video_id, String video_title, String video_link, String Course_id, String Course_title,String thumbnail,String videosub,String complete) {
        this.video_id = video_id;
        Video_title = video_title;
        Video_link = video_link;
        this.Course_id=Course_id;
        this.Course_title=Course_title;
        this.thumbnail=thumbnail;
        this.vdeosub=videosub;
        this.complete=complete;

    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVdeosub() {
        return vdeosub;
    }

    public void setVdeosub(String vdeosub) {
        this.vdeosub = vdeosub;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_title() {
        return Video_title;
    }

    public void setVideo_title(String video_title) {
        Video_title = video_title;
    }

    public String getVideo_link() {
        return Video_link;
    }

    public void setVideo_link(String video_link) {
        Video_link = video_link;
    }

    public String getCourse_id() {
        return Course_id;
    }

    public void setCourse_id(String course_id) {
        Course_id = course_id;
    }

    public String getCourse_title() {
        return Course_title;
    }

    public void setCourse_title(String course_title) {
        Course_title = course_title;
    }
}

