package com.alaa.microprocess.lrahtk.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alaa.microprocess.lrahtk.Enums.Variables;

public class Helper extends SQLiteOpenHelper {



    private static final String DATABASENAME = "product";
    private static final int VERSION = 4;



    public Helper(Context context) {

        super(context, DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String Table_Create

                = "CREATE TABLE "+ Variables.TABLE_NAME.getRef()+
                " ("+Variables.COL_ID.getRef() +" integer primary key autoincrement,"+
                Variables.Col_Product_Salary.getRef()+" Text,"+
                Variables.COL_ProductName.getRef()+" Text,"+
                Variables.Col_Product_Image+" Blob )";

        db.execSQL(Table_Create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS favourite_product");
        onCreate(db);
    }
}
