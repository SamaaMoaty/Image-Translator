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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.util.List;
import java.util.Locale;

public class translatedimage extends AppCompatActivity {
    /*Defining the activity Components*/
    TextView textView;
    TextView transTxt;
    Button Translate;
    Spinner src_img;
    Spinner tar_img;

    /*Public Volatile Strings to be used in the other Thread that calls IBM cloud */
    public volatile static String Original;
    public volatile static String src_lang;
    public volatile static String targrt_lang;

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
        setContentView(R.layout.activity_translatedimage);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        src_img =findViewById(R.id.spinner3);
        tar_img =findViewById(R.id.spinner4);
        src_img.setAdapter(adapter);
        tar_img.setAdapter(adapter);
        Toast.makeText(this,"Translation Process may take few seconds. \nPlease wait after pressing the translate button..." , Toast.LENGTH_LONG).show();
        /*Getting the Source language abbreviation From the selected item from dropdown menu */
        src_img.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        tar_img.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        textView = findViewById(R.id.textView5);
        transTxt = findViewById(R.id.textView8);
        //enable scrolling
        textView.setMovementMethod(new ScrollingMovementMethod());
        transTxt.setMovementMethod(new ScrollingMovementMethod());
        /*Get Text from intent sent from previous activity*/
        String extra = getIntent().getStringExtra("text");
        if(extra.equals("") ){
            textView.setText("No Text Recogonised...");
        }
        else{
            textView.setText(extra);
        }

        /*Translate Button Function*/
        Translate = findViewById(R.id.button7);
        Translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Original = textView.getText().toString();
                if(Original.equals("No Text Recogonised...")){
                    textView.setText("No Text Recogonised Please Try another Image...");
                }
                else{
                    IBM_Translator();
                }
            }
        });
    }

    /*Beginning of Translating Function*/
    private void IBM_Translator() {

        /*Creating a new Thread for the service to run on*/
        Thread1 thread = new Thread1();
        thread.start();

        /*Synchronization for the safety of the shared Variables*/
        synchronized (thread) {

            try {
                thread.wait();//wait until thread finishes
                String ocrTrans = thread.IBM;
                transTxt.setText(ocrTrans);//Assigning the Translation to the Translated Text Textbox
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/*Starting the Class of the new thread */
class Thread1 extends java.lang.Thread{
    String IBM ;
    @Override
    public void run() {
        synchronized (this) {
            try {
                /*Authorization to IBM Service*/
                Key = "Add your key here";
                URL = "Add your Translator URL here";
                Authenticator authenticator = new IamAuthenticator(Key);
                Discovery service = new Discovery("2018-05-01", authenticator);
                LanguageTranslator languageTranslator = new LanguageTranslator("2018-05-01", authenticator);
                languageTranslator.setServiceUrl(URL);

                /*Extract Values of shared Variables to enter the translator*/
                String Txt = translatedimage.Original;
                String src =translatedimage.src_lang;
                String dst =translatedimage.targrt_lang;
                String id = src + "-" + dst;
                String newText ="" ;
                for(int i =0;i <Txt.length();i++){
                    if(Txt.substring(i,i+1).equals("\n") ){
                        newText = newText+ " ";
                    }
                    else{
                        newText = newText + Txt.substring(i,i+1);
                    }
                }

                TranslateOptions translateOptions = new TranslateOptions.Builder()
                        .addText(newText)
                        .modelId(id)
                        .build();

                /*Executing Translation and getting results*/
                TranslationResult result = languageTranslator.translate(translateOptions)
                        .execute().getResult();

                /*Extracting Translation from result*/
                List Translation = result.getTranslations();
                Object trans = result.getTranslations().get(0);
                String str = trans.toString();
                IBM = str.substring(20, str.length() - 3);/* getting the text we want to view only*/
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
            notify();//After the thread finishes it notifies the main thread that it finished so main thread can proceed
        }
    }
}
