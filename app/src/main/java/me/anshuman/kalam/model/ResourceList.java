package me.anshuman.kalam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.Keep;

@Keep
public class ResourceList {
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("name")
    @Expose
    private String name;

    public ResourceList(String link, String name) {
        this.link = link;
        this.name = name;
    }

    public ResourceList() {

    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String start) {
        this.name = name;
    }
}
