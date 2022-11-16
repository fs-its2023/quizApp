package com.example.myapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class searchFragment extends Fragment {
    String spinner1;
    String spinner2;
    String keyword;
    int maxQuiz;
    int minQuiz ;
    int index;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }

    public void onClickEnter(View view) {

        EditText keywordInSub = view.findViewById(R.id.keywordIn);
        Spinner sp1 = (Spinner) view.findViewById(R.id.spinner1);
        Spinner sp2 = (Spinner) view.findViewById(R.id.spinner2);
        TextView textView = view.findViewById(R.id.textView);


        keyword = keywordInSub.getText().toString();//タイトルのテキストエディットに入力された値を取得
        spinner1 = sp1.getSelectedItem().toString();//スピナーで選択された値を取得
        spinner2 = sp2.getSelectedItem().toString();//スピナーで選択された値を取得

        if(keyword.equals("")&&spinner1.equals("なし")&&spinner2.equals("なし")){
            Toast.makeText(view.getContext(), "欄に入力してください", Toast.LENGTH_SHORT).show();
            //「欄に入力してください」というポップアップを表示
            //×を押してポップアップが消える

        }else{
            textView.setText(String.format("ジャンルは" + spinner1 + ",問題数は" + spinner2 + ",キーワードは" + keyword + "になります"));
            //listUpPackを起動
        }
    }

    public void listUpPack(){

    }
}
