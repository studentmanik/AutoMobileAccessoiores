package com.example.automobileaccessoiores.automobileaccessoiores;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_app);
        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(2*1000);
                    Intent i=new Intent(getBaseContext(),MainProduct.class);
                    startActivity(i);
                    finish();
                } catch (Exception e) {

                }
            }
        };
        background.start();
    }


}
