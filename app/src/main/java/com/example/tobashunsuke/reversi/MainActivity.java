package com.example.tobashunsuke.reversi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by tobashunsuke on 2016/10/22.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button toGameMode = (Button) findViewById(R.id.to_game_mode);

        toGameMode.setOnClickListener(new View.OnClickListener() { // メニューへ
            public void onClick(View v) {
                if(v == toGameMode){
                    Intent intent = new Intent(MainActivity.this, GameMode.class);
                    startActivity(intent);
                }
            }
        });

    }
}
