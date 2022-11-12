package com.example.quizapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class takeQuizPackActivity extends AppCompatActivity {

    private int correctNum;
    private FragmentTransaction transaction;
    private List<String> lstPackIdFile;
    private mainApplication mainApp;

    /*
    * @fn
    * Activity起動時, パック選択画面を表示するメソッド
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz_pack);

        /*
        * 画面遷移用transaction初期化
         */
        transaction = getSupportFragmentManager().beginTransaction();

        /*
        * mainApplicationクラスの取得
         */
        this.mainApp = (mainApplication)getApplication();

        /*
        * メニューボタン作成
         */
        Button btnMenu = findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> finish());

        /*
        * パック選択画面起動
         */
        this.mainApp.setSelectPack(true);   //select省略
        if(mainApp.getSelectPack()){
            this.takeQuiz();

        }else{
            this.selectPack();
        }
    }

    /*
    * @fn
    * パック選択画面を表示するメソッド
     */
    public void selectPack(){
        Intent intent =new Intent(getApplication(), selectPackActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    * @fn
    * クイズ回答画面を表示するメソッド
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void takeQuiz(){

        /*
        * クイズ初期化
         */
        this.correctNum = 0;
        //this.lstPackIdFile = this.mainApp.readFileAsList(this.mainApp.getPackId());
        this.lstPackIdFile = new ArrayList<>(Arrays.asList("q1,a1,b1,c1,d1,ex1",
                "q2,a2,b2,c2,d2,ex2",
                "q3,a3,b3,c3,d3,exq3"));

        /*
        * クイズ回答画面表示
         */
        takeQuizFragment takeQuizFragment = new takeQuizFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, takeQuizFragment);
        transaction.commit();
    }

    //結果を表示するメソッド (不要かも)
    /*public void result(){
        resultFragment resultFragment = new resultFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, resultFragment);
        transaction.commit();
    }*/

    //遷移先のFragmentからメニューボタンを再表示させるメソッド
    @SuppressLint("ResourceType")
    public LinearLayout getLinearLayout() {
        LinearLayout layout=new LinearLayout(this);
        layout.setId(0);
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



    /*
    * correctNumのgetterとsetter
     */
    public int getCorrectNum() {
        return this.correctNum;
    }
    public void setCorrectNum(int correctNum){
        this.correctNum = correctNum;
    }

    /*
    * lstPackIdFileのgetter
     */
    public List<String> getLstPackIdFile(){
        return this.lstPackIdFile;
    }

    public mainApplication getMainApplication(){
        return mainApp;
    }

}