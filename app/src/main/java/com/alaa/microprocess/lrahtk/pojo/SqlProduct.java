package com.alaa.microprocess.lrahtk.pojo;

/**
 * Created by microprocess on 2019-01-11.
 */

public class SqlProduct
{
  private String  sqlID , basketName , basketID , basketQuantity ,brand , image_Url ;
  private double price ;

    public SqlProduct(String sqlID, String basketName, String basketID, String basketQuantity, String brand, String image_Url, double price) {
        this.sqlID = sqlID;
        this.basketName = basketName;
        this.basketID = basketID;
        this.basketQuantity = basketQuantity;
        this.brand = brand;
        this.image_Url = image_Url;
        this.price = price;
    }

    public String getSqlID() {
        return sqlID;
    }

    public void setSqlID(String sqlID) {
        this.sqlID = sqlID;
    }

    public String getBasketName() {
        return basketName;
    }

    public void setBasketName(String basketName) {
        this.basketName = basketName;
    }

    public String getBasketID() {
        return basketID;
    }

    public void setBasketID(String basketID) {
        this.basketID = basketID;
    }

    public String getBasketQuantity() {
        return basketQuantity;
    }

    public void setBasketQuantity(String basketQuantity) {
        this.basketQuantity = basketQuantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImage_Url() {
        return image_Url;
    }

    public void setImage_Url(String image_Url) {
        this.image_Url = image_Url;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
