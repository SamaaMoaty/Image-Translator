package com.example.globallens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class trans extends AppCompatActivity {
    /*Init of layout Components*/
    ImageView mImageView;
    Button extractText_btn;

   /*Init of needed Variables*/
    Uri myUri; //Image Uri that will be sent to OCR after getting it from activity_img_Translator.xml (Previous Activity)
    String Result; //OCR extracted text that will be sent to next activity
    final int PIC_CROP = 1;
    private static final String TAG = "OcrCaptureActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans);

        /*Get the image Uri that was sent by previous activity containing the image*/
        Bundle extras = getIntent().getExtras();
        myUri = Uri.parse(extras.getString("imageUri"));

        mImageView = findViewById(R.id.imageView3);

        /*View Image Received*/
        mImageView.setImageURI(myUri);
        performCrop(myUri); //Call Crop Function

        /*Extract button that starts the OCR function*/
        extractText_btn = findViewById(R.id.button5);
        extractText_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = OCR(myUri);//send image uri to OCR
                /*Creating new Activity and send to it extracted text --> Check activity_translatedimage.xml and translatedimage.java*/
                Intent i = new Intent(trans.this , translatedimage.class);
                i.putExtra("text" , text);
                startActivity(i);

            }
        });

    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties here
            cropIntent.putExtra("crop", true);
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Permission to Croop
        if (requestCode == PIC_CROP) {
            if (data != null) {
                myUri = data.getData();
                mImageView.setImageURI(myUri);
            }
        }
    }

    private String OCR(Uri myUri) {
        // Create Text Recogonizer
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        // Check if this Text recogonizer is operational
        if (!textRecognizer.isOperational()) {
            Log.w(TAG, "Detector dependencies are not yet available.");
        }
        else{
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myUri); //convert uri to bitmap
                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> items = textRecognizer.detect(frame);
                StringBuilder sb = new StringBuilder();
                /*Loop over Strings in image*/
                for (int i = 0; i < items.size(); i++) {
                    TextBlock myItem = items.valueAt(i);
                    sb.append(myItem.getValue());
                    sb.append("\n");
                    Result = sb.toString(); //extracted String
                }
                return sb.toString(); //return extracted text
            }catch (Exception e){
                System.out.println("error in conversion!!");
            }
        }
        return "Error";
    }
}

