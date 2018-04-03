package cc.atspace.cloudy.cloudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cc.atspace.cloudy.cloudy.R;

public class Conversation extends AppCompatActivity {

    private TextView userName_tv;
    private String mChatUserId,mChatUserName;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        userName_tv = (TextView) findViewById(R.id.username_conv_tv);
        mChatUserId = getIntent().getStringExtra("userId");
        mChatUserName = getIntent().getStringExtra("userName");
        userName_tv.setText(mChatUserName);

    }
}
