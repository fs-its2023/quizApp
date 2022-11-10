package com.example.quizapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MakePackActivity extends AppCompatActivity {

    private String packTitle;
    private String packGenre;
    private String packIntroduction;
    private int quizTotalNum;

    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_pack);

        //画面遷移用transaction初期化
        transaction = getSupportFragmentManager().beginTransaction();

        //メニューボタン作成
        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> finish());

        //新規作成のボタンを押したときの処理
        Button btnMakeNewQuiz = findViewById(R.id.btnMakeNewQuiz);
        btnMakeNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNewPack();
            }
        });

        //編集のボタンを押したときの処理 
        Button btnEditQuiz = findViewById(R.id.btnEditQuiz);
        btnEditQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPack();
            }
        });
    }

    //編集ボタンを押した後の動作、selectPackFragmentを開く
    public void selectPack(){
        packTitle=null;
        packGenre=null;
        packIntroduction=null;
        selectPackFragment selectPackFragment = new selectPackFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, selectPackFragment);
        transaction.commit();
    }

    //新規作成ボタンを押した後の動作、makeNewPackFragmentを開く
    public void makeNewPack(){
        makeNewPackFragment makeNewPackFragment = new makeNewPackFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, makeNewPackFragment);
        transaction.commit();
        packTitle=null;
        packGenre=null;
        packIntroduction=null;
    }

    //遷移先のFragmentからメニューボタンを再表示させるメソッド
    public LinearLayout getLinearLayout() {
        LinearLayout layout=new LinearLayout(this);
        //layout.setId(101);
        //垂直方向にViewを追加していく
        layout.setOrientation(LinearLayout.VERTICAL);
        //layoutの幅、高さの設定
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));


        Button button = new Button(this);
        button.setTextSize(10);
        button.setText("メニュー");
        button.setOnClickListener(backMainActivity);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(buttonLayoutParams);
        layout.addView(button);

        return layout;
    }
    //再作成したメニューボタンの処理
    View.OnClickListener backMainActivity = new View.OnClickListener(){
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View v){
            finish();
        }
    };

    //フィールドのセッター、ゲッター
    public void setPackTitle(String packTitle){
        this.packTitle = packTitle;
    }

    public String getPackTitle(){
        return this.packTitle;
    }

    public void setPackGenre(String packGenre){
        this.packGenre = packGenre;
    }

    public String getPackGenre(){
        return this.packGenre;
    }

    public void setPackIntroduction(String packIntroduction){
        this.packIntroduction = packIntroduction;
    }

    public String getPackIntroduction(){
        return this.packIntroduction;
    }

    public void setQuizTotalNum(int quizTotalNum){
        this.quizTotalNum = quizTotalNum;
    }

    public int getQuizTotalNum(){
        return this.quizTotalNum;
    }
}
