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

        final Computer mComputer = new Computer();

        final ImageButton toHvsH = (ImageButton) findViewById(R.id.human_vs_human);
        final ImageButton returnMain = (ImageButton) findViewById(R.id.to_title);
        final ImageButton toHvsC1 = (ImageButton) findViewById(R.id.human_vs_computer_level_1);
        final ImageButton toHvsC2 = (ImageButton) findViewById(R.id.human_vs_computer_level_2);

        // 人対人
        toHvsH.setOnClickListener(new View.OnClickListener() { // 人　対　人　へ
            public void onClick(View v) {
                if(v == toHvsH){
                    mComputer.setComLV(0);
                    Intent intent = new Intent(GameMode.this, GameHvsH.class);
                    startActivity(intent);
                }
            }
        });

        // 人対COMLv1
        toHvsC1.setOnClickListener(new View.OnClickListener() { // 人　対　cpu　へ
            public void onClick(View v) {
                if(v == toHvsC1){
                    mComputer.setComLV(1); // コンピュータレベルの設定
                    Intent intent = new Intent(GameMode.this, GameHvsC1.class);
                    startActivity(intent);
                }
            }
        });

        // 人対COMLv2
        toHvsC2.setOnClickListener(new View.OnClickListener() { // 人　対　cpu　へ
            public void onClick(View v) {
                if(v == toHvsC2){
                    mComputer.setComLV(2);
                    Intent intent = new Intent(GameMode.this, GameHvsC1.class);
                    startActivity(intent);
                }
            }
        });

        // タイトルへ
        returnMain.setOnClickListener(new View.OnClickListener() { // タイトルへ
            public void onClick(View v) {
                if(v == returnMain){
                    finish();
                }
            }
        });
    }
}
