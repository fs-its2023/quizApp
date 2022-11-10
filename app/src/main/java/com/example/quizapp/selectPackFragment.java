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
import java.util.Collections;
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
    *フィールドリストの定義
     */
    List<String> allList=new ArrayList<>();
    List<String> selectList=new ArrayList<>();

    /*
    *フィールド変数の定義
     */
    int pageCurrent;
    int pageAll;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*MainActivityとmainApplicationをインスタンス化*/
        mainActivity=(MainActivity)getActivity();
        mainApplication= (com.example.quizapp.mainApplication) mainActivity.getMainApplication();

        allList=mainApplication.getAllList();

        mainApplication.deleteSelectList();
        mainApplication.setSelectList(allList);
        selectList=mainApplication.getSelectList();
        Collections.reverse(selectList);

        pageAll=((selectList.size()-1)/10)+1;
        pageCurrent=1;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        super.onCreateView(inflater, container, savedInstance);
        pageCurrent=1;
        pageAll=((selectList.size()-1)/10)+1;
        return inflater.inflate(R.layout.fragment_select_pack, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mainApplication.getFromMakePackActivity()){
            /*makePackActivityのインスタンス化*/
            makePackActivity=(com.example.quizapp.makePackActivity) getActivity();
            showPackList(makePackActivity);
        }
        if(mainApplication.getFromTakeQuizPackActivity()){
            /*takeQuizPackActivityのインスタンス化*/
            takeQuizPackActivity=(com.example.quizapp.takeQuizPackActivity) getActivity();
            showPackList(takeQuizPackActivity);
        }
    }


    /*
    *Viewの生成
     */
    @SuppressLint("ResourceType")
    public void showPackList(Activity activity){

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
