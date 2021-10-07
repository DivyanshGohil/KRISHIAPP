package com.example.krishiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class apple extends YouTubeBaseActivity implements View.OnClickListener {

    Button btn, appleinfobtn,appletypebtn,applerequirebtn,applepestbtn;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apple);

        appleinfobtn = (Button) findViewById(R.id.introapple);
        appletypebtn = (Button) findViewById(R.id.typesofapple);
        applerequirebtn = (Button) findViewById(R.id.climate_soil_ofapple);
        applepestbtn = (Button) findViewById(R.id.diseases_apple);

        appleinfobtn.setOnClickListener(this);
        appletypebtn.setOnClickListener(this);
        applerequirebtn.setOnClickListener(this);
        applepestbtn.setOnClickListener(this);

        btn = findViewById(R.id.playforapple);
        youTubePlayerView = findViewById(R.id.appleyoutube);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("rX0F8fuuhEQ");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                youTubePlayerView.initialize("AIzaSyCMLfGbJHNAe1GWsf3afrTmf9jdjtYYcqg",onInitializedListener);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.introapple:
            { Intent intent = new Intent(this,apple_info.class);
                startActivity(intent);
                break;}
            case R.id.typesofapple:{
                Intent intent = new Intent(this,typesofapple.class);
                startActivity(intent);
                break;}
            case R.id.climate_soil_ofapple:{
                Intent intent = new Intent(this,applerequire.class);
                startActivity(intent);
                break;
            }
            case R.id.diseases_apple:{
                Intent intent = new Intent(this,applepests.class);
                startActivity(intent);
                break;
            }
        }

    }
}