package com.example.tobashunsuke.reversi;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Computer {

    private GameObject gObj = new GameObject();

    private static int comOrder1 = 2; // cpu
    private static boolean hasTurnChange = false;
    private static int comLV = 0; // コンピュータレベル格納場所

    private int[] cornerPosition1 = {0, 0, 0, 7, 7, 56}; // doCornerCheck2をループ処理するための格納場所
    private int[] cornerPosition2 = {7, 56, 63, 56, 63, 63}; // doCornerCheck2をループ処理するための格納場所

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

    private void waitTime(){ // 少し待つ処理

        try {
            Thread.sleep(1000); //1000ミリ秒Sleepする
        } catch (InterruptedException e) {}

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

    private int weightPosition1[] = { 30, -12,  0, -1, -1,  0, -12,  30,
                                     -12, -15, -3, -3, -3, -3, -15, -12,
                                       0,  -3,  0, -1, -1,  0,  -3,   0,
                                      -1,  -3, -1, -1, -1, -1,  -3,  -1,
                                      -1,  -3, -1, -1, -1, -1,  -3,  -1,
                                       0,  -3,  0, -1, -1,  0,  -3,   0,
                                     -12, -15, -3, -3, -3, -3, -15, -12,
                                      30, -12,  0, -1, -1,  0, -12,  30
                                    };

    public void weightList(){
        weightList.clear();

        for(int i = 0; i <= 63; i++){
            weightList.add(weightPosition1[i]);
        }
    }

    private int weightPosition2[] = { 40,  2,  12,   9,   9,  12,   2,  40,
                                       2,  0,  10,   2,   2,  10,   0,   2,
                                      12, 10,   5,   3,   3,   5,  10,  12,
                                       9,  2,   3,   3,   3,   3,   2,   9,
                                       9,  2,   3,   3,   3,   3,   2,   9,
                                      12, 10,   5,   3,   3,   5,  10,  12,
                                       2,  0,  10,   2,   2,  10,   0,   2,
                                      40,  2,  12,   9,   9,  12,   2,  40
                                    };

    public void weightList2(){
        weightList.clear();

        for(int i = 0; i <= 63; i++){
            weightList.add(weightPosition2[i]);
        }
    }

    private int weightPosition3[] = { 50, 10, 10, 10, 10, 10, 10, 50,
                                      10,  3,  3,  3,  3,  3,  3, 10,
                                      10,  3,  3,  3,  3,  3,  3, 10,
                                      10,  3,  3,  3,  3,  3,  3, 10,
                                      10,  3,  3,  3,  3,  3,  3, 10,
                                      10,  3,  3,  3,  3,  3,  3, 10,
                                      10,  3,  3,  3,  3,  3,  3, 10,
                                      50, 10, 10, 10, 10, 10, 10, 50
                                    };

    public void weightList3(){
        weightList.clear();

        for(int i = 0; i <= 63; i++){
            weightList.add(weightPosition3[i]);
        }
    }


    private int outsidePosition[] = {2, 3, 4, 5, 16, 23, 24, 31, 32, 39, 40, 47, 58, 59, 60, 61}; // 外側真ん中４つ

    // 中盤チェック
    private boolean doOutsideCheck(){
        for(int i = 0; i <= 15; i++){
            if (gObj.getImgList().get(outsidePosition[i]).equals(gObj.getPiece(gObj.getSTurn()))){ // 外側真ん中４つに誰かの石が入っているか確認
                return true; // 入っていたら
            }
        }
        return false;
    }

    // 終盤チェック1
    private boolean doCornerCheck1(int position){
        if (gObj.getImgList().get(position).equals(gObj.getPiece(gObj.getSTurn()))){ // 角に自分の石が入っているか確認
            return true; // 入っていたら
        }

        return false; // 入っていなかったら
    }

    // 終盤チェック2
    private boolean doCornerCheck2(){
        for(int i = 0; i <= 5; i++){ // ２箇所に同じ色の石が入っているか確認
            if (doCornerCheck1(cornerPosition1[i]) && doCornerCheck1(cornerPosition2[i])){
                return true;
            }
        }
        return false;
    }


    public int com2AI(){

        /*
        if(doOutsideCheck()){ // 中盤の重みへ
            weightList2();
        }*/

        if(gObj.getTCount() > 30 && doCornerCheck2()){ // 終盤の重みへ
            weightList3();
        }

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
