package com.alaa.microprocess.lrahtk.View;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Adapters.SpinnerAdapter;
import com.alaa.microprocess.lrahtk.R;
import com.alaa.microprocess.lrahtk.SQLite.Helper;
import com.alaa.microprocess.lrahtk.SQLite.Operation_On_SQLite;

public class ShowProduct extends AppCompatActivity {
    ImageView image , back,fav;
    TextView name,price,ProductName;
    String Quantity;
    SQLiteDatabase  dpread;
    Helper helper;
    Operation_On_SQLite operation_on_sqLite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        helper = new Helper(this);
        operation_on_sqLite = new Operation_On_SQLite();
        dpread              = helper.getReadableDatabase();
        image       = findViewById(R.id.component_image);
        back        = findViewById(R.id.Back);
        ProductName = findViewById(R.id.ProductName);
        name = findViewById(R.id.name);
        price       = findViewById(R.id.price);
        fav = findViewById(R.id.fav);







        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            image.setImageResource(bundle.getInt("image"));
            name.setText(bundle.getString("name"));
            ProductName.setText(bundle.getString("name"));


            // we will checking if product in my Favourite or Not by ID
            if (operation_on_sqLite.getData(dpread,bundle.getString("id"))){


                fav.setImageDrawable(getDrawable(R.drawable.ic_color_heart));

            }
            else {


                fav.setImageDrawable(getDrawable(R.drawable.heart));

            }



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






//
//        Spinner spinner2 = findViewById(R.id.type_spinner2);
//        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>
//                (this, android.R.layout.simple_spinner_item,
//                        Qualifi){
//            @Override
//            public boolean isEnabled(int position){
//                if(position == 0)
//                {
//                    // Disable the first item from Spinner
//                    // First item will be use for hint
//                    return false;
//                }
//                else
//                {
//                    return true;
//                }
//            }
//            @SuppressLint("ResourceAsColor")
//            @Override
//            public View getDropDownView(int position, View convertView,
//                                        ViewGroup parent) {
//                View view = super.getDropDownView(position, convertView, parent);
//                TextView tv = (TextView) view;
//                if(position == 0){
//                    // Set the hint text color gray
//                    tv.setTextColor(Color.BLACK);
//                    tv.setTextSize(24f);
//
//                }
//                else {
//                    tv.setTextColor(Color.BLACK);
//                }
//                return view;
//            }
//        };
        //selected item will look like a spinner set from XML





//        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout
//                .simple_spinner_dropdown_item);
//        spinner2.setAdapter(spinnerArrayAdapter2);
//        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                if (i > 0) {
//                    Quantity = Qualifi[i];
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }
}
