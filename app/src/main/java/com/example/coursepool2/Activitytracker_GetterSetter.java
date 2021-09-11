package com.example.coursepool2;

public class Activitytracker_GetterSetter {
    String date;
    String ques;
    String coursetilte;
    String videotitle;
    String solution;



    public Activitytracker_GetterSetter() {

    }

    public Activitytracker_GetterSetter(String date, String ques, String coursetilte, String videotitle, String solution) {
        this.date = date;
        this.ques = ques;
        this.coursetilte = coursetilte;
        this.videotitle = videotitle;
        this.solution = solution;
    }

    public String getCoursetilte() {
        return coursetilte;
    }

    public void setCoursetilte(String coursetilte) {
        this.coursetilte = coursetilte;
    }

    public String getVideotitle() {
        return videotitle;
    }

    public void setVideotitle(String videotitle) {
        this.videotitle = videotitle;
    }

    public String getQuestion_of_solution() {
        return ques;
    }

    public void setQuestion_of_solution(String question_of_solution) {
        this.ques = question_of_solution;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQues_and_ans() {
        return solution;
    }

    public void setQues_and_ans(String ques_and_ans) {
        this.solution = ques_and_ans;
    }
}
