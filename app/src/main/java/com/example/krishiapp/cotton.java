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

public class cotton extends YouTubeBaseActivity implements View.OnClickListener {

    Button btn, cottoninfobtn,cottontypebtn,cottonrequirebtn,cottonpestbtn;
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotton);

        cottoninfobtn = (Button) findViewById(R.id.introcotton);
        cottontypebtn = (Button) findViewById(R.id.typesofcotton);
        cottonrequirebtn = (Button) findViewById(R.id.climate_soil_ofcotton);
        cottonpestbtn = (Button) findViewById(R.id.diseases_cotton);

        cottoninfobtn.setOnClickListener(this);
        cottontypebtn.setOnClickListener(this);
        cottonpestbtn.setOnClickListener(this);
        cottonrequirebtn.setOnClickListener(this);


        btn = findViewById(R.id.playforcotton);
        youTubePlayerView = findViewById(R.id.cottonyoutube);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("0mDByGrFHik");
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
            case R.id.introcotton:
            { Intent intent = new Intent(this,cotton_info.class);
                startActivity(intent);
                break;}
            case R.id.typesofcotton:{
                Intent intent = new Intent(this,typesofcotton.class);
                startActivity(intent);
                break;}
            case R.id.climate_soil_ofcotton:{
                Intent intent = new Intent(this,cottonrequire.class);
                startActivity(intent);
                break;
            }
            case R.id.diseases_cotton:{
                Intent intent = new Intent(this,cottonpests.class);
                startActivity(intent);
                break;
            }
        }

    }
}