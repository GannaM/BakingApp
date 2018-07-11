package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

public class Step {

    @SerializedName("id")
    private int id;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("description")
    private String longDescription;

    @SerializedName("videoURL")
    private String videoUrl;

    @SerializedName("thumbnailURL")
    private String thumbnailUrl;


    // CONSTRUCTORS

    public Step() {}

    public Step(int id, String shortDescription, String longDescription, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }


    // GETTERS

    public int getId() { return id; }
    public String getShortDescription() { return shortDescription; }
    public String getLongDescription() { return longDescription; }
    public String getVideoUrl() { return  videoUrl; }
    public String getThumbnailUrl() { return thumbnailUrl; }


    // SETTERS;

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDescription(String description) {
        this.shortDescription = description;
    }

    public void setLongDescription(String description) {
        this.longDescription = description;
    }

    public void setVideoUrl(String url) {
        this.videoUrl = url;
    }

    public void setThumbnailUrl(String url) {
        this.thumbnailUrl = url;
    }
}
