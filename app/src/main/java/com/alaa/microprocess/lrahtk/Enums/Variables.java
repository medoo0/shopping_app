package com.alaa.microprocess.lrahtk.Enums;

public enum  Variables {


    DATABASENAME("product"),VERSION("1"),TABLE_NAME("favourite_product"),

    // colums

    COL_ID("favourite_product_id"),COL_ProductName("COL_ProductName"),Col_Product_Salary("Col_Product_Salary"),Col_Product_Image("Col_Product_Image");




    private String val;

    private Variables(String val) {

        this.val = val;
    }

    public String getRef(){

        return this.val;

    }

    public int getINTref(){

        return Integer.parseInt(this.val);

    }
}
