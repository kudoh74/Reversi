package com.example.tobashunsuke.reversi;

import android.util.Log;

import java.util.Random;

public class Computer {

    private GameObject gObj = new GameObject();

    private static int comOrder1 = 2; // cpu
    private static boolean hasTurnChange = false;

    public int getComOrder1() {
        return comOrder1;
    }

    public void setComOrder1(int comOrder1) {
        this.comOrder1 = comOrder1;
    }

    public boolean getHasTurnChange() {
        return hasTurnChange;
    }

    public void setHasTurnChange(boolean hasTurnChange) {
        this.hasTurnChange = hasTurnChange;
    }

    public void waitTime(){ // 少し待つ処理

        try {
            Thread.sleep(1000); //500ミリ秒Sleepする
        } catch (InterruptedException e) {
        }

    }

    public int com1AI(){
        Random rnd = new Random();
        Log.d("onclick", String.valueOf(gObj.getCanPutList().size()) + "piece");
        if(gObj.getCanPutList().size() == 1){ // canPutListのサイズが1だと乱数を使えないため
            return gObj.getCanPutList().get(0);
        } else if(gObj.getCanPutList().size() < 1){ // canPutListのエラー
            return -1;
        } else {
            return gObj.getCanPutList().get(rnd.nextInt(gObj.getCanPutList().size())); // 配列の個数を引数に0〜n-1の乱数を返しそれを添字にリストの値を返す
        }
    }

}
