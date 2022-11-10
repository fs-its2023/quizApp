package com.example.quizapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//takequiz
public class takeQuizFragment extends Fragment {

    private List<Integer> order;
    /*
    *takeQuizPackActivity内変数
    */
    private takeQuizPackActivity tqActivity;
    private MainActivity mainActivity;
    private int correctNum;
    /*
    *mainApplication内変数
    */
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

        //外部クラスのデータ取得
        this.tqActivity = (takeQuizPackActivity)getActivity();
        this.mainActivity=(MainActivity)getActivity();
        this.correctNum = this.tqActivity.getCorrectNum();
        this.mainApp = (mainApplication) mainActivity.getMainApplication();
        this.packId = this.mainApp.getPackId();
        this.quizNum = this.mainApp.getQuizNum();

        //クイズ画面を表示
        this.setLayout();

    }

    /*
    * @fn
    * packIdファイルからquizNum行目を読み込むメソッド
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setLayout(){
        /*
        選択肢の表示順を決定
         */
        this.setOrder();

        /*
        * dataにファイルのquizNum行目を読み込み
        * data = [問題文, 正答, 誤答1, 誤答2, 誤答3]
         */
        String[] data = this.tqActivity.getLstPackIdFile().get(this.mainApp.getQuizNum()).split(",");

        /*
        * 画面を表示 (後日layoutと一緒に作成
         */
    }

    /*
    * @fn
    * 選択肢の表示順をランダムに決定するメソッド
     */
    public void setOrder(){
        this.order = new ArrayList<>(Arrays.asList(1,2,3,4));
        Collections.shuffle(this.order);
    }

    public void OnclickAnswer(){
        //正解の場合の処理

        //不正解の場合の処理

    }

    /*
     * @fn
     * 「解説を表示」ボタンが押された時に実行するメソッド
     */
    public void onclickShowExplanation(){
        /*
        * このFragmentをスタックし解説Fragment起動
         */
        LinearLayout layout = this.tqActivity.getLinearLayout();
        FragmentManager manager = this.tqActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(layout.getId(), new showExplanationFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /*
    * @fn
    * 「次の問題へ」ボタンが押された時に実行するメソッド
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickNextQuiz(){
        /*
        * 問題番号のインクリメント
         */
        this.mainApp.setQuizNum(this.mainApp.getQuizNum()+1);

        /*
        * fragmentの再読み込み
         */
    }

}

/*
* 残り作業
* layout作成
* setLayout
* 選択肢選択時処理
* fragment reload
 */