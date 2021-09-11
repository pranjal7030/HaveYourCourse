package com.example.coursepool2;

public class enrollcourse_gettersetter {
    //String email;
    String course_id;
    String course_title;
    String course_image;
    String enrollment_no;

    public enrollcourse_gettersetter(){

    }

    public enrollcourse_gettersetter( String course_id, String course_title, String course_image, String enrollment_no) {
       // this.email = email;
        this.course_id = course_id;
        this.course_title = course_title;
        this.course_image = course_image;
        this.enrollment_no = enrollment_no;
    }

   /* public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/

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

    public String getCourse_image() {
        return course_image;
    }

    public void setCourse_image(String course_image) {
        this.course_image = course_image;
    }

    public String getEnrollment_no() {
        return enrollment_no;
    }

    public void setEnrollment_no(String enrollment_no) {
        this.enrollment_no = enrollment_no;
    }
}
