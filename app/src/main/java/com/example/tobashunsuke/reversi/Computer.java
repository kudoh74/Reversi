package com.example.tobashunsuke.reversi;

import android.util.Log;

import java.util.Random;

public class Computer {

    private GameObject gObj = new GameObject();

    private static int comOrder1 = 2; // cpu

    public int getComOrder1() {
        return comOrder1;
    }

    public void setComOrder1(int sTurn) {
        this.comOrder1 = comOrder1;
    }

    public void waitTime(){ // 少し待つ処理

        try {
            Thread.sleep(100); //500ミリ秒Sleepする
        } catch (InterruptedException e) {
        }

    }

    public int com1AI(){
        Random rnd = new Random();
        Log.d("onclick", String.valueOf(gObj.getCanPutList().get(rnd.nextInt(gObj.getCanPutList().size()))));
        return gObj.getCanPutList().get(rnd.nextInt(gObj.getCanPutList().size()));
    }

}
