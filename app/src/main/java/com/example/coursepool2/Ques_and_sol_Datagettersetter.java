package com.example.coursepool2;

public class Ques_and_sol_Datagettersetter {

    String Question_Id;
    String email;
    String Video_Id;
    String Question;
    String QuestionDate;
    String coursetitle;
    String Videotitle;


    public Ques_and_sol_Datagettersetter() {

    }

    public Ques_and_sol_Datagettersetter(String question_Id, String email, String video_Id, String question, String questionDate, String coursetitle, String videotitle) {
        Question_Id = question_Id;
        this.email = email;
        Video_Id = video_Id;
        Question = question;
        QuestionDate = questionDate;
        this.coursetitle = coursetitle;
        Videotitle = videotitle;
    }

    public String getCoursetitle() {
        return coursetitle;
    }

    public void setCoursetitle(String coursetitle) {
        this.coursetitle = coursetitle;
    }

    public String getVideotitle() {
        return Videotitle;
    }

    public void setVideotitle(String videotitle) {
        Videotitle = videotitle;
    }

    public String getQuestion_Id() {
        return Question_Id;
    }

    public void setQuestion_Id(String question_Id) {
        Question_Id = question_Id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVideo_Id() {
        return Video_Id;
    }

    public void setVideo_Id(String video_Id) {
        Video_Id = video_Id;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getQuestionDate() {
        return QuestionDate;
    }

    public void setQuestionDate(String questionDate) {
        QuestionDate = questionDate;
    }
}
