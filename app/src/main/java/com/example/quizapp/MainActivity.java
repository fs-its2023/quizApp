package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/// ヒサダ追加
// スイッチのimport
import android.annotation.SuppressLint;
import android.widget.CompoundButton;
import android.widget.Switch;

// 音楽再生のimport
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.content.res.AssetFileDescriptor;
import android.widget.Toast;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    mainApplication mainApplication;

    private MediaPlayer mediaPlayer;

    boolean isisChecked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainApplication=(mainApplication)getApplication();

        //activity遷移ボタン
        Button button0 = findViewById(R.id.buttonTakeQuiz);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), takeQuizPackActivity.class);
                startActivity(intent);
            }
        });

    }

    //--- パック編集へ遷移
    public void onClickMakePack() {
        Intent intent = new Intent(getApplication(), makePackActivity.class);
        startActivity(intent);
    }

    //--- クイズ回答へ遷移
    public void onClickTakeQuiz() {
        Intent intent = new Intent(getApplication(), takeQuizPackActivity.class);
        startActivity(intent);
    }

    //--- ミュート処理
    public void onClickMute() {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch sw = findViewById(R.id.switch1);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // スイッチ(トグル)がオンの時
                    audioStop();
                } else {
                    // スイッチ(トグル)がオフの時　
                    audioPlay();
                }
            }
        });
    }

    // MediaPlayer関係
    private boolean audioSetup(){
        // インタンスを生成
        mediaPlayer = new MediaPlayer();

        //音楽ファイル名, あるいはパス
        String filePath = "music.mp3";

        boolean fileCheck = false;

        // assetsから mp3 ファイルを読み込み
        try(AssetFileDescriptor afdescripter = getAssets().openFd(filePath))
        {
            // MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());
            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            fileCheck = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return fileCheck;
    }

    private void audioPlay() {

        if (mediaPlayer == null) {
            // audio ファイルを読出し
            if (audioSetup()){
                Toast.makeText(getApplication(), "Rread audio file", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplication(), "Error: read audio file", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        else{
            // 繰り返し再生する場合
            mediaPlayer.stop();
            mediaPlayer.reset();
            // リソースの解放
            mediaPlayer.release();
        }

        // 再生する
        mediaPlayer.start();

        // 終了を検知するリスナー
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                Log.d("debug","end of audio");
//                audioStop();
//            }
//        });
        // lambda
        mediaPlayer.setOnCompletionListener( mp -> {
            Log.d("debug","end of audio");
            audioStop();
        });

    }

    private void audioStop() {
        // 再生終了
        mediaPlayer.stop();
        // リセット
        mediaPlayer.reset();
        // リソースの解放
        mediaPlayer.release();

        mediaPlayer = null;
    }

    public Application getMainApplication(){
        return mainApplication;
    }
}