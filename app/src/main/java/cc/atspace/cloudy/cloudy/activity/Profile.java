package cc.atspace.cloudy.cloudy.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import java.io.File;
import java.util.Random;

import cc.atspace.cloudy.cloudy.R;
import cc.atspace.cloudy.cloudy.utils.AppPreference;

public class Profile extends AppCompatActivity {

    private Button changeProfilePicButton;
    private static final int GALLERY_PICK = 1;
    private ImageView profilePicImageView;
    private StorageReference mImageStorage;
    private FirebaseUser mCurrrentUser;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //transparent status and navigation bar
        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        //Firebase storage
        mImageStorage = FirebaseStorage.getInstance().getReference();

        //Firebase User
        mCurrrentUser = FirebaseAuth.getInstance().getCurrentUser();

        //Firebase Database Reference
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(mCurrrentUser.getUid());

        changeProfilePicButton = (Button) findViewById(R.id.change_profile_pic);
        profilePicImageView = (ImageView) findViewById(R.id.profile_pic_iv_profile);


        //Data change listener

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("email").getValue().toString();
                String profilePic = dataSnapshot.child("profile").getValue().toString();

                Picasso.with(Profile.this).load(profilePic).into(profilePicImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        changeProfilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

 /*               Intent galleryIntent = new Intent();
                galleryIntent.setType("image*//*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent,"Select Image"), GALLERY_PICK);
*/
                Crop.pickImage(Profile.this);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //Progress Dialog
            mProgressDialog = new ProgressDialog(Profile.this);
            mProgressDialog.setTitle("Uploading Image...");
            mProgressDialog.setMessage("Please wait a while");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            if (requestCode == Crop.REQUEST_PICK) {
                Uri sourceUri = data.getData();
                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped"));

                Crop.of(sourceUri, destinationUri).asSquare().start(Profile.this);
                Uri resultUri = data.getData();
                profilePicImageView.setImageURI(resultUri);
                profilePicUpload(resultUri);
            } else if (requestCode == Crop.REQUEST_CROP) {
                if (resultCode == RESULT_OK) {
                    Uri resultUri = Crop.getOutput(data);
                    profilePicImageView.setImageURI(resultUri);
                    profilePicUpload(resultUri);
                } else if (resultCode == Crop.RESULT_ERROR) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            }
        }
    }

    public static String random() {
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

    public void profilePicUpload(Uri resultUri) {
        StorageReference filepath = mImageStorage.child("profile_images").child("pro.jpg");
        filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    String downloadLink = task.getResult().getDownloadUrl().toString();
                    //Firebase database reference
                    mDatabase.child("profile").setValue(downloadLink).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Success Uploading", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    public void setmProgressDialog() {

    }
}
