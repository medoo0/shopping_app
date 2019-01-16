package com.alaa.microprocess.lrahtk.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by microprocess on 2019-01-15.
 */

public class Basket {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("quantity")
    @Expose
    private int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}