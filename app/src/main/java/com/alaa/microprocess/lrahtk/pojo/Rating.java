package com.alaa.microprocess.lrahtk.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by microprocess on 2019-01-07.
 */

public class Rating implements Parcelable {
    @SerializedName("rate")
    @Expose
    private float rate;
    @SerializedName("length")
    @Expose
    private int length;

    protected Rating(Parcel in) {
        rate = in.readInt();
        length = in.readInt();
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(rate);
        parcel.writeInt(length);
    }
}
