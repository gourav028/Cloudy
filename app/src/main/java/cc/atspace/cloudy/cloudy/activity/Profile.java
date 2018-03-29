package cc.atspace.cloudy.cloudy.activity;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;

import java.io.File;

import cc.atspace.cloudy.cloudy.R;

public class Profile extends AppCompatActivity {

    private Button changeProfilePicButton;
    private static final int GALLERY_PICK = 1;
    private ImageView profilePicImageView;
    private StorageReference mImageStorage;

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

        changeProfilePicButton = (Button) findViewById(R.id.change_profile_pic);
        profilePicImageView = (ImageView) findViewById(R.id.profile_pic_iv_profile);

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
            if (requestCode == Crop.REQUEST_PICK) {
                Uri sourceUri = data.getData();
                Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped"));

                 Crop.of(sourceUri, destinationUri).asSquare().start(Profile.this);
                Uri resultUri = data.getData();
//                profilePicImageView.setImageURI(resultUri);

                StorageReference filepath = mImageStorage.child("profile_images").child("pro.jpg");
                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Success1", Toast.LENGTH_LONG).show();

                        }
                    }
                });

            } else if (requestCode == Crop.REQUEST_CROP) {
                if (resultCode == RESULT_OK) {
                    Uri resultUri = Crop.getOutput(data);
                    profilePicImageView.setImageURI(resultUri);
                    StorageReference filepath = mImageStorage.child("profile_images").child("pro.jpg");
                    filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Success2", Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                } else if (resultCode == Crop.RESULT_ERROR) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
