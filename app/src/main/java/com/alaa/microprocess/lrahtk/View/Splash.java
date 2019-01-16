package com.alaa.microprocess.lrahtk.View;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.Fragment.Paying_Fragment;
import com.alaa.microprocess.lrahtk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash extends AppCompatActivity {

    @BindView(R.id.lrahtk_img)
    ImageView lrahtk_img;


    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.replacetest)
    RelativeLayout replacetest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
          // ButterKnife initial .
          ButterKnife.bind(this);

          progress();



        lrahtk_img.setScaleX(.5f);
        lrahtk_img.setScaleY(.5f);
        lrahtk_img.animate().alpha(1).scaleX(1f).scaleY(1f).setDuration(500);

          //animate
        lrahtk_img.animate().rotation(360).setDuration(1000);


    }

   public void progress (){
       progress.setAlpha(.5f);
       progress.setVisibility(View.VISIBLE);

       progress.animate().alpha(1).setDuration(3000).setListener(new Animator.AnimatorListener() {
           @Override
           public void onAnimationStart(Animator animator) {

           }

           @Override
           public void onAnimationEnd(Animator animator) {

               //goto MainActivity
               Intent intent = new Intent(Splash.this , MainActivity.class);
               startActivity(intent);
                finish();
           }

           @Override
           public void onAnimationCancel(Animator animator) {

           }

           @Override
           public void onAnimationRepeat(Animator animator) {

           }
       });
   }
}
