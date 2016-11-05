package com.example.tobashunsuke.reversi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class GameMode extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_mode);

        final ImageButton toHvsH = (ImageButton) findViewById(R.id.human_vs_human);
        final ImageButton returnMain = (ImageButton) findViewById(R.id.to_title);
        final ImageButton toHvsC1 = (ImageButton) findViewById(R.id.human_vs_computer_level_1);
        final ImageButton toHvsC2 = (ImageButton) findViewById(R.id.human_vs_computer_level_2);

        toHvsH.setOnClickListener(new View.OnClickListener() { // 人　対　人　へ
            public void onClick(View v) {
                if(v == toHvsH){
                    Intent intent = new Intent(GameMode.this, GameHvsH.class);
                    startActivity(intent);
                }
            }
        });

        toHvsC1.setOnClickListener(new View.OnClickListener() { // 人　対　cpu　へ
            public void onClick(View v) {
                if(v == toHvsC1){
                    Intent intent = new Intent(GameMode.this, GameHvsC1.class);
                    startActivity(intent);
                }
            }
        });

        toHvsC2.setOnClickListener(new View.OnClickListener() { // 人　対　cpu　へ
            public void onClick(View v) {
                if(v == toHvsC2){
                    Intent intent = new Intent(GameMode.this, GameHvsC2.class);
                    startActivity(intent);
                }
            }
        });

        returnMain.setOnClickListener(new View.OnClickListener() { // タイトルへ
            public void onClick(View v) {
                if(v == returnMain){
                    finish();
                }
            }
        });
    }
}
