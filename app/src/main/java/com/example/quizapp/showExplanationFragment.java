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
import java.util.List;

public class showExplanationFragment extends Fragment {
    /*
    *フィールド変数の宣言
     */
    takeQuizPackActivity tqActivity;
    mainApplication mainApplication;
    int quizNum;
    String packId;
    String[] quizData=new String[6];
    List<String> quizList=new ArrayList<>();
    TextView problemStatement;
    TextView explanation;
    TextView correctAnswer;
    Button backFragment;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_show_explanation,container,false);
        problemStatement=view.findViewById(R.id.problemStatement);
        explanation=view.findViewById(R.id.explanation);
        correctAnswer=view.findViewById(R.id.correctAnswer);
        backFragment=view.findViewById(R.id.closeShowExplanation);
        return view;
    }

    /*
    *フィールド変数に値を代入、Viewの生成
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        /*
        *フィールド変数に値を代入
         */

        /*
        *takeQuizPackActivityのメソッドをつくるためにインスタンス化
         */
        tqActivity=(takeQuizPackActivity)getActivity();

        /*
        *mainApplicationを使えるようにActivityからApplicationを受け取る
         */
        mainApplication= (com.example.quizapp.mainApplication) tqActivity.getMainApplication();

        /*
        *パックIDをmainApplicationから受け取る
         */
        packId =mainApplication.getPackId();

        /*
        *パックIDに該当するファイルを読み込み各行をリストとして受け取る
         */
        quizList=mainApplication.readFileAsList(packId);

        /*
        *mainApplicationの問題番号を受け取る
         */
        quizNum=mainApplication.getQuizNum();

        /*
        *クイズが入っているリストを","区切りで配列に代入
         */
        setQuizData(quizList);

        /*
        *Viewの生成
         */
        createView();

    }


    /*
    *Viewの生成をするメソッド
     */
    @SuppressLint("ResourceType")
    public void createView(){
        problemStatement.setText("問題文\n"+ quizData[0]);
        explanation.setText("解説\n"+ quizData[5]);
        correctAnswer.setText("正解の選択肢\n"+ quizData[1]);
        backFragment.setOnClickListener(this::onClick);
    }

    /*
    *ボタンが押された時の処理
     */
    public void onClick(View v){
        getFragmentManager().popBackStack();
    }

    /*
    *選ばれた問題番号の問題文、解説、選択しを入れておく配列に値を入れる
     */
    public void setQuizData(List<String> list) {
        quizData = list.get(quizNum).split(",");
    }
}
