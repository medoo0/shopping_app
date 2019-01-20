package com.alaa.microprocess.lrahtk.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by microprocess on 2019-01-20.
 */

public class PostComment {

    @SerializedName("stars")
    @Expose
    private float stars;
    @SerializedName("description")
    @Expose
    private String description;

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
