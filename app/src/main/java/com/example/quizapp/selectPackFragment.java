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
import androidx.fragment.app.FragmentManager;

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
    LinearLayout verticalLayout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*MainActivityとmainApplicationをインスタンス化*/
        mainActivity=(MainActivity)getActivity();
        mainApplication= (com.example.quizapp.mainApplication) mainActivity.getMainApplication();

        /*ApplicationのallListを取得*/
        allList=mainApplication.getAllList();

        /*ApplicationのselectListにallListを入れる*/
        mainApplication.deleteSelectList();
        mainApplication.setSelectList(allList);
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        super.onCreateView(inflater, container, savedInstance);

        /*selectListに値を入れ、新着順に変更*/
        selectList=mainApplication.getSelectList();
        Collections.reverse(selectList);

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
        verticalLayout=new LinearLayout(activity);
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
        searchButton.setOnClickListener(onClickSearch);
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

            /*
            *パック情報のボタンを表示
             */
            for(int j=0;j<2;j++){
                Button packButton=new Button(activity);
                packButton.setText(""+listData[3]);
                packButton.setTextSize(20);
                packButton.setTag(i);
                packButton.setOnClickListener(onClickSetPackId);
                packButton.setLayoutParams(buttonLayoutParams);
                packIntroductionHorizontalLayout.addView(packButton);
            }
        }

    }

    /*
    *検索ボタンが押された時の処理
     */
    private View.OnClickListener onClickSearch =new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            /*現在のページ数の変数を1にする*/
            pageCurrent=1;

            if(mainApplication.getFromMakePackActivity()){
                FragmentManager manager=makePackActivity.getSupportFragmentManager();
            }
            if(mainApplication.getFromTakeQuizPackActivity()){

            }
        }
    };

    /*
    *パックが選択された時の処理
     */
    private View.OnClickListener onClickSetPackId=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    /*
    *selectListを","区切りで配列に入れている
     */
    public void setListData(int i,List<String> allList){
        listData=allList.get(i).split(",");
    }

    /*
    *searchFragmentの生成
     */


}
