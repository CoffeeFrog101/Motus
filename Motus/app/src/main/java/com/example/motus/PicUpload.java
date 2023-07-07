package com.example.motus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.UUID;

import kotlin.sequences.Sequence;

public class PicUpload extends AppCompatActivity {

    private ImageView imageView;
    private FirebaseAuth mAuth;
    private  StorageReference Chest;
    private FirebaseUser firebaseUser;
    private Uri Path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_upload);



        Button upload = findViewById(R.id.Uploader);
        Button Choose = findViewById(R.id.ChoosenPhoto);
        imageView = findViewById(R.id.pic);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        Chest = FirebaseStorage.getInstance().getReference("Images");

        Uri uri = firebaseUser.getPhotoUrl();

       // Picasso.with(PicUpload.this).load(uri).into(imageView);
        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Photo = new Intent(Intent.ACTION_PICK);
                Photo.setType("image/*");
                Photo.putExtra("pro", String.valueOf((imageView) ));
                startActivityForResult(Photo,1);


            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             submitPhoto();



            }
        });









    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 &&resultCode == RESULT_OK && data != null){

            Path = data.getData();
            UploadImageinView();
        }
    }
    private void UploadImageinView(){
        Bitmap bitmap = null;

        try {
            bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),Path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
    };
    private void submitPhoto(){
        FirebaseStorage.getInstance().getReference("images/*"+ UUID.randomUUID().toString()).putFile(Path).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(PicUpload.this, "PicUploaded succesfully.",
                        Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(getApplicationContext(),Edit_UserDetails.class);
                startActivity(intent);
                finish();

            }
        });

    }
}