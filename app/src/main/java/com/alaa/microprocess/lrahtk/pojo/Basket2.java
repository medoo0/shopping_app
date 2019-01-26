package com.alaa.microprocess.lrahtk.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by microprocess on 2019-01-26.
 */

public class Basket2 implements Parcelable {
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("product")
    @Expose
    private Product2 product;
    @SerializedName("price")
    @Expose
    private int price;

    protected Basket2(Parcel in) {
        quantity = in.readInt();
        id = in.readString();
        product = in.readParcelable(Product2.class.getClassLoader());
        price = in.readInt();
    }

    public static final Creator<Basket2> CREATOR = new Creator<Basket2>() {
        @Override
        public Basket2 createFromParcel(Parcel in) {
            return new Basket2(in);
        }

        @Override
        public Basket2[] newArray(int size) {
            return new Basket2[size];
        }
    };

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product2 getProduct() {
        return product;
    }

    public void setProduct(Product2 product) {
        this.product = product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(quantity);
        parcel.writeString(id);
        parcel.writeParcelable(product, i);
        parcel.writeInt(price);
    }
}
