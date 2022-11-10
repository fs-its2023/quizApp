package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// ヒサダ追加
import android.annotation.SuppressLint;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    mainApplication mainApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainApplication=(mainApplication)getApplication();

        //activity遷移ボタン
        Button button0 = findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), takeQuizPackActivity.class);
                startActivity(intent);
            }
        });


        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // スイッチ(トグル)がオンの時
                } else {
                    // スイッチ(トグル)がオフの時
                }
            }
        });
    }

    // パック編集へ遷移
    public void onClickMakePack() {
        Intent intent = new Intent(getApplication(), MakePackActivity.class);
        startActivity(intent);
    }

    // クイズ回答へ遷移
    public void onClickTakeQuiz() {
        Intent intent = new Intent(getApplication(), takeQuizPackActivity.class);
        startActivity(intent);
    }

    // スイッチでミュートの切り替えをする
    public void onClickMute() {

    }

    public Application getMainApplication(){
        return mainApplication;
    }
}