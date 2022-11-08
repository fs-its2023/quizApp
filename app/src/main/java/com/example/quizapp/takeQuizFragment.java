package com.example.quizapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

//takequiz
public class takeQuizFragment extends Fragment {

    private int[] order={1,2,3,4};
    //takeQuizPackActivity内変数
    private takeQuizPackActivity tqActivity;
    private int correctNum;
    //mainApplication内変数
    private mainApplication mainApp;
    private String packId;
    private int quizNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        super.onCreateView(inflater, container, savedInstance);
        return inflater.inflate(R.layout.fragment_take_quiz, container, false);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //ファイル読み込み用変数
        List<String> lines = new ArrayList<>();

        String[] quizData;

        //外部クラスのデータ取得
        this.tqActivity = (takeQuizPackActivity)getActivity();
        this.correctNum = this.tqActivity.getCorrectNum();
        this.mainApp = tqActivity.getMainApplication();
        this.packId = this.mainApp.getPackId();
        this.quizNum = mainApp.getQuizNum();

        //ファイル読み込み
        lines = mainApp.readFileAsList(packId);
        //quizData = readQuizData(this.quizNum);

        //問題の表示
    }

    /*
    public String[] readQuizData(quizNum){

    }*/

    public void OnclickAnswer(){
        //正解の場合の処理

        //不正解の場合の処理

    }

    public void onclickShowExplanation(){

    }

    public void onClickNextQuiz(){
        this.quizNum = this.quizNum + 1;


    }

}
