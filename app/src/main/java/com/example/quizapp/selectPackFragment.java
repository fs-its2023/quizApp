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
    String[] listData;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*MainActivityとmainApplicationをインスタンス化*/
        mainActivity=(MainActivity)getActivity();
        mainApplication= (com.example.quizapp.mainApplication) mainActivity.getMainApplication();

        /*ApplicationのallListを取得*/
        allList=mainApplication.getAllList();

        /*selectListにallListを入れ、新着順に変更*/
        mainApplication.deleteSelectList();
        mainApplication.setSelectList(allList);
        selectList=mainApplication.getSelectList();
        Collections.reverse(selectList);

        /*ページ数を表示するために変数に値を代入*/
        pageAll=((selectList.size()-1)/10)+1;
        pageCurrent=1;

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        super.onCreateView(inflater, container, savedInstance);

        /*ページ数を表示するために変数に値を代入*/
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
        *リニアレイアウト(HORIZONTAL)の設定表示
         */
        LinearLayout horizontalLayout=new LinearLayout(activity);
        horizontalLayout.setId(2);
        horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
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
        *ひとつ前のページに戻るボタンの生成
         */
        Button backPageButton=new Button(activity);
        backPageButton.setText("◀");
        backPageButton.setTextSize(15);
        backPageButton.setTag(-1);
        backPageButton.setLayoutParams(buttonLayoutParams);
        horizontalLayout.addView(backPageButton);

        /*
         *現在のページ数と全ページ数を表示する
         */
        TextView pageNumText=new TextView(activity);
        pageNumText.setText(pageCurrent+"/"+pageAll);
        pageNumText.setTextSize(15);
        LinearLayout.LayoutParams textLayoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        pageNumText.setLayoutParams(textLayoutParams);
        horizontalLayout.addView(pageNumText);

        /*
        *次のページに行くボタンを作る
         */
        Button nextPageButton=new Button(activity);
        nextPageButton.setText("▶");
        nextPageButton.setTextSize(15);
        nextPageButton.setTag(1);
        nextPageButton.setLayoutParams(buttonLayoutParams);
        horizontalLayout.addView(nextPageButton);

        /*
        *パックの情報を出力する
         */
        for(int i=10*pageCurrent-10;i<10*pageCurrent;i++){
            setListData(i,selectList);

            /*
            *リニアレイアウト(HORIZONTAL)の設定表示
             */
            LinearLayout packIntroductionHorizontalLayout=new LinearLayout(activity);
            packIntroductionHorizontalLayout.setId(3);
            packIntroductionHorizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            packIntroductionHorizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            verticalLayout.addView(packIntroductionHorizontalLayout);
        }

    }

    private View.OnClickListener search=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    public void setListData(int i,List<String> allList){
        listData=allList.get(i).split(",");
    }

}
