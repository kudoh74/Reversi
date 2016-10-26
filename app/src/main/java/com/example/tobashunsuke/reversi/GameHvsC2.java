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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GameHvsC2 extends AppCompatActivity {
    private GridAdapter adapter;

    private InitFlip initFlip = new InitFlip();
    private GameObject gObj = new GameObject();
    private Computer computer = new Computer();

    private Button returnGameMode;
    private Button afterAttack;

    private Button boardClear;
    private TextView whichTurn;
    private TextView resultText;
    private TextView w_bText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_h_vs_c2);

        returnGameMode = (Button) findViewById(R.id.return_gamemode2);
        afterAttack = (Button) findViewById(R.id.after_attack2);

        boardClear = (Button) findViewById(R.id.button_clear2); // 盤のクリア
        whichTurn = (TextView) findViewById(R.id.textView_turn2); // どちらのターンか表示
        resultText = (TextView) findViewById(R.id.textView_result2); // 結果の表示
        w_bText = (TextView) findViewById(R.id.textView_wb2); // 白と黒の数の表示

        resultText.setText("");
        setWBText();

        // GridViewのインスタンスを生成
        final GridView gridview = (GridView) findViewById(R.id.gridview2);
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

        computer.initCom2List(); // 重み付けのリストの初期化


        // gridviewをタップしたとき
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (initFlip.canPut(position) && gObj.getImgList().get(position).equals(gObj.getPiece(3))) { // 置くことができ、置ける画像の場所なら

                    for (int i = 0; i <= 1; i++) {

                        if (i == 0) {
                            clickAndCom(position);
                            adapter.notifyDataSetChanged();
                        } else if(computer.getComOrder1() == gObj.getSTurn()){
                            Log.e("onclick", "through : 33333");/*
                            for(int j = 0; j < 8; j++) {
                                gObj.getImgList().set(8 * j, gObj.getPiece(2));
                                gObj.getImgList().set((8 * j) + 1, gObj.getPiece(1));
                                gObj.getImgList().set((8 * j) + 2, gObj.getPiece(0));
                                gObj.getImgList().set((8 * j) + 3, gObj.getPiece(0));
                                gObj.getImgList().set((8 * j) + 4, gObj.getPiece(0));
                                gObj.getImgList().set((8 * j) + 5, gObj.getPiece(0));
                                gObj.getImgList().set((8 * j) + 6, gObj.getPiece(0));
                            }*/
                            while (computer.getComOrder1() == gObj.getSTurn()) {
                                initFlip.putPlace();// 置ける場所に置ける画像を配置
                                position = computer.com2AI();

                                if (position == -1) { // エラー、置く場所がない
                                    gObj.setSTurn(3 - gObj.getSTurn()); // ターンチェンジ
                                    computer.setHasTurnChange(true); // ターンチェンジしたことを保存
                                    setWhichTurn();
                                    Log.e("onclick", "through : 3");
                                    break;
                                }
                                clickAndCom(position);
                                Log.d("onclick", String.valueOf(position));
                                adapter.notifyDataSetChanged();
                                Log.d("onclick", "through : 1");
                                //computer.waitTime();
                            }
                        }

                    }
                }

                if (computer.getHasTurnChange()) {
                    setWhichTurn(); // ないとパスした時に相手のターンが表示される
                    computer.setHasTurnChange(false);
                    Log.e("onclick", "through : 2");
                    initFlip.putPlace();// 置ける場所に置ける画像を配置
                }
            }

        });

        boardClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // クリアボタンを押したら
                if (v == boardClear) {
                    initFlip.initBoard(); // 盤の初期化
                    initGui();
                    computer.setComOrder1(2); // CPUを後攻に

                    for(int i = 0; i < 5; i++) {/*
                        gObj.getImgList().set(8 * i, gObj.getPiece(2));
                        gObj.getImgList().set((8 * i) + 1, gObj.getPiece(1));
                        gObj.getImgList().set((8 * i) + 2, gObj.getPiece(1));
                        gObj.getImgList().set((8 * i) + 3, gObj.getPiece(0));
                        gObj.getImgList().set((8 * i) + 4, gObj.getPiece(0));
                        gObj.getImgList().set((8 * i) + 5, gObj.getPiece(1));
                        gObj.getImgList().set((8 * i) + 6, gObj.getPiece(1));*/
                    }
                    //gObj.getImgList().set(34, gObj.getPiece(2));
                    //gObj.getImgList().set(35, gObj.getPiece(0));

                    initFlip.putPlace();// 置ける場所に置ける画像を配置
                    adapter.notifyDataSetChanged(); // 画面更新
                    computer.setHasTurnChange(false);
                }

            }
        });

        afterAttack.setOnClickListener(new View.OnClickListener() { // 後攻にする
            public void onClick(View v) {
                //adapter.notifyDataSetChanged();
                if(v == afterAttack) {
                    int position;
                    initFlip.initBoard();
                    computer.setComOrder1(1); // 後攻にする
                    initGui();

                    position = computer.com2AI();
                    clickAndCom(position);
                    computer.setHasTurnChange(false); // ターンチェンジしたことを保存

                    adapter.notifyDataSetChanged();
                }
            }
        });

        returnGameMode.setOnClickListener(new View.OnClickListener() { // GameModeクラスに画面遷移
            public void onClick(View v) {
                if(v == returnGameMode) {
                    finish();
                }
            }
        });
    }

    // guiの処理
    private void setWhichTurn(){
        if (gObj.getSTurn() == 1) {
            whichTurn.setText("黒のターン");
        } else {
            whichTurn.setText("白のターン");
        }
    }
    private void setWBText(){
        w_bText.setText(String.format("白:%d - 黒:%d", gObj.getWCount(), gObj.getBCount()));
    }
    private void initGui(){
        resultText.setText(""); // 結果を消す
        initFlip.putPlace(); // 置ける場所を表示
        setWhichTurn();


        setWBText();
    }

    private void clickAndCom(int position){

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
