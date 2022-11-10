package com.example.quizapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class editQuizFragment extends Fragment {

    MainActivity mainActivity;
    mainApplication mainApplication;
    boolean isMakeNewPack;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_edit_quiz,container,false);
    }

    //フラグメントが生成されたときに行う処理
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);



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
