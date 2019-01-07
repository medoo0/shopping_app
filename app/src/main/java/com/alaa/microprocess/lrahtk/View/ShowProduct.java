package com.alaa.microprocess.lrahtk.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.SpinnerAdapter;
import com.alaa.microprocess.lrahtk.R;
import com.bumptech.glide.Glide;


public class ShowProduct extends AppCompatActivity {
    ImageView image , back,fav;
    TextView name,price,ProductName ,Category , Description , txRate , txbrand , size;
    String proID , ProName ,ProDesc,ProBrand,ProCategory;
    int ProRate, ProLength ,ProPrice,proQuantity;
    RatingBar ratingBar;


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






        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
           ProName =  bundle.getString("name");
           ProductName.setText(ProName);
           name.setText(ProName);

           ProDesc = bundle.getString("description");
           Description.setText(ProDesc);

           ProPrice = bundle.getInt("price");
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

           if( bundle.getParcelable("Image") == null){
              Glide.with(this).load(bundle.getString("ImageURl")).into(image) ;
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
                    Toast.makeText(ShowProduct.this, Qualifi[position], Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }
}
