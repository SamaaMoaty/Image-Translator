package com.example.globallens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //On pressing "Image Translator" button , we move on to the next activity --> Go check "activity_img_translator.xml" and "imgTranslator.java"
    public void imgTrans(View view) {
        Intent intent = new Intent(this, imgTranslator.class);
        startActivity(intent);
    }

    //On pressing "Text Translator" button , we move on to the next activity --> Go check "activity_translatetext.xml" and "translatetext.java"
    public void txtTrans(View view) {
        Intent intent = new Intent(this, translatetext.class);
        startActivity(intent);
    }
}