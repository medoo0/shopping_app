package com.alaa.microprocess.lrahtk.View;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.alaa.microprocess.lrahtk.R;

public class ShowProduct extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        image = findViewById(R.id.component_image);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
        {
            image.setImageResource(bundle.getInt("image"));

        }
    }
}
