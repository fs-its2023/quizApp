package com.example.quizapp;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class selectPackFragment extends Fragment {
    /*
    *フィールド変数の定義
     */
    mainApplication mainApplication;
    Activity activity;
    MainActivity mainActivity;
    List<String> allList=new ArrayList<>();


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
            //activity=(com.example.quizapp.makePackActivity);
        }
        if(mainApplication.getFromTakeQuizPackActivity()){
            /*takeQuizPackActivityのインスタンス化*/
            activity=(com.example.quizapp.takeQuizPackActivity) getActivity();
        }

    }

    public void createHorizontalView(){

    }

}
