package cc.atspace.cloudy.cloudy.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.activity.Profile;
import cc.atspace.cloudy.cloudy.adapter.ConversationAdapter;
import cc.atspace.cloudy.cloudy.adapter.VerticalStoriesOuterAdapter;
import cc.atspace.cloudy.cloudy.bean.story;
import cc.atspace.cloudy.cloudy.bean.users;
import cc.atspace.cloudy.cloudy.utils.AppPreference;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
public class Story extends Fragment {
    private View rootView;
    private Context context;
    private RecyclerView mStoryList;
    private VerticalStoriesOuterAdapter verticalStoriesOuterAdapter;
    private CardView yourStoryCV;
    private ImageView yourStroyIV;

    private StorageReference mImageStorage;
    private FirebaseUser mCurrrentUser;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgressDialog;
    private String storyListPos;

    private final List<story> storyList = new ArrayList<story>();
    private LinearLayoutManager linearLayoutManager;
    private VerticalStoriesOuterAdapter mAdapter;


    public Story(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_story, container, false);

        yourStoryCV = (CardView) rootView.findViewById(R.id.your_story_card_view);
        mStoryList = (RecyclerView) rootView.findViewById(R.id.recent_vertical_rv_outer);
        yourStroyIV = (ImageView) rootView.findViewById(R.id.your_story_image_view);

/*
        verticalStoriesOuterAdapter = new VerticalStoriesOuterAdapter(context);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(verticalStoriesOuterAdapter);
        verticalStoriesOuterAdapter.notifyDataSetChanged();
*/

        //Firebase storage
        mImageStorage = FirebaseStorage.getInstance().getReference();

        //Firebase User
        mCurrrentUser = FirebaseAuth.getInstance().getCurrentUser();

        //Firebase Database Reference
        if (mCurrrentUser != null)
            mDatabase = FirebaseDatabase.getInstance().getReference().child("story");

        Log.d("Story09", AppPreference.getInstance(context).getCurrentTask().toString());

        if (AppPreference.getInstance(context).getCurrentTask().toString().equalsIgnoreCase("story")) {
            AppPreference.getInstance(context).setCurrentTask("default");
            Log.d("Story01", "onAssist");
            CropImage.activity().start(context, Story.this);

        }

        mAdapter = new VerticalStoriesOuterAdapter(storyList,context);

//        mStoryList = (RecyclerView) rootView.findViewById(R.id.all_contacts_rv);
//        mUserList.setHasFixedSize(true);
        mStoryList.setLayoutManager(new LinearLayoutManager(context));
        mStoryList.setAdapter(mAdapter);


        yourStoryCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Story01", "onClick");

                CropImage.activity().start(context, Story.this);
//            Crop.pickImage((Activity) context);
            }
        });

        Log.d("Story08", "return");
        loadStory();
        return rootView;
    }

    private void loadStory() {
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                story story = dataSnapshot.getValue(story.class);
                storyList.add(story);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                yourStroyIV.setImageURI(resultUri);
                storyPicUpload(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public static String random() {
        Log.d("Story05", "random");

        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public void storyPicUpload(Uri resultUri) {
        Log.d("Story06", "storyPicUpload");

        StorageReference filepath = mImageStorage.child("story_images").child(mCurrrentUser.getUid().toString()).child(random() + ".jpg");
        filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("Story07", "onComplete");
                    //Firebase database reference
                    String downloadLink = task.getResult().getDownloadUrl().toString();
           String pushID =          mDatabase.child("story").child(mCurrrentUser.getUid().toString()).push().getKey();
                    mDatabase.child("story").child(mCurrrentUser.getUid().toString()).child("story_link").setValue(downloadLink).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                mProgressDialog.dismiss();
                                Log.d("Story08", "onSuccess");

                                Toast.makeText(context, "Success Uploading", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
//                    mProgressDialog.dismiss();
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();

                }
            }
        });


    }

}
