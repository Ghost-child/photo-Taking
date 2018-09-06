package com.example.claudio.splashscreen;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.felipecsl.gifimageview.library.GifImageView;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private GifImageView gifImage;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gifImage = findViewById(R.id.gifImageView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(progressBar.VISIBLE);

        //get GIF image from asset folder and convert it to byte array
        //set GifImageView resource

        try{
            InputStream inputStream = getAssets().open("growzz.gif");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            gifImage.setBytes(bytes);
            gifImage.startAnimation();
        }

        catch (IOException ex)
        {

        }
        //wait for 15 seconds and start next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.startActivity(new Intent(getApplicationContext(), LoginPage.class));
                MainActivity.this.finish();


            }
        },15000);


    }
}
