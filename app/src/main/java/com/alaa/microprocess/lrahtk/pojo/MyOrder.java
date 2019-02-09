package com.alaa.microprocess.lrahtk.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by microprocess on 2019-01-26.
 */

public class MyOrder implements Parcelable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("basket")
    @Expose
    private List<Basket2> basket = null;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("__v")
    @Expose
    private int v;
    @SerializedName("id")
    @Expose
    private String id;

    protected MyOrder(Parcel in) {
        status = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        basket = in.createTypedArrayList(Basket2.CREATOR);
        total = in.readInt();
        address = in.readString();
        v = in.readInt();
        id = in.readString();
    }

    public static final Creator<MyOrder> CREATOR = new Creator<MyOrder>() {
        @Override
        public MyOrder createFromParcel(Parcel in) {
            return new MyOrder(in);
        }

        @Override
        public MyOrder[] newArray(int size) {
            return new MyOrder[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Basket2> getBasket() {
        return basket;
    }

    public void setBasket(List<Basket2> basket) {
        this.basket = basket;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(status);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeTypedList(basket);
        parcel.writeInt(total);
        parcel.writeString(address);
        parcel.writeInt(v);
        parcel.writeString(id);
    }
}
