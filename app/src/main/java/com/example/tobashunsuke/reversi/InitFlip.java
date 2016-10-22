package com.example.tobashunsuke.reversi;

import java.util.ArrayList;
import java.util.List;

public class InitFlip {

    private GameObject gObj = new GameObject();

    // 打った場所の格納
    private List<Integer> pList = new ArrayList<>();

    private static int sCnt = 1; // 何マスひっくり返すか

    private final int MAXBOARD = 63; //  リストの格納数0〜63
    private final int MINBOARD = 0; //  リストの格納数0〜63

    //盤の初期化
    protected void initBoard() {
        gObj.getImgList().clear(); // 盤の中身の画像をクリア
        for (int i = MINBOARD; i <= MAXBOARD; i++) { // 盤に緑の盤画像を挿入
            gObj.getImgList().add(gObj.getPiece(0));
        }
        gObj.getImgList().set(27, gObj.getPiece(2));
        gObj.getImgList().set(28, gObj.getPiece(1));
        gObj.getImgList().set(35, gObj.getPiece(1));
        gObj.getImgList().set(36, gObj.getPiece(2));
        pList.clear(); // リストの初期化

        // 真ん中四ます分を埋める
        pList.add(27);
        pList.add(28);
        pList.add(35);
        pList.add(36);

        gObj.setTCount(0);
        gObj.setWCount(2);
        gObj.setBCount(2);
        gObj.setPCount(0);

        gObj.setSkipTurn(false);

        gObj.setSTurn(1); // 黒先行
        //Log.d("onClick","through");
    }

    // 一回打った場所か判定
    public boolean isDuplicate(int listPosition) { // 一回打ったところでなければfalseを返す
        boolean duplicate = false;
        for (int i : pList) {
            if (listPosition == i) duplicate = true; // 一回打った場所か判定
        }
        pList.add(listPosition); // 打った場所を保存
        return duplicate;
    }

    // 置ける場所に置ける画像を配置
    public void putPlace() {
        gObj.setPCount(0);
        for (int i = MINBOARD; i <= MAXBOARD; i++) {
            if (gObj.getImgList().get(i).equals(gObj.getPiece(3))) { // 置ける場所の画像を盤の画像に
                gObj.getImgList().set(i, gObj.getPiece(0));
            }
            if (canPut(i) && gObj.getImgList().get(i).equals(gObj.getPiece(0))) { // 置くことができるか判定し、置きたい場所が盤の画像なら
                gObj.getImgList().set(i, gObj.getPiece(3)); // 置くことができる画像を配置
                gObj.setPCount(gObj.getPCount() + 1); // 置くことができたらスキップしないようにpCountに+1
            }
        }
        //Log.d("putPlace", "through");
    }

    public void computerMove(){

    }

