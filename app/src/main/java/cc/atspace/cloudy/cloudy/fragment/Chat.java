package cc.atspace.cloudy.cloudy.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cc.atspace.cloudy.cloudy.activity.Conversation;
import cc.atspace.cloudy.cloudy.activity.Profile;
import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.bean.chat;
import cc.atspace.cloudy.cloudy.bean.users;

@SuppressLint("ValidFragment")
public class Chat extends Fragment {

    private View rootView;
    private Button profile;
    private Context context;
    private String userIdList;
    private RecyclerView mUserList;

//firebase
    private DatabaseReference mUserDatabaseReference,mChatDatabaseReference;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;



    public Chat(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        mAuth = FirebaseAuth.getInstance();

        mCurrentUserId = mAuth.getCurrentUser().getUid();

        mChatDatabaseReference = FirebaseDatabase.getInstance().getReference().child("chat").child(mCurrentUserId);
//        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(mCurrentUserId);

        mUserList = (RecyclerView) rootView.findViewById(R.id.chat_rv);
//        mUserList.setHasFixedSize(true);
        mUserList.setLayoutManager(new LinearLayoutManager(context));
        Log.d("oncreateview", "4");
        return rootView;
    }

/*

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<chat, Chat.UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<chat, Chat.UserViewHolder>(
                chat.class,
                R.layout.single_line_all_contacts,
                Chat.UserViewHolder.class,
                mUserDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(final Chat.UserViewHolder userViewHolder, final chat allUser, int position) {
                userIdList = getRef(position).getKey();

                mUserDatabaseReference.child(userIdList).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        final String userId = dataSnapshot.getKey().toString();
//                        final String userName = dataSnapshot.getValue().toString();

//                        final String userPhone = dataSnapshot.child("phone").getValue().toString();
//                        String profileLink = dataSnapshot.child("profile").getValue().toString();

                        String uid = FirebaseDatabase.getInstance().getReference().child("chat").child(mCurrentUserId).getKey();


                        Log.d("uid", uid);
  //                      userViewHolder.setName(allUser.getName());
//                        userViewHolder.setPhone(allUser.getPhone());
                        */
/*userViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent conv = new Intent(context, Conversation.class);
                                conv.putExtra("userId", userId);
                                conv.putExtra("userName", userName);
//                                conv.putExtra("userPhone", userPhone);
                                Log.d("key_sent", userId);
                                startActivity(conv);
                            }
                        });
*//*

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        };
        Log.d("onStart", "1");
        mUserList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View mView;
        TextView userNameTextView,userPhoneTextView;
        ImageView userProfileImageView;

        public UserViewHolder(final View itemView) {
            super(itemView);

            mView = itemView;
            Log.d("userViewholder", "2");
            userNameTextView = (TextView) mView.findViewById(R.id.single_contact_name_tv);
            userPhoneTextView = (TextView) mView.findViewById(R.id.single_contact_phone_tv);
            userProfileImageView = (ImageView) mView.findViewById(R.id.contact_profile_pic_iv);
*/
/*
            userNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent conv = new Intent(itemView.getContext(), Conversation.class);
                    conv.putExtra("userId",userIdList)
                    itemView.getContext().startActivity(conv);

                }
            });
*//*


        }


        public void setName(String name) {
//            userNameTextView = (TextView) mView.findViewById(R.id.single_contact_name_tv);
            userNameTextView.setText(name);
            Log.d("setName", name);

        }

        public void setProfile(String name) {
            //   userProfileImageView.setImageURI();
            Log.d("setName", name);

        }


        public void setPhone(String phone) {
            Log.d("setPhone", phone);
            userPhoneTextView.setText(phone);

        }


        @Override
        public void onClick(View view) {


        }
    }

*/
}
