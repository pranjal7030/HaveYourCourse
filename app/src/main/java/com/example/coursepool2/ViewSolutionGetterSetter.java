package com.example.coursepool2;

public class ViewSolutionGetterSetter {
    String question_id;
    String video_id;
    String email;


    public ViewSolutionGetterSetter()
    {

    }
    public ViewSolutionGetterSetter(String question_id,String video_id,String email)
    {
        this.question_id=question_id;
        this.video_id=video_id;
        this.email=email;

    }


    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

