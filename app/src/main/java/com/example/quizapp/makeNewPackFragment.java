package com.example.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;




public class makeNewPackFragment extends Fragment {
    /*MainActivityをインスタンス化するための変数*/
    //MainActivity maActivity;
    /*MakePackActivityをインスタンス化するための変数*/
    com.example.quizapp.makePackActivity makePackActivity;
    /*mainApplicationをインスタンス化するための変数*/
    mainApplication mainApplication;


    /*
     *このフラグメントが生成された時に行われる処理
     * フィールド変数を定義している
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //maActivity=(MainActivity) getActivity();
        makePackActivity=(com.example.quizapp.makePackActivity) getActivity();
        mainApplication= (com.example.quizapp.mainApplication) makePackActivity.getMainApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_make_new_pack,container,false);
        Button btnSend = (Button)view.findViewById(R.id.btnSend);
        EditText packTitleSub = view.findViewById(R.id.editTxtIncorrectOption3);
        EditText packIntroductionSub = view.findViewById(R.id.packIntroduction);


        /*スピナーを取得*/
        Spinner sp = (Spinner) view.findViewById(R.id.spinner);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*タイトルのテキストエディットに入力された値を取得*/
                String packTitle = packTitleSub.getText().toString();

                /*説明文のテキストエディットに入力された値を取得*/
                String packIntroduction = packIntroductionSub.getText().toString();

                /*スピナーで選択された値を取得*/
                String packGenre = sp.getSelectedItem().toString();
                if(packTitle.contains(",")||packIntroduction.contains(",")){
                    Toast.makeText(view.getContext(), "使用できない文字が含まれています", Toast.LENGTH_SHORT).show();
                }else{
                    if(packTitle.equals("")||packIntroduction.equals("")||packGenre.equals("なし")){
                        /*「すべての欄に入力してください」というポップアップを表示*/
                        Toast.makeText(view.getContext(), "すべての欄に入力してください", Toast.LENGTH_SHORT).show();
                    }else{

                        makePackActivity.setPackTitle(packTitle);
                        makePackActivity.setPackIntroduction(packIntroduction);
                        makePackActivity.setPackGenre(packGenre);
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.container,new editQuizFragment())
                                .addToBackStack(null)
                                .commit();
                        }
                    }

            }
        });
        return view;
    }
}