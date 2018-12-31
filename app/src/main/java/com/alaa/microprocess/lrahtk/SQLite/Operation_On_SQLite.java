package com.alaa.microprocess.lrahtk.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alaa.microprocess.lrahtk.Enums.Variables;

public class Operation_On_SQLite {





    public  boolean addProduct(SQLiteDatabase dp, String Product_Id , String COL_ProductName , String Col_Product_Salary,byte [] image ){

        ContentValues row = new ContentValues();

        row.put(Variables.COL_ID.getRef(),Product_Id);
        row.put(Variables.COL_ProductName.getRef(),COL_ProductName);
        row.put(Variables.Col_Product_Salary.getRef(),Col_Product_Salary);
        row.put(Variables.Col_Product_Image.getRef(),image);

        long result = dp.insert(Variables.TABLE_NAME.getRef(),null,row);
        if(result!=-1)

            return true;

        else
            return false;


    }


    public boolean getData(SQLiteDatabase dp , String ProductID){


        Cursor c = dp.rawQuery("SELECT favourite_product_id FROM favourite_product WHERE favourite_product_id = '"+ProductID+"' ",null);

//       Cursor c=  dp.rawQuery("SELECT '"+Variables.COL_ID.getRef()+"' FROM '"+Variables.TABLE_NAME.getRef()+"' WHERE '"+Variables.COL_ID.getRef()+"'='"+ProductID+"'", null);
        if (c.getCount()>0){

            return true;
        }else
            return false;
    }




    public boolean deleterow(SQLiteDatabase dp , String ProductID){


        return dp.delete(Variables.TABLE_NAME.getRef(),"favourite_product_id=?",new String[]{ProductID})>0;

    }

    public Cursor getAllData(SQLiteDatabase dp ){


       return dp.rawQuery("select * from favourite_product",null);

    }


}
