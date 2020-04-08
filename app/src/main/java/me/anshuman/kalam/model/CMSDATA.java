package me.anshuman.kalam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CMSDATA {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("picurl")
    @Expose
    private String picurl;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("attendance")
    @Expose
    private List<String> attendance = null;
    @SerializedName("course")
    @Expose
    private String course;
    @SerializedName("sem")
    @Expose
    private Integer sem;
    @SerializedName("timetable")
    @Expose
    private String timetable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<String> attendance) {
        this.attendance = attendance;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getSem() {
        return sem;
    }

    public void setSem(Integer sem) {
        this.sem = sem;
    }

    public String getTimetable(){return timetable;}

    public void setTimetable(String timetable){this.timetable=timetable;}
}
