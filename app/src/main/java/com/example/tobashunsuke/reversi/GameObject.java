package com.example.tobashunsuke.reversi;

import java.util.ArrayList;
import java.util.List;

public class GameObject {
    // 画像をpieceに入れる、配列にすることで添字でいろいろできるようになる
    private final String[] piece = {String.valueOf(R.drawable.ban), String.valueOf(R.drawable.kuro), String.valueOf(R.drawable.shiro), String.valueOf(R.drawable.put)};
    private final String[] piece2 = {String.valueOf(R.drawable.ban), String.valueOf(R.drawable.blackdroid), String.valueOf(R.drawable.whitedroid), String.valueOf(R.drawable.put)};
    // 要素をArrayListで設定
    private static List<String> imgList = new ArrayList<>(); // staticにすると処理ができる

    private static List<Integer> canPutList = new ArrayList<>(); // 置くことができる場所を記録

    private static int sTurn = 1; // 1なら黒,2なら白
    private static int tCount = 0; // ターン数
    private static int wCount = 2; // 白の数
    private static int bCount = 2; // 黒の数
    private static int pCount = 0; // 置ける場所のカウント
    private static boolean skipTurn = false; // 両方置けなかった場合

    private static boolean droidMode = false; // 石の画像をドロイドに変更、falseだと通常の石、trueだとドロイド

    public boolean getDroidMode(){
        return droidMode;
    }

    public void setDroidMode(boolean droidMode){
        this.droidMode = droidMode;
    }

    public String getPiece(int position){
        if(!droidMode){
            return piece[position];
        } else{
            return piece2[position];
        }
    }

    public List<String> getImgList(){ // staticにすると処理ができる
        return imgList;
    }

    public List<Integer> getCanPutList(){ // staticにすると処理ができる
        return canPutList;
    }

    public int getSTurn(){
        return sTurn;
    }

    public void setSTurn(int sTurn){
        this.sTurn = sTurn;
    }

    public int getTCount(){
        return tCount;
    }

    public void setTCount(int tCount){
        this.tCount = tCount;
    }

    public int getWCount(){
        return wCount;
    }

    public void setWCount(int wCount){
        this.wCount = wCount;
    }

    public int getBCount(){
        return bCount;
    }

    public void setBCount(int bCount){
        this.bCount = bCount;
    }

    public int getPCount(){
        return pCount;
    }

    public void setPCount(int pCount){
        this.pCount = pCount;
    }

    public boolean getSkipTurn(){
        return skipTurn;
    }

    public void setSkipTurn(boolean skipTurn){
        this.skipTurn = skipTurn;
    }
}
