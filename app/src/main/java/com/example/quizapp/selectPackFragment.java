package com.example.quizapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class selectPackFragment extends Fragment {
    /*
    *ActivityとApplicationの定義
     */
    mainApplication mainApplication;
    takeQuizPackActivity takeQuizPackActivity;
    com.example.quizapp.makePackActivity makePackActivity;
    MainActivity mainActivity;

    /*
    *フィールド編巣の定義
     */
    List<String> allList=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        super.onCreateView(inflater, container, savedInstance);
        return inflater.inflate(R.layout.fragment_select_pack, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Activityのインスタンス化*/
        mainActivity=(MainActivity)getActivity();
        mainApplication= (com.example.quizapp.mainApplication) mainActivity.getMainApplication();
        if(mainApplication.getFromMakePackActivity()){
            /*makePackActivityのインスタンス化*/
            makePackActivity=(com.example.quizapp.makePackActivity) getActivity();
            createView(makePackActivity);
        }
        if(mainApplication.getFromTakeQuizPackActivity()){
            /*takeQuizPackActivityのインスタンス化*/
            takeQuizPackActivity=(com.example.quizapp.takeQuizPackActivity) getActivity();
            createView(takeQuizPackActivity);
        }
    }


    /*
    *Viewの生成
     */
    @SuppressLint("ResourceType")
    public void createView(Activity activity){

        /*
        *スクロールバーの設定・表示
         */
        ScrollView scrollView=new ScrollView(activity);
        activity.setContentView(scrollView);

        /*
        *リニアレイアウト(VERTICAL)の設定・表示
         */
        LinearLayout verticalLayout=new LinearLayout(activity);
        verticalLayout.setId(1);
        verticalLayout.setOrientation(LinearLayout.VERTICAL);
        verticalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        scrollView.addView(verticalLayout);

        /*
        *リニアレイアウト()の設定表示
         */
        LinearLayout horizontalLayout=new LinearLayout(activity);
        horizontalLayout.setId(2);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        verticalLayout.addView(horizontalLayout);

        /*
        *検索ボタンの生成
         */
        Button searchButton=new Button(activity);
        searchButton.setText("検索");
        searchButton.setTextSize(30);
        searchButton.setOnClickListener(search);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        searchButton.setLayoutParams(buttonLayoutParams);
        horizontalLayout.addView(searchButton);

        /*
        *現在のページ数と全ページ数を表示する
         */
        TextView pageNumText=new TextView(activity);
        pageNumText.setText("現在のページ数");

        /*
        *ひとつ前のページに戻るボタンの生成
         */
        Button backPage=new Button(activity);
        backPage.setText("◀");
        backPage.setTextSize(15);
        backPage.setTag(-1);

    }

    private View.OnClickListener search=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

}
