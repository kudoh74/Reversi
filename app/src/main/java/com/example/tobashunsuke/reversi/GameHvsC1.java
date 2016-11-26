package com.example.tobashunsuke.reversi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GameHvsC1 extends AppCompatActivity {
    private GridAdapter adapter;

    private InitFlip initFlip = new InitFlip();
    private GameObject gObj = new GameObject();
    private Computer computer = new Computer();

    private ImageButton returnGameMode;
    private ImageButton afterAttack;

    private ImageButton boardClear;
    private TextView whichTurn;
    private TextView resultText;
    private TextView w_bText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_h_vs_c1);

        returnGameMode = (ImageButton) findViewById(R.id.return_gamemode1); // 戻るボタン
        afterAttack = (ImageButton) findViewById(R.id.after_attack1); // 後攻ボタン

        boardClear = (ImageButton) findViewById(R.id.button_clear1); // 盤のクリア
        whichTurn = (TextView) findViewById(R.id.textView_turn1); // どちらのターンか表示
        resultText = (TextView) findViewById(R.id.textView_result1); // 結果の表示
        w_bText = (TextView) findViewById(R.id.textView_wb1); // 白と黒の数の表示

        resultText.setText(""); // 結果のテキストを描画しない
        setWBText(); // 現在の各石の個数を描画

        // GridViewのインスタンスを生成
        final GridView gridview = (GridView) findViewById(R.id.gridview1);
        // BaseAdapter を継承したGridAdapterのインスタンスを生成
        // 子要素のレイアウトファイル grid_items.xml を activity_main.xml に inflate するためにGridAdapterに引数として渡す
        adapter = new GridAdapter(this.getApplicationContext(), R.layout.grid_items, gObj.getImgList());
        // gridViewにadapterをセット
        gridview.setAdapter(adapter);

        // /assets/image/以下に画像を入れています
        // それのパスを取り出す method
        initFlip.initBoard();

        initFlip.putPlace();// 置ける場所に置ける画像を配置

        initGui(); // guiの初期化
        computer.setHasTurnChange(false);

        if(computer.getComLV() == 1) {
        } else if(computer.getComLV() == 2){
            computer.weightList(); // 重み付けのリストの初期化
        }



        // gridviewをタップしたとき
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (initFlip.canPut(position) && gObj.getImgList().get(position).equals(gObj.getPiece(3))) { // 置くことができ、置ける画像の場所なら

                    for (int i = 0; i <= 1; i++) { // 自分が行動した後に、コンピュータを動作させる

                        if (i == 0) { // 自分の行動
                            doPlayerAndComputer(position); // 場所に対する処理

                            screenuUpdating();

                        } else if(computer.getComOrder1() == gObj.getSTurn()){
                            //Log.e("onclick", "through : 33333");
                            
                            while (computer.getComOrder1() == gObj.getSTurn()) { // プレイヤーのパスが終わるまで
                                initFlip.putPlace();// 置ける場所に置ける画像を配置
                                if(computer.getComLV() == 1) {
                                    position = computer.com1AI(); // comLv1の導き出した場所
                                } else if(computer.getComLV() == 2){
                                    position = computer.com2AI(); // comLv2の導き出した場所
                                }

                                if (position == -1) { // エラー、置く場所がない
                                    gObj.setSTurn(3 - gObj.getSTurn()); // ターンチェンジ
                                    computer.setHasTurnChange(true); // ターンチェンジしたことを保存
                                    setWhichTurn();
                                    //Log.e("onclick", "through : 3");
                                    break;
                                }

                                doPlayerAndComputer(position); // 場所に対する処理
                                //Log.d("onclick", String.valueOf(position));

                                //Log.d("onclick", "through : 1");

                                screenuUpdating();
                            }
                        }

                    }
                }

                if (computer.getHasTurnChange()) {
                    setWhichTurn(); // ないとパスした時に相手のターンが表示される
                    computer.setHasTurnChange(false);
                    //Log.e("onclick", "through : 2");
                    initFlip.putPlace();// 置ける場所に置ける画像を配置
                }
            }

        });

        // クリアボタン
        boardClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // クリアボタンを押したら
                if (v == boardClear) {
                    initFlip.initBoard(); // 盤の初期化
                    initGui();
                    computer.setComOrder1(2); // CPUを後攻に

                    for(int i = 0; i < 5; i++) {
                    }

                    initFlip.putPlace();// 置ける場所に置ける画像を配置
                    screenuUpdating();
                    computer.setHasTurnChange(false);
                    computer.weightList(); // 重み付けのリストを初期化
                }

            }
        });

        // 後攻ボタン
        afterAttack.setOnClickListener(new View.OnClickListener() { // 後攻にする
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                if(v == afterAttack) {
                    int position = 0;
                    initFlip.initBoard();
                    computer.setComOrder1(1); // 後攻にする
                    initGui();

                    if(computer.getComLV() == 1) {
                        position = computer.com1AI(); // comLv1の導き出した場所
                    } else if(computer.getComLV() == 2){
                        position = computer.com2AI(); // comLv2の導き出した場所
                    }

                    doPlayerAndComputer(position);
                    computer.setHasTurnChange(false); // ターンチェンジしたことを保存

                    adapter.notifyDataSetChanged();
                    computer.weightList(); // 重み付けのリストを初期化
                }
            }
        });

        // 戻るボタン
        returnGameMode.setOnClickListener(new View.OnClickListener() { // GameModeクラスに画面遷移
            public void onClick(View v) {
                if(v == returnGameMode) {
                    initFlip.initBoard(); // 盤の初期化
                    initGui();
                    computer.setComOrder1(2); // CPUを後攻に
                    initFlip.putPlace();// 置ける場所に置ける画像を配置
                    adapter.notifyDataSetChanged(); // 画面更新
                    computer.setHasTurnChange(false);
                    computer.weightList(); // 重み付けのリストを初期化
                    finish();
                }
            }
        });
    }

    // 画面更新
    private void screenuUpdating(){
        adapter.notifyDataSetChanged(); // 画面更新
    }

    // guiの処理　どちらのターンか描画
    private void setWhichTurn(){
        if (gObj.getSTurn() == 1) {
            whichTurn.setText("黒のターン");
        } else {
            whichTurn.setText("白のターン");
        }
    }
    // guiの処理　現在の各石の個数を描画
    private void setWBText(){
        w_bText.setText(String.format("白:%d - 黒:%d", gObj.getWCount(), gObj.getBCount()));
    }
    private void initGui(){
        resultText.setText(""); // 結果を消す
        initFlip.putPlace(); // 置ける場所を表示
        setWhichTurn();

        setWBText();
    }

    private void doPlayerAndComputer(int position){ // タップした時の動作とコンピュータの動作

        //if (initFlip.canPut(position) && !initFlip.isDuplicate(position)) { // 置くことができ、一回打ったところでない
        if (initFlip.canPut(position) && gObj.getImgList().get(position).equals(gObj.getPiece(3))) { // 置くことができ、置ける画像の場所なら

            if (gObj.getSTurn() == 1) {
                whichTurn.setText("白のターン");
                gObj.getImgList().set(position, gObj.getPiece(1)); // 画像を変える
            } else {
                whichTurn.setText("黒のターン");
                gObj.getImgList().set(position, gObj.getPiece(2)); // 画像を変える
            }
            initFlip.pBetween(position); // 返す処理
            gObj.setSTurn(3 - gObj.getSTurn()); // ターンチェンジ

            gObj.setTCount(gObj.getTCount() + 1); // ターンを１進める
            gObj.setPCount(0); // 置ける場所の初期化
        }
        initFlip.putPlace(); // 置ける場所に置ける画像を配置

        // 置く場所がなかったらスキップ処理
        if(gObj.getPCount() == 0){
            gObj.setSTurn(3 - gObj.getSTurn()); // ターンチェンジ
            //setWhichTurn(); // どちらのターンかテキストに描画
            computer.setHasTurnChange(true); // ターンチェンジしたことを保存
            initFlip.putPlace();
            if (gObj.getPCount() == 0) gObj.setSkipTurn(true); // 一人が置く場所がなくもう一人も置く場所がなかったら
        }

        initFlip.countAllWB(); // 石の数を数える

        setWBText(); // 各々の石の個数を記入

        // 終了判定
        if (gObj.getTCount() >= 60 || gObj.getSkipTurn()) { // 60ターン以上になるか、両方とも置けなかったら
            if (gObj.getWCount() < gObj.getBCount()) {
                resultText.setText("黒の勝ち");
            } else if (gObj.getWCount() > gObj.getBCount()) {
                resultText.setText("白の勝ち");
            } else if (gObj.getWCount() == gObj.getBCount()) {
                resultText.setText("引き分け");
            }
        }

    }

    class ViewHolder {
        ImageView imageView;
    }

    // BaseAdapter を継承した GridAdapter クラスのインスタンス生成
    class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int layoutId;

        public GridAdapter(Context context, int layoutId, List<String> imgList) {
            super();
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.layoutId = layoutId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String mFilepath = gObj.getImgList().get(position);

            ViewHolder holder;
            if (convertView == null) {
                // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
                convertView = inflater.inflate(layoutId, parent, false);
                // ViewHolder を生成
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            int id = getResources().getIdentifier(mFilepath, "drawable", getPackageCodePath());
            Drawable d = getResources().getDrawable(id);
            holder.imageView.setImageDrawable(d);

            return convertView;
        }

        @Override
        public int getCount() {
            // List<String> imgList の全要素数を返す
            return gObj.getImgList().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
