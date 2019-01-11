package com.alaa.microprocess.lrahtk.SQLite;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by microprocess on 2019-01-09.
 */

public class FavHelper extends SQLiteOpenHelper {
        public static String DataBase_Name = "Lrahtk";
        public static String FavID = "FavID";
        public static String BasketID = "BasketID";
        public static String BasketQuantity = "BasketQuantity";
        public static String ID = "ID";
        public static String Brand = "Brand";
        public static String Image_Url = "Image_Url";
        public static String BasketName = "BasketName";
        public static String prices = "prices";
        Context context ;
        public FavHelper(Context context) {
            super(context,DataBase_Name,null,3);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {



        }


        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
             SharedPreferences preferences = context.getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

                if (preferences.getString("AreInOrNot","").equals("IN")){


                        String UserID      = preferences.getString("id","");
                        String favTable = "T"+UserID;
                        String BasketTable = "B"+UserID;
                        sqLiteDatabase.execSQL("Drop Table "+favTable + "");
                        sqLiteDatabase.execSQL("Drop Table "+BasketTable + "");
                        onCreate(sqLiteDatabase);
                }
        }



        public void CreateFavTable(String TableName) { //create more than one table .
        final SQLiteDatabase db = getWritableDatabase();

        db.execSQL("create table if not exists " + TableName + " ( "
                + FavID + " TEXT "
                +" ) "
        );




    }
        public void CreateBasketTable(String TableName) { //create more than one table .
                final SQLiteDatabase db = getWritableDatabase();

                db.execSQL("create table if not exists " + TableName + " ( "
                        + ID + " INTEGER PRIMARY KEY autoincrement  ,"
                        + BasketName +" TEXT ,"
                        + BasketID + " TEXT ,"
                        + BasketQuantity + " TEXT ,"
                        + Brand + " TEXT ,"
                        + Image_Url + " TEXT ,"
                        + prices + " TEXT"
                        + " ) "
                );
        }

}
