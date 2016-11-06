package com.example.tobashunsuke.reversi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Computer {

    private GameObject gObj = new GameObject();

    private static int comOrder1 = 2; // cpu
    private static boolean hasTurnChange = false;
    private static int comLV = 0;

    private static List<Integer> weightList = new ArrayList<>(); // 重み付け格納リスト

    public int getComOrder1() {
        return comOrder1;
    }

    public void setComOrder1(int comOrder1) {
        this.comOrder1 = comOrder1;
    }

    public int getComLV() {
        return comLV;
    }

    public void setComLV(int comLV) {
        this.comLV = comLV;
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

    public void initCom2List(){
        weightList.clear();

        weightList.add(30); // a1
        weightList.add(-12);
        weightList.add(0);
        weightList.add(-1);
        weightList.add(-1);
        weightList.add(0);
        weightList.add(-12);
        weightList.add(30);
        weightList.add(-12); // a2
        weightList.add(-15);
        weightList.add(-3);
        weightList.add(-3);
        weightList.add(-3);
        weightList.add(-3);
        weightList.add(-15);
        weightList.add(-12);
        weightList.add(0); // a3
        weightList.add(-3);
        weightList.add(0);
        weightList.add(1);
        weightList.add(1);
        weightList.add(0);
        weightList.add(-3);
        weightList.add(0);
        weightList.add(-1); // a4
        weightList.add(-3);
        weightList.add(-1);
        weightList.add(-1);
        weightList.add(-1);
        weightList.add(-1);
        weightList.add(-3);
        weightList.add(-1);
        weightList.add(-1); // a5
        weightList.add(-3);
        weightList.add(-1);
        weightList.add(-1);
        weightList.add(-1);
        weightList.add(-1);
        weightList.add(-3);
        weightList.add(-1);
        weightList.add(0); // a6
        weightList.add(-3);
        weightList.add(0);
        weightList.add(1);
        weightList.add(1);
        weightList.add(0);
        weightList.add(-3);
        weightList.add(0);
        weightList.add(-12); // a7
        weightList.add(-15);
        weightList.add(-3);
        weightList.add(-3);
        weightList.add(-3);
        weightList.add(-3);
        weightList.add(-15);
        weightList.add(-12);
        weightList.add(30); // a8
        weightList.add(-12);
        weightList.add(0);
        weightList.add(-1);
        weightList.add(-1);
        weightList.add(0);
        weightList.add(-12);
        weightList.add(30);
    }

    public int com2AI(){
        int max = -15; // 重みで一番低い値で初期化
        int iCntnr = 0; // iの格納場所
        Log.d("onclick", String.valueOf(gObj.getCanPutList().size()) + "piece");
        if(gObj.getCanPutList().size() == 1){ // canPutListのサイズが1だと乱数を使えないため
            return gObj.getCanPutList().get(0);
        } else if(gObj.getCanPutList().size() < 1){ // canPutListのエラー
            return -1;
        } else {
            for (int i = 0; i < gObj.getCanPutList().size(); i++) {
                if(max < weightList.get(gObj.getCanPutList().get(i))){
                    max = weightList.get(gObj.getCanPutList().get(i));
                    iCntnr = i;
                }
            }
            return gObj.getCanPutList().get(iCntnr); // 配列の個数を引数に0〜n-1の乱数を返しそれを添字にリストの値を返す
        }
    }

}
