package cc.atspace.cloudy.cloudy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cc.atspace.cloudy.cloudy.R;

public class UserLogin extends AppCompatActivity {

    public Button button;


    public void button() {
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLogin.this, Login.class));
            }
        });



    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        button();
    }

}


