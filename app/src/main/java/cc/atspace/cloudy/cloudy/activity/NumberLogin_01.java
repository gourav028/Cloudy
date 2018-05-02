package cc.atspace.cloudy.cloudy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.utils.AppPreference;
import in.shadowfax.proswipebutton.ProSwipeButton;

public class NumberLogin_01 extends AppCompatActivity {

    ProSwipeButton nextBtn;
    Button verifyNumberBtn;
    EditText verifyNumberET, verifyOTPET;
    int btnTyp = 0;
    private String phoneNumber;

    //Firebase
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_login);

        verifyNumberBtn = (Button) findViewById(R.id.button_no_login);
        verifyNumberET = (EditText) findViewById(R.id.enterMobile_no_login);
        verifyOTPET = (EditText) findViewById(R.id.enterOTP_et_no_login);
        nextBtn = (ProSwipeButton) findViewById(R.id.next_button_no_login);

        //default layout
        verifyOTPET.setVisibility(View.INVISIBLE);
        verifyNumberBtn.setText("Send OTP");

        mAuth = FirebaseAuth.getInstance();


        if (AppPreference.getInstance(NumberLogin_01.this).isLoggedIn()) {
            startActivity(new Intent(NumberLogin_01.this, MainActivity.class));
            finish();
        }

        msgPermission();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {

                mVerificationId = verificationId;
                mResendToken = token;
                btnTyp = 1;
                verifyNumberET.setEnabled(false);
                verifyOTPET.setVisibility(View.VISIBLE);
                verifyNumberBtn.setText("Verify OTP");
                verifyNumberBtn.setVisibility(View.GONE);
                nextBtn.setVisibility(View.VISIBLE);


                YoYo.with(Techniques.Bounce)
                        .duration(1000)
                        .repeat(2)
                        .playOn(verifyOTPET);


            }

        };


        verifyNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnTyp == 0) {
                    phoneNumber = verifyNumberET.getText().toString();
                    if (phoneNumber.length() == 10) {
                        Log.d("onClick()", phoneNumber);

                        //            sendMsg(number);
                        getOTP(phoneNumber);
                    } else
                        Toast.makeText(getApplicationContext(), "Invalid number", Toast.LENGTH_LONG).show();
                } else {
                    String OTPNumber = verifyOTPET.getText().toString();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, OTPNumber);
                    signInWithPhoneAuthCredential(credential);

                }
            }
        });

        nextBtn.setOnSwipeListener(new ProSwipeButton.OnSwipeListener() {
            @Override
            public void onSwipeConfirm() {
                // user has swiped the btn. Perform your async operation now
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // task success! show TICK icon in ProSwipeButton
                        nextBtn.showResultIcon(true); // false if task failed
                        String OTPNumber = verifyOTPET.getText().toString();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, OTPNumber);
                        signInWithPhoneAuthCredential(credential);

                    }
                }, 2000);

            }
        });




    }

    private void getOTP(String phoneNumber) {
        Log.d("getOTP()", "01");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                NumberLogin_01.this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }


    private void sendMsg(String number) {

        try {

            SmsManager smsManager = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
                smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, "welcome to cloudy", null, null);
            }

            Intent intent = new Intent(getApplicationContext(), UserLogin_02.class);
            startActivity(intent);
            finish();


        } catch (Exception e) {
            Toast.makeText(NumberLogin_01.this, "Authentication Failed_01", Toast.LENGTH_LONG).show();
        }
    }

    private void msgPermission() {
        if (ContextCompat.checkSelfPermission(NumberLogin_01.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(NumberLogin_01.this, Manifest.permission.SEND_SMS)) {

                ActivityCompat.requestPermissions(NumberLogin_01.this, new String[]{Manifest.permission.SEND_SMS}, 1);

            } else {
                ActivityCompat.requestPermissions(NumberLogin_01.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        } else {
            // do nothing
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(NumberLogin_01.this,
                            Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission NOT granted!", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("success", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            AppPreference.getInstance(NumberLogin_01.this).setUserId(user.getUid().toString());

                            Intent userLoginIntent = new Intent(getApplicationContext(), UserLogin_02.class);
                            userLoginIntent.putExtra("phoneNumber", phoneNumber);
                            startActivity(userLoginIntent);
                            finish();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("not_success", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}

