
package me.anshuman.kalam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CMSDATA {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("picurl")
    @Expose
    private String picurl;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("semester1")
    @Expose
    private String semester1;
    @SerializedName("semester2")
    @Expose
    private String semester2;
    @SerializedName("semester3")
    @Expose
    private String semester3;
    @SerializedName("semester4")
    @Expose
    private String semester4;
    @SerializedName("section")
    @Expose
    private String section;

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

    public String getSemester1() {
        return semester1;
    }

    public void setSemester1(String semester1) {
        this.semester1 = semester1;
    }

    public String getSemester2() {
        return semester2;
    }

    public void setSemester2(String semester2) {
        this.semester2 = semester2;
    }

    public String getSemester3() {
        return semester3;
    }

    public void setSemester3(String semester3) {
        this.semester3 = semester3;
    }

    public String getSemester4() {
        return semester4;
    }

    public void setSemester4(String semester4) {
        this.semester4 = semester4;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
