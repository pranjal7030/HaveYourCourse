package com.example.coursepool2;

public class MYUPLOADS_getterSetter {


        String date;
        String cap_and_status;
        String type;
        String file;
        String email;


        public MYUPLOADS_getterSetter () {

        }

        public MYUPLOADS_getterSetter (String date, String cap_and_status, String type, String file,String email) {
            this.date = date;
            this.cap_and_status = cap_and_status;
            this.type = type;
            this.file = file;
            this.email=email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getcap_and_status() {
            return cap_and_status;
        }

        public void setcap_and_status(String ques_and_ans) {
            this.cap_and_status = ques_and_ans;
        }
    }


