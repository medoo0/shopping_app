package com.alaa.microprocess.lrahtk.View;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.SpinnerAdapter;
import com.alaa.microprocess.lrahtk.Dialog.AlertDialog;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.FavHelper;
import com.bumptech.glide.Glide;


public class ShowProduct extends AppCompatActivity {
    ImageView image , back,fav;
    TextView name,price,ProductName ,Category , Description , txRate , txbrand , size;
    String proID , ProName ,ProDesc,ProBrand,ProCategory , Quantity = "" , ImageURl;
    int ProRate, ProLength ,proQuantity  ;
    double ProPrice ;

    RatingBar ratingBar;
    FavHelper helper;
    SQLiteDatabase db ;
    SharedPreferences preferences ;
    String UserID , FavTableName , BasketTableName ;
    Button BtnAddToBasket ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        image       = findViewById(R.id.component_image);
        back        = findViewById(R.id.Back);
        ProductName = findViewById(R.id.ProductName);
        Description = findViewById(R.id.description);
        txRate = findViewById(R.id.rateNumber);
        Category    = findViewById(R.id.type);
        name = findViewById(R.id.name);
        price       = findViewById(R.id.price);
        fav = findViewById(R.id.fav);
        txbrand = findViewById(R.id.brand);
        size = findViewById(R.id.size);
        ratingBar = findViewById(R.id.rating);
        BtnAddToBasket = findViewById(R.id.BtnAddToBasket);




        this.helper   = new FavHelper(this);
        this.db       = helper.getWritableDatabase();
        preferences = getSharedPreferences("Sign_in_out", Context.MODE_PRIVATE);

        if (preferences.getString("AreInOrNot","").equals("IN")){


            UserID      = preferences.getString("id","");
            FavTableName = "T"+UserID;
            BasketTableName = "B"+UserID;
        }




        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
           ProName =  bundle.getString("name");
           ProductName.setText(ProName);
           name.setText(ProName);

           ProDesc = bundle.getString("description");
           Description.setText(ProDesc);

           ProPrice = bundle.getDouble("price");
           price.setText(ProPrice + " L.E");

           proQuantity = bundle.getInt("quantity");
           size.setText(proQuantity+"");

           ProRate = bundle.getInt("rating");
           txRate.setText(ProRate+"");
           ratingBar.setRating(Float.parseFloat(String.valueOf(ProRate)));

           ProLength =  bundle.getInt("length");
           ProBrand =  bundle.getString("brand");
           txbrand.setText(ProBrand);

           ProCategory = bundle.getString("category");
           Category.setText(ProCategory);

            ImageURl =  bundle.getString("ImageURl");
           if( bundle.getParcelable("Image") == null){
              Glide.with(this).load(ImageURl).into(image) ;
           }else {
               byte[] byteArray = getIntent().getByteArrayExtra("Image");
               Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
               image.setImageBitmap(bmp);
           }

           proID = bundle.getString("proID");

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //spinner .
        SpinnerChoose();




        if(Check_if_product_in_Fav_List(proID)){

            fav.setImageResource(R.drawable.ic_color_heart);
        }
        else {
            fav.setImageResource(R.drawable.heart);

        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.CreateFavTable(FavTableName);

                if(!Check_if_product_in_Fav_List(proID)) {
                    // insert
                    ContentValues row = new ContentValues();
                    row.put(helper.FavID,proID);
                    db.insert(FavTableName, null, row);
                    fav.setImageResource(R.drawable.ic_color_heart);
                }
                else {

                    int deleted = db.delete(FavTableName,FavHelper.FavID+" = ? ",new String[]{proID} );
                    if (deleted > 0 ){
                        //deleted
                        fav.setImageResource(R.drawable.heart);

                    }

                }
            }
        });

        BtnAddToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Quantity.isEmpty()){
                    Toast.makeText(ShowProduct.this, "يرجي اختيار العدد", Toast.LENGTH_SHORT).show();
                }
                else {

                    helper.CreateBasketTable(BasketTableName);
                    ContentValues row = new ContentValues();
                    row.put(FavHelper.BasketName ,ProName );
                    row.put(FavHelper.BasketID , proID);
                    row.put(FavHelper.BasketQuantity , Quantity);
                    row.put(FavHelper.Brand , ProBrand );
                    row.put(FavHelper.Image_Url , ImageURl );
                    row.put(FavHelper.prices , ProPrice);


                    long insert = db.insert(BasketTableName , null , row);
                    if(insert > 0 ){
                        AlertDialog alertDialog = new AlertDialog(ShowProduct.this,"تم اضافة المنتج داخل سلة المشتريات . ");
                        alertDialog.show();
                    }
                    //refresh basket fragment if opened  .
                    Intent intent = new Intent("Refresh");
                    sendBroadcast(intent);

                }
            }
        });

    }

    private void SpinnerChoose(){



        Spinner spinner2 = findViewById(R.id.type_spinner2);
        final String [] Qualifi = new String[]{"العدد","1","2","3","4","5","6","7","8","9","10"};
        SpinnerAdapter  spinnerAdapter = new SpinnerAdapter(this,Qualifi);
        spinner2.setAdapter(spinnerAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position>0) {

                    Quantity = Qualifi[position] ;

                }else if (position == 0 ){
                    Quantity = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public boolean Check_if_product_in_Fav_List(String ProductID){
        //create if not exists .
        helper.CreateFavTable(FavTableName);

        String [] Cols = {FavHelper.FavID};
        Cursor Pointer = db.query(FavTableName,Cols,Cols[0]+" = ?",new String[]{ProductID},null,null,null);
        if(Pointer.moveToNext()){
            return true ;
        }
        else {

            return false ;

        }

    }
}