    // 上方向の処理
    private boolean topFunc(int valuePosition) {
        valuePosition -= 8; // 置く場所の上
        for (sCnt = 1; MINBOARD <= valuePosition && !(gObj.getImgList().get(valuePosition)).equals(gObj.getPiece(0)); sCnt++) {
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(3))) break; // 置くことができる画像ならループを抜ける
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn())))
                return true; // ループを回して自分の画像がきたらtrueを返す
            valuePosition -= 8;
        }
        return false;
    }

    // 右上方向の処理
    private boolean toprightFunc(int valuePosition) {
        valuePosition -= 7;
        for (sCnt = 1; MINBOARD <= valuePosition && valuePosition % 8 != 7 && !(gObj.getImgList().get(valuePosition)).equals(gObj.getPiece(0)); sCnt++) {
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(3))) break;
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) return true;
            if (valuePosition % 8 == 0) break;
            valuePosition -= 7;
            if (MINBOARD <= valuePosition && gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) {
                sCnt++;
                return true;
            }
            if (valuePosition % 8 == 0) break;
        }
        return false;
    }

    // 右方向の処理
    private boolean rightFunc(int valuePosition) {
        valuePosition += 1;
        for (sCnt = 1; valuePosition <= MAXBOARD && valuePosition % 8 != 7 && !(gObj.getImgList().get(valuePosition)).equals(gObj.getPiece(0)); sCnt++) {
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(3))) break;
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) return true;
            if (valuePosition % 8 == 0) break;
            valuePosition += 1;
            if (valuePosition <= MAXBOARD && gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) {
                sCnt++;
                return true;
            }
            if (valuePosition % 8 == 0) break;
        }
        return false;
    }

    // 下右方向の処理
    private boolean bottomrightFunc(int valuePosition) {
        valuePosition += 9;
        for (sCnt = 1; valuePosition <= MAXBOARD && valuePosition % 8 != 7 && !(gObj.getImgList().get(valuePosition)).equals(gObj.getPiece(0)); sCnt++) {
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(3))) break;
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) return true;
            if (valuePosition % 8 == 0) break;
            valuePosition += 9;
            if (valuePosition <= MAXBOARD && gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) {
                sCnt++;
                return true;
            }
            if (valuePosition % 8 == 0) break;
        }
        return false;
    }

    // 下方向の処理
    private boolean bottomFunc(int valuePosition) {
        valuePosition += 8;
        for (sCnt = 1; valuePosition <= MAXBOARD && !(gObj.getImgList().get(valuePosition)).equals(gObj.getPiece(0)); sCnt++) {
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(3))) break;
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) return true;
            valuePosition += 8;
        }
        return false;
    }

    // 下左方向の処理
    private boolean bottomleftFunc(int valuePosition) {
        valuePosition += 7;
        for (sCnt = 1; valuePosition <= 55 && valuePosition % 8 != 0 && !(gObj.getImgList().get(valuePosition)).equals(gObj.getPiece(0)); sCnt++) {
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(3))) break;
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) return true;
            if (valuePosition % 8 == 0) break;
            valuePosition += 7;
            if (valuePosition <= MAXBOARD && gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) {
                sCnt++;
                return true;
            }
            if (valuePosition % 8 == 0) break;
        }
        return false;
    }

    // 左方向の処理
    private boolean leftFunc(int valuePosition) {
        valuePosition -= 1;
        for (sCnt = 1; valuePosition >= 0 && valuePosition % 8 != 0 && !(gObj.getImgList().get(valuePosition)).equals(gObj.getPiece(0)); sCnt++) {
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(3))) break;
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) return true;
            if (valuePosition % 8 == 0) break;
            valuePosition -= 1;
            if (valuePosition >= MINBOARD && gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) {
                sCnt++;
                return true;
            }
            if (valuePosition % 8 == 0) break;
        }
        return false;
    }

    // 上左方向の処理
    private boolean topleftFunc(int valuePosition) {
        valuePosition -= 9;
        for (sCnt = 1; 8 <= valuePosition && !(gObj.getImgList().get(valuePosition)).equals(gObj.getPiece(0)); sCnt++) {
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(3))) break;
            if (gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) return true;
            if (valuePosition % 8 == 0) break;
            valuePosition -= 9;
            if (MINBOARD <= valuePosition && gObj.getImgList().get(valuePosition).equals(gObj.getPiece(gObj.getSTurn()))) {
                sCnt++;
                return true;
            }
            if (valuePosition % 8 == 0) break;
        }
        return false;
    }

    // 間の判定
    public void pBetween(int boardPosition) {

        // Log.d("onclick", "through");
        boolean overTop = false, overRight = false, overBottom = false, overLeft = false;

        boolean firstBoradPosition = false, lastBoradPosition = false;

        // gObj.getImgList().getの範囲外にならないようにする
        if (boardPosition - 8 < MINBOARD) overTop = true;
        if (boardPosition % 8 == 0) overLeft = true;
        if (boardPosition % 8 == 7) overRight = true;
        if (boardPosition + 8 > MAXBOARD) overBottom = true;
        if (boardPosition > 61) lastBoradPosition = true; // 右方向の処理、リストの範囲外になるのを防ぐ
        if (boardPosition < 2) firstBoradPosition = true; // 左方向の処理、リストの範囲外になるのを防ぐ

        int valueCntnr = boardPosition; // valueCntnrで計算する

        // 上方向の返す処理
        if (!overTop) {
            if (gObj.getImgList().get(boardPosition - 8).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (topFunc(valueCntnr)) { // ひっくり返す判定
                    valueCntnr = boardPosition;
                    while (sCnt-- > 1) { // sCnt分返す
                        valueCntnr -= 8;
                        gObj.getImgList().set(valueCntnr, gObj.getPiece(gObj.getSTurn()));
                    }
                }
            }
        }

        valueCntnr = boardPosition;

        // 上右方向の返す処理
        if (!overTop && !overRight) {
            if (gObj.getImgList().get(boardPosition - 7).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                //Log.d("onClick","変数 myPiece は「" + myPiece + "」top-right");
                if (toprightFunc(valueCntnr)) {
                    valueCntnr = boardPosition;
                    while (sCnt-- > 1) {
                        valueCntnr -= 7;
                        gObj.getImgList().set(valueCntnr, gObj.getPiece(gObj.getSTurn()));
                    }
                }
            }
        }

        valueCntnr = boardPosition;

        // 右方向の返す処理
        if (!lastBoradPosition && !overRight) {
            if (gObj.getImgList().get(boardPosition + 1).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (rightFunc(valueCntnr)) {
                    valueCntnr = boardPosition;
                    while (sCnt-- > 1) {
                        valueCntnr += 1;
                        gObj.getImgList().set(valueCntnr, gObj.getPiece(gObj.getSTurn()));
                    }
                }
            }
        }

        valueCntnr = boardPosition;

        // 下右方向の返す処理
        if (!overBottom && !overRight && boardPosition + 9 <= MAXBOARD) {
            if (gObj.getImgList().get(boardPosition + 9).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (bottomrightFunc(valueCntnr)) {
                    valueCntnr = boardPosition;
                    while (sCnt-- > 1) {
                        valueCntnr += 9;
                        gObj.getImgList().set(valueCntnr, gObj.getPiece(gObj.getSTurn()));
                    }
                }
            }
        }

        valueCntnr = boardPosition;

        // 下方向の返す処理
        if (!overBottom) {
            if (gObj.getImgList().get(boardPosition + 8).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                //Log.d("onClick","変数 myPiece は「" + myPiece + "」bottom");
                if (bottomFunc(valueCntnr)) {
                    valueCntnr = boardPosition;
                    while (sCnt-- > 1) {
                        valueCntnr += 8;
                        gObj.getImgList().set(valueCntnr, gObj.getPiece(gObj.getSTurn()));
                    }
                }
            }
        }

        valueCntnr = boardPosition;

        // 下左方向の返す処理
        if (!overBottom && !overLeft) {
            if (gObj.getImgList().get(boardPosition + 7).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                //Log.d("onClick","変数 myPiece は「" + myPiece + "」bottom-left");
                if (bottomleftFunc(valueCntnr)) {
                    valueCntnr = boardPosition;
                    while (sCnt-- > 1) {
                        valueCntnr += 7;
                        gObj.getImgList().set(valueCntnr, gObj.getPiece(gObj.getSTurn()));
                    }
                }
            }
        }

        valueCntnr = boardPosition;

        // 左方向の返す処理
        if (!firstBoradPosition && !overLeft) {
            if (gObj.getImgList().get(boardPosition - 1).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                //Log.d("onClick","変数 myPiece は「" + myPiece + "」left");
                if (leftFunc(valueCntnr)) {
                    valueCntnr = boardPosition;
                    while (sCnt-- > 1) {
                        valueCntnr -= 1;
                        gObj.getImgList().set(valueCntnr, gObj.getPiece(gObj.getSTurn()));
                    }
                }
            }
        }

        valueCntnr = boardPosition;

        // 上左方向の返す処理
        if (!overTop && !overLeft && boardPosition - 9 >= MINBOARD) {
            if (gObj.getImgList().get(boardPosition - 9).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                //Log.d("onClick","変数 myPiece は「" + myPiece + "」top-left");
                if (topleftFunc(valueCntnr)) {
                    valueCntnr = boardPosition;
                    while (sCnt-- > 1) {
                        valueCntnr -= 9;
                        gObj.getImgList().set(valueCntnr, gObj.getPiece(gObj.getSTurn()));
                    }
                }
            }
        }
    }

    // 置くことができるか判定
    public boolean canPut(int boardPosition) {
        boolean overTop = false, overRight = false, overBottom = false, overLeft = false;

        boolean firstBoradPosition = false, lastBoradPosition = false;

        if (boardPosition - 8 < MINBOARD) overTop = true;
        if (boardPosition % 8 == 0) overLeft = true;
        if (boardPosition % 8 == 7) overRight = true;
        if (boardPosition + 8 > MAXBOARD) overBottom = true;
        if (boardPosition > 61) lastBoradPosition = true;
        if (boardPosition < 2) firstBoradPosition = true;

        int valueCntnr = boardPosition;

        // 上
        if (!overTop) {
            if (gObj.getImgList().get(boardPosition - 8).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (topFunc(valueCntnr)) return true;
            }
        }

        valueCntnr = boardPosition;

        // 右上
        if (!overTop && !overRight) {
            if (gObj.getImgList().get(boardPosition - 7).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (toprightFunc(valueCntnr)) return true;
            }
        }

        valueCntnr = boardPosition;

        // 右
        if (!lastBoradPosition && !overRight) {
            if (gObj.getImgList().get(boardPosition + 1).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (rightFunc(valueCntnr)) return true;
            }
        }

        valueCntnr = boardPosition;

        // 右下
        if (!overBottom && !overRight && boardPosition + 9 <= MAXBOARD) {
            if (gObj.getImgList().get(boardPosition + 9).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (bottomrightFunc(valueCntnr)) return true;
            }
        }

        valueCntnr = boardPosition;

        // 下
        if (!overBottom) {
            if (gObj.getImgList().get(boardPosition + 8).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (bottomFunc(valueCntnr)) return true;
            }
        }

        valueCntnr = boardPosition;

        // 左下
        if (!overBottom && !overLeft) {
            if (gObj.getImgList().get(boardPosition + 7).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (bottomleftFunc(valueCntnr)) return true;
            }
        }

        valueCntnr = boardPosition;

        // 左
        if (!firstBoradPosition && !overLeft) {
            if (gObj.getImgList().get(boardPosition - 1).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (leftFunc(valueCntnr)) return true;
            }
        }

        valueCntnr = boardPosition;

        // 左上
        if (!overTop && !overLeft && boardPosition - 9 >= MINBOARD) {
            if (gObj.getImgList().get(boardPosition - 9).equals(gObj.getPiece(3 - gObj.getSTurn()))) {
                if (topleftFunc(valueCntnr)) return true;
            }
        }

        return false; // どの方向も返すことができない場合
    }
}
