package cc.atspace.cloudy.cloudy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.adapter.ConversationAdapter;
import cc.atspace.cloudy.cloudy.bean.chat;

public class Conversation extends AppCompatActivity {

    private TextView userName_tv;
    private String mChatUserId, mChatUserName, mCurrentUserId;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth mAuth;
    private EditText msgConvEditText;
    private Button msgSentButton;
    private RecyclerView messagesListRV;

    private final List<chat> chatList = new ArrayList<chat>();
    private LinearLayoutManager linearLayoutManager;
    private ConversationAdapter mAdapter;

    //private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        userName_tv = (TextView) findViewById(R.id.username_conv_tv);
        mChatUserId = getIntent().getStringExtra("userId");
        mChatUserName = getIntent().getStringExtra("userName");
        userName_tv.setText(mChatUserName + " " + mChatUserId);
        msgConvEditText = (EditText) findViewById(R.id.message_conv_et);
        msgSentButton = (Button) findViewById(R.id.send_conv_btn);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //constructor
        mAdapter = new ConversationAdapter(chatList);

        messagesListRV = (RecyclerView) findViewById(R.id.message_list_rv_conv);
        //layout
        linearLayoutManager = new LinearLayoutManager(this);

//        messagesListRV.setHasFixedSize(true);
        messagesListRV.setLayoutManager(linearLayoutManager);
        messagesListRV.setAdapter(mAdapter);

        //method


        //Firebase Instance
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mCurrentUserId = mAuth.getCurrentUser().getUid();

        loadMessages();
        //update values to chat object in firebase
  /*      mDatabaseRef.child("chat").child(mCurrentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(mChatUserId)) {

                    Map chatUserMap = new HashMap();
                    chatUserMap.put("chat/" + mCurrentUserId + "/" + mChatUserId, "hi");
                    chatUserMap.put("chat/" + mChatUserId + "/" + mCurrentUserId, "hello");

                    mDatabaseRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null)
                                Log.d("chat_error", databaseError.getMessage().toString());

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        msgSentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

    }

    private void loadMessages() {

        mDatabaseRef.child("chat").child(mCurrentUserId).child(mChatUserId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                chat chat = dataSnapshot.getValue(chat.class);
                chatList.add(chat);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage() {
        String message = msgConvEditText.getText().toString();
        if (!TextUtils.isEmpty(message)) {
            DatabaseReference userMessagePush = mDatabaseRef.child("chat").child(mCurrentUserId).child(mChatUserId).push();
            String pushId = userMessagePush.getKey();

            Map messageMap = new HashMap();
            messageMap.put("chat/" + mCurrentUserId + "/" + mChatUserId + "/" + pushId+"/message", message);
            messageMap.put("chat/" + mChatUserId + "/" + mCurrentUserId + "/" + pushId+"/message", message);

            mDatabaseRef.updateChildren(messageMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null)
                        Log.d("chat_error", databaseError.getMessage().toString());
                    msgConvEditText.setText(null);
                }


            });

        }
    }
}
