package com.alaa.microprocess.lrahtk.View;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alaa.microprocess.lrahtk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash extends AppCompatActivity {

    @BindView(R.id.lrahtk_img)
    ImageView lrahtk_img;
    
    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
          // ButterKnife initial .
          ButterKnife.bind(this);


          //animate
           lrahtk_img.setX( -300 );
           lrahtk_img.animate().translationXBy(300).setDuration(500).setListener(new Animator.AnimatorListener() {
               @Override
               public void onAnimationStart(Animator animator) {
                  
               }

               @Override
               public void onAnimationEnd(Animator animator) {
                   
                   progress();
               
               }

               @Override
               public void onAnimationCancel(Animator animator) {

               }

               @Override
               public void onAnimationRepeat(Animator animator) {

               }
           });



    }
    
   public void progress (){
       progress.setAlpha(.5f);
       progress.setVisibility(View.VISIBLE);

       progress.animate().alpha(1).setDuration(2000).setListener(new Animator.AnimatorListener() {
           @Override
           public void onAnimationStart(Animator animator) {

           }

           @Override
           public void onAnimationEnd(Animator animator) {
                
               //goto MainActivity
               Intent intent = new Intent(Splash.this , MainActivity.class);
               startActivity(intent);
                
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
