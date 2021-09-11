package com.example.coursepool2;

public class CommunityUploadgettersetter {
    String id;
    String email;
    String name;
    String file;
    String filecapstatus;
    String date;
    String type;

    public CommunityUploadgettersetter() {
    }

    public CommunityUploadgettersetter(String id, String email, String name, String file, String filecapstatus, String date,String type) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.file = file;
        this.filecapstatus = filecapstatus;
        this.date = date;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFilecapstatus() {
        return filecapstatus;
    }

    public void setFilecapstatus(String filecapstatus) {
        this.filecapstatus = filecapstatus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
