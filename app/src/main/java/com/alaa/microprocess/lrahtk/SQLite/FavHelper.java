package com.alaa.microprocess.lrahtk.SQLite;

import android.content.Context;
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

        public FavHelper(Context context) {
            super(context,DataBase_Name,null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {



        }


        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
                        + BasketID + " TEXT ,"
                        + BasketQuantity + " TEXT "
                        + " ) "
                );
        }

}
