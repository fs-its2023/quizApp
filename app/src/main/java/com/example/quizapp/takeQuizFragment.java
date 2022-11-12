package com.example.quizapp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private Button btnTopRight, btnTopLeft, btnBottomRight, btnBottomLeft;
    private Button[] btnArray;
    private TextView txtCorrectRatio, txtJudge;
    private Button btnNext, btnExplain;
    private takeQuizPackActivity tqActivity;
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
        this.mainApp = tqActivity.getMainApplication();

        this.setOrder();
        String[] quizData = this.tqActivity.getLstPackIdFile().
                get(this.mainApp.getQuizNum()).split(",");

        /*
        * 画面表示 (問題文, 選択肢ボタン, 正解率)
         */
        TextView txtQuiz = view.findViewById(R.id.txtQuiz);
        txtQuiz.setText(quizData[0]);

        this.btnTopLeft = view.findViewById(R.id.btnTopLeft);
        this.btnTopRight = view.findViewById(R.id.btnTopRight);
        this.btnBottomLeft = view.findViewById(R.id.btnBottomLeft);
        this.btnBottomRight = view.findViewById(R.id.btnBottomRight);
        this.btnArray = new Button[]{this.btnTopLeft, this.btnTopRight, this.btnBottomLeft, this.btnBottomRight};
        for(int i = 0; i < this.btnArray.length; i++){
            this.btnArray[this.order.get(i)].setText(quizData[i + 1]);
            this.btnArray[this.order.get(i)].setOnClickListener(this);
            if(i == 0){
                this.btnArray[this.order.get(i)].setTag("correct");
            }else{
                this.btnArray[this.order.get(i)].setTag("wrong");
            }
        }

        this.txtCorrectRatio = view.findViewById(R.id.txtCorrectRatio);
        this.setRatio(this.tqActivity.getCorrectNum(), this.mainApp.getQuizNum());

        /*
        * その他のviewの設定
         */
        this.txtJudge = view.findViewById(R.id.txtJudge);
        this.btnNext = view.findViewById(R.id.btnNext);
        this.btnExplain = view.findViewById(R.id.btnExplain);
        if(this.mainApp.getQuizNum() == this.tqActivity.getLstPackIdFile().size() - 1){
            this.btnNext.setText("結果を見る");
        }

    }

    /*
    * @fn
    * 選択肢の表示順をランダムに決定するメソッド
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setOrder(){
        this.order = new ArrayList<>(Arrays.asList(0,1,2,3));
        Collections.shuffle(this.order);
    }

    public void setRatio(int correct, int completed){
        String ratio = correct + " / " + completed;
        this.txtCorrectRatio.setText(ratio);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        FragmentTransaction ft;
        FragmentManager fm = getParentFragmentManager();
        switch(view.getId()){
            case R.id.btnTopLeft:
            case R.id.btnTopRight:
            case R.id.btnBottomLeft:
            case R.id.btnBottomRight:
                /*
                * ボタン色付け, 無効化
                 */
                this.btnArray[this.order.get(0)].setBackgroundColor(Color.RED);
                this.btnArray[this.order.get(0)].setClickable(false);
                for(int i = 1; i < this.btnArray.length; i++){
                    this.btnArray[this.order.get(i)].setBackgroundColor(Color.BLUE);
                    this.btnArray[this.order.get(i)].setClickable(false);
                }

                /*
                * 正誤判定
                 */
                if(view.getTag() == "correct"){
                    this.txtJudge.setText("正解!");
                    this.tqActivity
                            .setCorrectNum(this.tqActivity.getCorrectNum() + 1);
                }else{
                    this.txtJudge.setText("不正解!");
                }
                this.txtJudge.setVisibility(View.VISIBLE);
                this.setRatio(this.tqActivity.getCorrectNum(), this.mainApp.getQuizNum() + 1);

                /*
                * 「次へ」「解説」ボタンの有効化
                 */
                this.btnNext.setVisibility(View.VISIBLE);
                this.btnNext.setOnClickListener(this);
                this.btnExplain.setVisibility(View.VISIBLE);
                this.btnExplain.setOnClickListener(this);

                break;
            /*
             * 次へボタンが押された時の処理
             */
            case R.id.btnNext:
                if(this.mainApp.getQuizNum() < this.tqActivity.getLstPackIdFile().size() - 1){
                    this.mainApp.setQuizNum(this.mainApp.getQuizNum() + 1);
                    ft = fm.beginTransaction();
                    ft.replace(R.id.container, new takeQuizFragment());
                }else{
                    ft = fm.beginTransaction();
                    ft.addToBackStack(null);
                    ft.replace(R.id.container, new resultFragment());
                }
                ft.commit();
                break;
            /*
            * 解説表示ボタンが押された時の処理
             */
            case R.id.btnExplain:
                ft = fm.beginTransaction();
                ft.addToBackStack(null);
                ft.replace(R.id.container, new showExplanationFragment());
                ft.commit();
                break;
        }
    }
}
