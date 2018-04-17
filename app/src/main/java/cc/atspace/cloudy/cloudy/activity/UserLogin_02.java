package cc.atspace.cloudy.cloudy.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.utils.AppPreference;

public class UserLogin_02 extends AppCompatActivity {

    private Button nextBtn;
    private EditText nameET;
    private Context context;
    String nameStr, phoneNumberStr;
    private final String default_profil_pic_link = "https://firebasestorage.googleapis.com/v0/b/cloudy-e0811.appspot.com/o/profile_images%2Fprofile.png?alt=media&token=65c5801f-1dcd-49ea-b95b-e09f8a256986";


    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        context = this;


        phoneNumberStr = getIntent().getStringExtra("phoneNumber");

        mAuth = FirebaseAuth.getInstance();

        nameET = (EditText) findViewById(R.id.name_user_login);
        nextBtn = (Button) findViewById(R.id.next_button_user_login);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameStr = nameET.getText().toString();
                if(!nameStr.isEmpty())
                {
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = currentUser.getUid();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("phone", phoneNumberStr);
                    userMap.put("name", nameStr);
                    userMap.put("profile",default_profil_pic_link);

                    mDatabase.setValue(userMap);

                    AppPreference.getInstance(context).setName(nameStr);
                    AppPreference.getInstance(context).setPhone(phoneNumberStr);
                    Log.d("data", AppPreference.getInstance(context).getUserId()+", "+AppPreference.getInstance(context).getName()+", "+AppPreference.getInstance(context).getPhone());


                    startActivity(new Intent(UserLogin_02.this, MainActivity.class));
                    finish();


                }
            }
        });
    }


}


