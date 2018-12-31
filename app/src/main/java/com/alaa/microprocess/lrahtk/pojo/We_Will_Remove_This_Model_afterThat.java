package com.alaa.microprocess.lrahtk.pojo;

import android.content.Intent;
import android.graphics.Bitmap;

public class We_Will_Remove_This_Model_afterThat {


    String productName , productSalary ;

    Bitmap productImage;


    public We_Will_Remove_This_Model_afterThat(String productName, String productSalary, Bitmap productImage) {
        this.productName = productName;
        this.productSalary = productSalary;
        this.productImage = productImage;

    }


    public String getProductName() {
        return productName;
    }

    public String getProductSalary() {
        return productSalary;
    }

    public Bitmap getProductImage() {
        return productImage;
    }
}


