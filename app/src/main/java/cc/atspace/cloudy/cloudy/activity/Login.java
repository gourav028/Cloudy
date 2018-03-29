package cc.atspace.cloudy.cloudy.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.utils.AppPreference;

public class Login extends AppCompatActivity {

    private EditText email, pass;
    private Button login, reg;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        if (AppPreference.getInstance(context).getUser() != null) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }

        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);

        login = (Button) findViewById(R.id.loginBtn);
        reg = (Button) findViewById(R.id.regBtn);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAdd = email.getText().toString();
                String passVal = email.getText().toString();

                regUser(emailAdd, passVal);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAdd = email.getText().toString();
                String passVal = email.getText().toString();

                loginUser(emailAdd, passVal);
            }
        });
    }

    private void loginUser(final String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AppPreference.getInstance(context).setUser(email);

                            // Sign in success, update UI with the signed-in user's information
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplication(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    private void regUser(final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = currentUser.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("email", email);

                            mDatabase.setValue(userMap);

                            AppPreference.getInstance(context).setUser(email);

                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplication(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}
