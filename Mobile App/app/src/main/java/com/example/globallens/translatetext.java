package com.example.globallens;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;
import com.ibm.cloud.sdk.core.service.exception.RequestTooLargeException;
import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import com.ibm.watson.discovery.v1.Discovery;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;
import com.ibm.watson.language_translator.v3.util.Language;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class translatetext extends AppCompatActivity {

    /*Defining the activity Components*/
    Button TransBtn;
    Spinner lang1;
    Spinner lang2;
    public static EditText text; //Input text
    public static EditText translatedText; //Translated Text

    /*Public Volatile Strings to be used in the other Thread that calls IBM cloud */
    public volatile static String Original;
    public volatile static String src_lang ;
    public volatile static String targrt_lang ;

    /*Languages Supported by IBM cloud Service for Translation and their abbreviations*/
    String[] items = new String[]{"Arabic", "Bengali", "Bulgarian" ,  "Chinese (Traditional)" , "Croatian" ,
            "Czech" , "Danish" ,"Dutch" , "English" , "Estonian" , "Finnish" , "French" , "German" ,"Greek" , "Gujarati" , "Hebrew" , "Hindi" ,
            "Hungarian" , "Irish" , "Indonesian" , "Italian" , "Japanese" , "Korean" , "Latvian", "Lithuanian" ,"Malay" , "Malayalam" ,
            "Maltese", "Norwegian Bokmål" , "Polish" , "Portuguese" , "Romanian" , "Russian" , "Slovak" , "Slovenian" , "Spanish" ,
            "Swedish" , "Tamil" , "Telugu" , "Thai" ,"Turkish" , "Urdu" , "Vietnamese"};

    final String[] abbr = new String[]{"ar", "bn", "bg" , "zh-TW" , "hr" , "cs" , "da" ,"nl" , "en" , "et" , "fi" , "fr" , "de" ,"el" , "gu" ,
            "he" , "hi" , "hu" , "ga" , "id" , "it" , "ja" , "ko" , "lv" , "li" ,"ms" , "ml" , "mt", "nb" , "pl" , "pt" , "ro" , "ru" , "sk" ,
            "sl" , "es" , "sv" , "ta" , "te" , "th" ,"tr" , "ur" , "vi"};



    /*On create Function to start the layout of the activity */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translatetext);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        Toast.makeText(this,"Translation Process may take few seconds. \nPlease wait after pressing the translate button..." , Toast.LENGTH_LONG).show();

        lang1 = findViewById(R.id.spinner);
        lang2 = findViewById(R.id.spinner2);
        lang1.setAdapter(adapter);
        lang2.setAdapter(adapter);

        /*Getting the Source language abbreviation From the selected item from dropdown menu */
        lang1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               src_lang = abbr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                src_lang = "ar"; //by default arabic

            }
        });

        /*Getting the Target language abbreviation From the selected item from dropdown menu */
        lang2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targrt_lang = abbr[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                targrt_lang ="ar"; //by default arabic
            }
        });

        /*assigning Variables to Components of Layout*/
        text=findViewById(R.id.editText);
        translatedText=findViewById(R.id.editText2);

        //enable scrolling
        text.setMovementMethod(new ScrollingMovementMethod());
        translatedText.setMovementMethod(new ScrollingMovementMethod());

        /*Translate Button Function*/
        TransBtn = findViewById(R.id.button6);
        TransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Original = text.getText().toString();
                if(Original.equals("")){
                    text.setText("Please insert Text First...");
                }
                else{
                    IBM_Translator();
                }
            }
        });

    }

    /*Beginning of Translating Function*/
    public void IBM_Translator() {
        /*Creating a new Thread for the service to run on*/
        Thread thread = new Thread();
        thread.start();
        /*Synchronization for the safety of the sgared Variables*/
        synchronized (thread) {
            try {
                thread.wait(); //wait until thread finishes
                String TranslatedText = thread.IBM;
                translatedText.setText(TranslatedText); //Assigning the Translation to the Translated Text Textbox
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 }

 /*Starting the Class of the new thread */
 class Thread extends java.lang.Thread{
    String IBM ;
     @Override
     public void run() {
         synchronized (this) {
             try {
                 /*Authorization to IBM Service*/
                 Authenticator authenticator = new IamAuthenticator("rCQPOsySSTdS0JRmP56Aw6zLnsMktgCsn1Oe7-LQ5RfV");
                 LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", authenticator);
                 languageTranslator.setServiceUrl("https://api.eu-gb.language-translator.watson.cloud.ibm.com/instances/cbd6be82-43a1-4f31-97cd-3f66d5d5db87");

                 /*Extract Values of shared Variables to enter the translator*/
                 String Txt = translatetext.Original;
                 String src =translatetext.src_lang;
                 String dst =translatetext.targrt_lang;
                 String id = src + "-" + dst;


                 TranslateOptions translateOptions = new TranslateOptions.Builder()
                         .addText(Txt)
                         .modelId(id)
                         .build();

                 /*Executing Translation and getting results*/
                 TranslationResult result = languageTranslator.translate(translateOptions)
                         .execute().getResult();

                /*Extracting Translation from result*/
                 List Translation = result.getTranslations();
                 Object trans = result.getTranslations().get(0);
                 String str = trans.toString();

                 IBM = str.substring(20, str.length() - 3); /* getting the text we want to view only*/

             } catch (NotFoundException e) {
                 // Handle Not Found (404) exception
             } catch (RequestTooLargeException e) {
                 // Handle Request Too Large (413) exception
             } catch (ServiceResponseException e) {
                 // Base class for all exceptions caused by error responses from the service
                 System.out.println("Service returned status code "
                         + e.getStatusCode() + ": " + e.getMessage());
             } catch (Exception e) {
                 Log.e("TAG", e.getMessage());
             }
             notify(); //After the thread finishes it notifies the main thread that it finished so main thread can proceed
         }
     }
}
