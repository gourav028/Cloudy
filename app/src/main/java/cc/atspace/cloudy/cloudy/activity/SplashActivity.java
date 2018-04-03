package cc.atspace.cloudy.cloudy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;

import cc.atspace.cloudy.cloudy.activity.Login;
import cc.atspace.cloudy.cloudy.activity.NumberLogin;


public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, NumberLogin.class));
                finish();
            }
        }, 4000);


    }
}
