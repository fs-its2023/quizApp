package com.example.quizapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class editQuizFragment extends Fragment {

    //MainApplicationの取得
    //MainActivity MainActivity=(MainActivity)getActivity();

    makePackActivity makePackActivity;
    //mainApplication mainApplication= (com.example.quizapp.mainApplication) MainActivity.getMainApplication();
    mainApplication mainApplication;

    //新規か編集かの判定、trueなら新規
    boolean isMakeNewPack;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_edit_quiz,container,false);
    }

    //フラグメントが生成されたときに行う処理
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        makePackActivity =(makePackActivity)getActivity();
        mainApplication=(com.example.quizapp.mainApplication) makePackActivity.getMainApplication();

        //新規か編集かの判定
        if(makePackActivity.getQuizTotalNum()==0){
            this.isMakeNewPack=true;
        }else{
            this.isMakeNewPack=false;
        }

        //新規だった場合。パックタイトルを表示する、packIdの新規作成を行う
        if(isMakeNewPack){
            //packIdの新規作成 ファイル最終行のId+1のIdを作成する
            List<String> allList = this.mainApplication.getAllList();
            String[] strLastRowAllList = allList.get(allList.size()-1).split(",");
            String newPackId = String.valueOf(Integer.parseInt(strLastRowAllList[0])+1);
            mainApplication.setPackId(newPackId);
        }

        //編集だった場合。入力欄に保存されていた情報を表示する、mainApplicationからpackIdを取得する
        if(!isMakeNewPack){

        }
    }


    //「保存して終了」を押された時の処理。
    //保存出来たらフラグメントを閉じる、保存できなかったら何もしない
    public void onClickSaveAndClose(View view){
        
    }

    //「保存して次の問題」を作成を押された時の処理。
    // 保存出来たら入力欄の削除、保存できなかったら何もしない
    public void onClickSaveAndNext(View view){

    }


    //クイズデータを保存する
    public boolean saveQuiz(){
        return true;
    }

    //保存可能かどうか判定する、保存できない場合は警告を出す
    public boolean isSavePossible(){
        return true;
    }

    //パックのデータを保存する、新規と編集で保存内容が増減する
    public void savePack(){

    }
}
