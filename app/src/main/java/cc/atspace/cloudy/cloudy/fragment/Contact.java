package cc.atspace.cloudy.cloudy.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.activity.Conversation;
import cc.atspace.cloudy.cloudy.bean.users;

@SuppressLint("ValidFragment")
public class Contact extends Fragment {

    private RecyclerView mUserList;
    private View rootView;
    private DatabaseReference mUserDatabaseReference;
    public Context context;
    private String userIdList;


    public Contact(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_contact, container, false);

        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        mUserList = (RecyclerView) rootView.findViewById(R.id.all_contacts_rv);
//        mUserList.setHasFixedSize(true);
        mUserList.setLayoutManager(new LinearLayoutManager(context));
        Log.d("oncreateview", "4");

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<users, UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<users, UserViewHolder>(
                users.class,
                R.layout.single_line_all_contacts,
                UserViewHolder.class,
                mUserDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(final UserViewHolder userViewHolder, final users allUser, int position) {
                userIdList = getRef(position).getKey();

                mUserDatabaseReference.child(userIdList).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("name").getValue().toString();
                        final String userId = dataSnapshot.getKey().toString();
                        String profileLink = dataSnapshot.child("profile").getValue().toString();


                        Log.d("key", userId);
                        userViewHolder.setName(allUser.getEmail());
                        userViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent conv = new Intent(context, Conversation.class);
                                conv.putExtra("userId", userId);
                                conv.putExtra("userName", userName);
                                Log.d("key_sent", userId);
                                startActivity(conv);
                            }
                        });

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
        TextView userNameTextView;

        public UserViewHolder(final View itemView) {
            super(itemView);

            mView = itemView;
            Log.d("userViewholder", "2");
            userNameTextView = (TextView) mView.findViewById(R.id.single_contact_name_tv);
/*
            userNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent conv = new Intent(itemView.getContext(), Conversation.class);
                    conv.putExtra("userId",userIdList)
                    itemView.getContext().startActivity(conv);

                }
            });
*/

        }


        public void setName(String name) {
//            userNameTextView = (TextView) mView.findViewById(R.id.single_contact_name_tv);
            userNameTextView.setText(name);
            Log.d("setName", name);

        }

        @Override
        public void onClick(View view) {


        }
    }
}
