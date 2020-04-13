package me.anshuman.kalam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.Keep;

@Keep
public class ClassDetail {
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("faculty")
    @Expose
    private String faculty;
    @SerializedName("room")
    @Expose
    private String room;

    public ClassDetail(String start, String subject, String faculty, String room) {
        this.subject = subject;
        this.start = start;
        this.faculty = faculty;
        this.room = room;
    }

    public ClassDetail() {

    }


    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}