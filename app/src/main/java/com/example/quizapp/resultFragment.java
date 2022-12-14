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

public class resultFragment extends Fragment {

    /*フィールド変数の宣言*/
    takeQuizPackActivity tqActivity;
    mainApplication mainApplication;
    int packNum;
    int quizNumProblem;
    String packId;
    TextView resultText;
    LinearLayout resultLayout;
    String[] listData=new String[6];
    List<String> allList=new ArrayList<>();
    Button backButton;




    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        /*追加内容*/
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        resultText=view.findViewById(R.id.resultText);
        resultLayout=view.findViewById(R.id.resultVerticalLayout);
        backButton=view.findViewById(R.id.backButton);
        return view;
        //return inflater.inflate(R.layout.fragment_result,container,false);
    }


    //フラグメントが生成されたときに行う処理、主に結果の表示とボタンの生成

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //フィールド変数に値を代入
        tqActivity=(takeQuizPackActivity)getActivity();  //Activityをインスタンス化して、Activityのメソッドが使えるようにする
        mainApplication = (com.example.quizapp.mainApplication) tqActivity.getMainApplication();
        packNum = mainApplication.getPackNum();
        packId=mainApplication.getPackId();
        this.backButton.setOnClickListener(backButtonListener);


        //Applicationクラスにあるパックの情報をリストに代入
        //allList= mainApplication.readFileAsList(mainApplication.PACK_DATA_FILE_NAME);
        allList=mainApplication.getAllList();

        //配列に値を代入
        setListData(allList);

        //問題数をint型に変換
        quizNumProblem=toListDataQuizNumProblems(listData[2]);

        //最終結果の表示とボタンの生成を行う
        createView(quizNumProblem);
    }


    //リストの情報を","区切りでパックの情報を入れる配列に代入するメソッド
    public void setListData(List<String> allList){
        listData=allList.get(packNum).split(",");
    }


    //問題数をint型に変換するメソッド
    public int toListDataQuizNumProblems(String listData){
        int change=Integer.parseInt(listData);
        return change;
    }


    //Viewの作成
    @SuppressLint("ResourceType")
    public void createView(int quizNumProblem){
        resultText.setText("最終結果："+tqActivity.getCorrectNum()+"/"+quizNumProblem);


        //ボタンの生成
        Button commentaryButton;
        for(int i=1;i<=quizNumProblem;i++){
            //ボタンの設定
            commentaryButton = new Button(tqActivity);
            commentaryButton.setText("問題番号"+i+"番");
            commentaryButton.setTextSize(30);
            commentaryButton.setTag(i-1);
            commentaryButton.setOnClickListener(createShowExplanationFragment);
            //ボタンの幅、高さの設定
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            commentaryButton.setLayoutParams(buttonLayoutParams);
            //ボタンの表示
            resultLayout.addView(commentaryButton);
        }

    }

    //ボタンが押された時の処理
    View.OnClickListener createShowExplanationFragment =new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            mainApplication.setQuizNum((int)view.getTag());  //押されたボタンのタグをint型に変換し問題番号を入れている変数に渡す

            mainApplication.setFromTakeQuizFragment(false);

            // フラグメントマネージャーの取得
            //FragmentManager manager = getFragmentManager(); // アクティビティではgetSupportFragmentManager()?
            FragmentManager manager =getParentFragmentManager();
            // フラグメントトランザクションの開始
            FragmentTransaction transaction = manager.beginTransaction();
            // レイアウトをfragmentに置き換え（追加）
            transaction.replace(R.id.container, new showExplanationFragment());
            // 置き換えのトランザクションをバックスタックに保存する
            transaction.addToBackStack(null);
            // フラグメントトランザクションをコミット
            transaction.commit();
        }
    };

    View.OnClickListener backButtonListener =new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            mainApplication.setMustSelectPack(true);
            mainApplication.setFromTakeQuizPackActivity(true);
            tqActivity.selectPack();
        }
    };
}


