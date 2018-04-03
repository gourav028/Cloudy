package cc.atspace.cloudy.cloudy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.utils.AppPreference;

public class NumberLogin extends AppCompatActivity {

    Button button;

    EditText editText, editText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_login);

        if (ContextCompat.checkSelfPermission(NumberLogin.this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(NumberLogin.this,
                    Manifest.permission.SEND_SMS)
                    ) {
                ActivityCompat.requestPermissions(NumberLogin.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);


            } else {
                ActivityCompat.requestPermissions(NumberLogin.this,
                        new String[]{Manifest.permission.SEND_SMS}, 1);


            }
        } else {
            // do nothing
        }
        button = (Button) findViewById(R.id.button);

        //  editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = editText2.getText().toString();
                //   String sms = editText2.getText().toString();

                if (number.length() == 10) {

                    try {

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(number, null, "welcome to cloudy", null, null);
                        Toast.makeText(NumberLogin.this, "welcome to cloudy family", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), UserLogin.class);
                            startActivity(intent);
                            finish();


                    } catch (Exception e) {
                        Toast.makeText(NumberLogin.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "invalid number", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(NumberLogin.this,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}

