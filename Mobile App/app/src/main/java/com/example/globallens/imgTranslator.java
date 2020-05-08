package com.example.globallens;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class imgTranslator extends AppCompatActivity {

   /*Capture Image Components and Permission Init*/
    Button mCaptureBtn;
    Uri image_uri;
    private static final int PERMISSION_CODE1 = 1000;
    private static final int CAMERA_REQUEST_CODE = 102;


    /*select existing image Components and Permission Init*/
    Button btnPick;
    Uri imageData;
    private static final int PERMISSION_CODE = 1001;
    private static final int GALLERY_REQUEST_CODE = 123;


    /*Create activity_img_translator_layout */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_translator);

        /*Capture Image Button Function*/
        mCaptureBtn = findViewById(R.id.button3);
        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check on Permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ){
                        //request Permission
                        String[] Permission = {Manifest.permission.CAMERA , Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to request permission
                        requestPermissions(Permission, PERMISSION_CODE1);
                    }
                    else{
                        //permission granted
                        openCamera();
                    }
                }
                else{
                        //Grant Permission Directly
                        openCamera();
                }
            }
        });

        /*Select Existing Image button Function*/
        btnPick = findViewById(R.id.button4);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check on Permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        //request Permission
                        String[] Permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup to request permission
                        requestPermissions(Permission, PERMISSION_CODE);
                    }
                    else{
                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                        intent1.setType("image/*");
                        startActivityForResult(intent1, GALLERY_REQUEST_CODE);
                    }
                }
                else{
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setType("image/*");
                    //intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent1, GALLERY_REQUEST_CODE);
                }

            }
        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE , "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION , "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT , image_uri);
        startActivityForResult(cameraIntent , CAMERA_REQUEST_CODE );
    }

    //Handling Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE1:{ //Camera Permission
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else{
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
            case PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent intent1 = new Intent(Intent.ACTION_PICK);
                    intent1.setType("image/*");
                    startActivityForResult(intent1, GALLERY_REQUEST_CODE);
                }
                else{
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

       if(requestCode == GALLERY_REQUEST_CODE){
           if (resultCode == RESULT_OK && data != null) {

               imageData =  data.getData();

               /*Send Image Uri to the next activity --> Check trans.java and activity_trans.xml*/
               Intent ii = new Intent(imgTranslator.this , trans.class);
               ii.putExtra("imageUri" , imageData.toString());
               startActivity(ii);
           }
       }
       else if (requestCode == CAMERA_REQUEST_CODE){
           if (resultCode == RESULT_OK){

               /*Send Image Uri to the next activity --> Check trans.java and activity_trans.xml*/
               Intent i = new Intent(imgTranslator.this , trans.class);
               i.putExtra("imageUri" , image_uri.toString());
               startActivity(i);

           }
       }
    }


}






