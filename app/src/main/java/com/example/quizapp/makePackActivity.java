package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class makePackActivity extends AppCompatActivity {

    private String packTitle;
    private String packGenre;
    private String packIntroduction;
    private int quizTotalNum;
    private static mainApplication mainApplication;

    //↓あるパックに含まれる全クイズデータを入れるリスト。↑のquizTotalNumいらなくね？
    private List<String> quizData = new ArrayList<String>();

<<<<<<< HEAD

=======
>>>>>>> sasa
    private Button btnMakeNewQuiz;
    private Button btnEditQuiz;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_pack);

        mainApplication=(mainApplication)this.getApplication();

        //画面遷移用transaction初期化
        transaction = getSupportFragmentManager().beginTransaction();

        //メニューボタン作成
        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> finish());

        //新規作成のボタンを押したときの処理
        btnMakeNewQuiz = findViewById(R.id.btnMakeNewQuiz);
        btnMakeNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeNewPack();
            }
        });

        //編集のボタンを押したときの処理 
        btnEditQuiz = findViewById(R.id.btnEditQuiz);
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
        quizTotalNum=0;
        Intent intent=new Intent(getApplication(), selectPackActivity.class);
        startActivity(intent);
    }

    //新規作成ボタンを押した後の動作、makeNewPackFragmentを開く。
    public void makeNewPack(){
        packTitle=null;
        packGenre=null;
        packIntroduction=null;
        quizTotalNum=0;
        btnEditQuiz.setVisibility(View.GONE);
        btnMakeNewQuiz.setVisibility(View.GONE);
//        makeNewPackFragment makeNewPackFragment = new makeNewPackFragment();
        editQuizFragment editQuizFragment = new editQuizFragment();
        transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.container, makeNewPackFragment);
        transaction.replace(R.id.container, editQuizFragment);
        transaction.commit();
    }



    public void reload() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    //フィールドのセッターとゲッター
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

    public mainApplication getMainApplication(){
        return mainApplication;
    }

    public void setQuizData(List<String> inputQuizData){
        this.quizData=inputQuizData;
    }

    public List<String> getQuizData(){
        return this.quizData;
    }
}

