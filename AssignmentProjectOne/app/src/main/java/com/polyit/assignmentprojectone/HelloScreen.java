package com.polyit.assignmentprojectone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class HelloScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_screen);

        ImageView ivHelloScreen = findViewById(R.id.ivHelloSceen);

        Glide.with(this).load(R.mipmap.hlloscreen).into(ivHelloScreen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(HelloScreen.this,LoginActivity.class));
            }
        },2500);
    }
}