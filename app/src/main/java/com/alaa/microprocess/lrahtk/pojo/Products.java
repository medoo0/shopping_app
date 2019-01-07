package com.alaa.microprocess.lrahtk.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by microprocess on 2019-01-07.
 */

public class Products implements Parcelable {
    @SerializedName("featured")
    @Expose
    private boolean featured;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("gallery")
    @Expose
    private List<String> gallery = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("category")
    @Expose
    private parent_Categories category;
    @SerializedName("brand")
    @Expose
    private Brand brand;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("__v")
    @Expose
    private int v;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rating")
    @Expose
    private Rating rating;

    protected Products(Parcel in) {
        featured = in.readByte() != 0;
        thumbnail = in.readString();
        gallery = in.createStringArrayList();
        createdAt = in.readString();
        updatedAt = in.readString();
        name = in.readString();
        price = in.readInt();
        quantity = in.readInt();
        code = in.readString();
        description = in.readString();
        v = in.readInt();
        id = in.readString();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getGallery() {
        return gallery;
    }

    public void setGallery(List<String> gallery) {
        this.gallery = gallery;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public parent_Categories getCategory() {
        return category;
    }

    public void setCategory(parent_Categories category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (featured ? 1 : 0));
        parcel.writeString(thumbnail);
        parcel.writeStringList(gallery);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeInt(quantity);
        parcel.writeString(code);
        parcel.writeString(description);
        parcel.writeInt(v);
        parcel.writeString(id);
    }

}
