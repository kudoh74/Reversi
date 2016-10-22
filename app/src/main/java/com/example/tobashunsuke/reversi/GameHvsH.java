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

public class GameHvsH extends AppCompatActivity {
    GridAdapter adapter;

    private final int MAXBOARD = 63; //  リストの格納数0〜63
    private final int MINBOARD = 0; //  リストの格納数0〜63

    private InitFlip initFlip = new InitFlip();
    private GameObject gObj = new GameObject();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_h_vs_h);

        final Button returnGameMode = (Button) findViewById(R.id.return_gamemode);

        final Button boardClear = (Button) findViewById(R.id.button_clear); // 盤のクリア
        final TextView whichTurn = (TextView) findViewById(R.id.textView_turn); // どちらのターンか表示
        final TextView resultText = (TextView) findViewById(R.id.textView_result); // 結果の表示
        final TextView w_bText = (TextView) findViewById(R.id.textView_wb); // 白と黒の数の表示
        resultText.setText("");
        w_bText.setText(String.format("白:%d - 黒:%d", gObj.getWCount(), gObj.getBCount()));

        // GridViewのインスタンスを生成
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        // BaseAdapter を継承したGridAdapterのインスタンスを生成
        // 子要素のレイアウトファイル grid_items.xml を activity_main.xml に inflate するためにGridAdapterに引数として渡す
        adapter = new GridAdapter(this.getApplicationContext(), R.layout.grid_items, gObj.getImgList());
        // gridViewにadapterをセット
        gridview.setAdapter(adapter);

        // /assets/image/以下に画像を入れています
        // それのパスを取り出す method
        initFlip.initBoard();

        initFlip.putPlace();// 置ける場所に置ける画像を配置

        if (gObj.getSTurn() == 1) {
            whichTurn.setText("黒のターン");
        } else {
            whichTurn.setText("白のターン");
        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // gridview更新？
                adapter = new GridAdapter(getApplicationContext(), R.layout.grid_items, gObj.getImgList());
                gridview.setAdapter(adapter);

                initFlip.putPlace(); // 置ける場所に置ける画像を配置

                if (initFlip.canPut(position) && !initFlip.isDuplicate(position)) { // 置くことができ、一回打ったところでない

                    if (gObj.getSTurn() == 1) {
                        whichTurn.setText("白のターン");
                        gObj.getImgList().set(position, gObj.getPiece(1)); // 画像を変える
                    } else {
                        whichTurn.setText("黒のターン");
                        gObj.getImgList().set(position, gObj.getPiece(2)); // 画像を変える
                    }
                    initFlip.pBetween(position); // 返す処理
                    gObj.setSTurn(3 - gObj.getSTurn()); // ターンチェンジ

                    gObj.setTCount(gObj.getTCount() + 1);
                    gObj.setPCount(0); // 置ける場所の初期化
                }
                initFlip.putPlace(); // 置ける場所に置ける画像を配置
                //Log.e("onclick", String.valueOf(gObj.getSkipTurn()));

                // 置く場所がなかったらスキップ処理
                if(gObj.getPCount() == 0){
                    gObj.setSTurn(3 - gObj.getSTurn()); // ターンチェンジ
                    if (gObj.getSTurn() == 1) {
                        whichTurn.setText("黒のターン");
                    } else {
                        whichTurn.setText("白のターン");
                    }
                    initFlip.putPlace();
                    if (gObj.getPCount() == 0) gObj.setSkipTurn(true); // 一人が置く場所がなくもう一人も置く場所がなかったら
                    // Log.d("onClick", "関数 canPut は「         " + skipTurn + "       」");
                }
                //Log.e("onclick", String.valueOf(gObj.getSkipTurn()));

                gObj.setWCount(0);
                gObj.setBCount(0);

                for (int i = MINBOARD; i <= MAXBOARD; i++) { // 現状の石の数を数える
                    if (gObj.getImgList().get(i).equals(gObj.getPiece(1))) { // 黒の数
                        gObj.setBCount(gObj.getBCount() + 1);
                    } else if (gObj.getImgList().get(i).equals(gObj.getPiece(2))) { // 白の数
                        gObj.setWCount(gObj.getWCount() + 1);
                    }
                }

                w_bText.setText(String.format("白:%d - 黒:%d", gObj.getWCount(), gObj.getBCount())); // 各々の石の個数を記入

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
        });

        boardClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // gridview更新？
                adapter = new GridAdapter(getApplicationContext(), R.layout.grid_items, gObj.getImgList());
                gridview.setAdapter(adapter);

                // クリアボタンを押したら
                if (v == boardClear) {
                    initFlip.initBoard(); // 盤の初期化
                    resultText.setText(""); // 結果を消す
                    initFlip.putPlace(); // 置ける場所を表示
                    if (gObj.getSTurn() == 1) {
                        whichTurn.setText("黒のターン");
                    } else {
                        whichTurn.setText("白のターン");
                    }
                    w_bText.setText(String.format("白:%d - 黒:%d", gObj.getWCount(), gObj.getBCount()));

                }
                //Log.d("initBoard", String.valueOf(gObj.getBCount()));
            }
        });

        returnGameMode.setOnClickListener(new View.OnClickListener() { // GameModeクラスに画面遷移
            public void onClick(View v) {
                if(v == returnGameMode) {
                    finish();
                }
                //Log.d("initBoard", String.valueOf(gObj.getBCount()));
            }
        });


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
