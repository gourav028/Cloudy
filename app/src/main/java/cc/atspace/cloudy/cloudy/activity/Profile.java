package cc.atspace.cloudy.cloudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import cc.atspace.cloudy.cloudy.R;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //transparent status and navigation bar
        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


    }
}
