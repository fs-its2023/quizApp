package com.example.quizapp;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class takeQuizFragment extends Fragment implements View.OnClickListener {

    private List<Integer> order;
    private String[] quizData;

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

        this.setOrder();
        this.readQuizData();

        /*
        * 画面表示 (問題文, 選択肢ボタン)
         */
        TextView txtQuiz = view.findViewById(R.id.txtQuiz);
        txtQuiz.setText(this.quizData[0]);

        Button btnTopRight, btnTopLeft, btnBottomRight, btnBottomLeft;
        btnTopLeft = view.findViewById(R.id.btnTopLeft);
        btnTopRight = view.findViewById(R.id.btnTopRight);
        btnBottomLeft = view.findViewById(R.id.btnBottomLeft);
        btnBottomRight = view.findViewById(R.id.btnBottomRight);
        Button[] btnArray = {btnTopLeft, btnTopRight, btnBottomLeft, btnBottomRight};
        for(int i = 0; i < btnArray.length; i++){
            btnArray[this.order.get(i)].setText(this.quizData[i + 1]);
            btnArray[this.order.get(i)].setTag(i);
        }

    }

    /*
    * @fn
    * packIdファイルからquizNum行目を読み込むメソッド
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void readQuizData(){
        /*
        選択肢の表示順を決定
         */
        this.setOrder();

        /*
        * dataにファイルのquizNum行目を読み込み
        * quizData = [問題文, 正答, 誤答1, 誤答2, 誤答3, 解説]
         */
        this.quizData = this.tqActivity.getLstPackIdFile().get(this.mainApp.getQuizNum()).split(",");
    }

    /*
    * @fn
    * 選択肢の表示順をランダムに決定するメソッド
     */
    public void setOrder(){
        this.order = new ArrayList<>(Arrays.asList(1,2,3,4));
        Collections.shuffle(this.order);
    }

    @SuppressLint("ResourceType")
    public void OnclickAnswer(View view) {
        /*
        * 正誤判定
        * ボタンTAGは左上0, 右上1, 左下2, 右下3
        * order = [正答のTAG, 誤答1のTAG, 誤答2のTAG, 誤答3のTAG]
         */
        if((int)view.getTag() == 0){
            //「正解」表

            /*
            + 正解数更新
             */
            this.tqActivity.setCorrectNum(this.tqActivity.getCorrectNum() + 1);
        }else{
            //不正解表示
        }

        /*
        * ボタンに色でも付ける?
         */

        /*
        * 「次の問題へ」「解説へ」ボタン作成
         */
        LinearLayout layout=tqActivity.getLinearLayout();

        Button btnNext = new Button(tqActivity);
        btnNext.setText("次の問題");
        btnNext.setTextSize(30);
        btnNext.setTag("Next");
        btnNext.setOnClickListener(this);
        LinearLayout.LayoutParams buttonNextParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnNext.setLayoutParams(buttonNextParams);
        layout.addView(btnNext);

        Button btnShowExplanation = new Button(tqActivity);
        btnShowExplanation.setText("次の問題");
        btnShowExplanation.setTextSize(30);
        btnNext.setTag("Explain");
        btnShowExplanation.setOnClickListener(this);
        LinearLayout.LayoutParams buttonExplainParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        btnNext.setLayoutParams(buttonExplainParams);
        layout.addView(btnShowExplanation);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        if (view.getTag() == "Next") {
            /*
             * 問題番号のインクリメント
             */
            this.mainApp.setQuizNum(this.mainApp.getQuizNum()+1);

            this.tqActivity.reloadQuiz();

        } else if (view.getTag() == "Explain") {
            /*
             * このFragmentをスタックし解説Fragment起動
             */
            LinearLayout layout = this.tqActivity.getLinearLayout();
            FragmentManager manager = this.tqActivity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(layout.getId(), new showExplanationFragment());
            transaction.addToBackStack(null);
            transaction.commit();

            /*
            getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentcontainer, new ***Fragment)
                .addToBackStack(null)
                .commit();
             */
        }
    }


}
