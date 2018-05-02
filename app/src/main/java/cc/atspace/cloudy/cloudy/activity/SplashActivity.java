package cc.atspace.cloudy.cloudy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cc.atspace.cloudy.cloudy.R;


public class SplashActivity extends AppCompatActivity {

    private ImageView gifView;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = getApplicationContext();
        String url = "https://firebasestorage.googleapis.com/v0/b/cloudy-e0811.appspot.com/o/add_images%2Fcloudy_banner_logo_04.gif?alt=media&token=af44c4a6-c4e2-48c7-ad34-a1dd2d830440";

        gifView = findViewById(R.id.logo_image_SA);
        Glide.with(context).load(url).into(gifView);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, NumberLogin_01.class));
                finish();
            }
        }, 3000);


    }
}
