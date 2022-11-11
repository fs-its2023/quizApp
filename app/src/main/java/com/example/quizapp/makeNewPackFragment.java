package com.example.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;




public class makeNewPackFragment extends Fragment {
    /*MainActivityをインスタンス化するための変数*/
    MainActivity maActivity;
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

        maActivity=(MainActivity) getActivity();
        mainApplication= (com.example.quizapp.mainApplication) maActivity.getMainApplication();
        makePackActivity=(com.example.quizapp.makePackActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_make_new_pack,container,false);
        Button btnSend = (Button)view.findViewById(R.id.btnSend);
        EditText packTitleSub = view.findViewById(R.id.packTitle);
        EditText packIntroductionSub = view.findViewById(R.id.packIntroduction);
        TextView textView = view.findViewById(R.id.textView);

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

                if(packTitle.equals("")||packIntroduction.equals("")||packGenre.equals("なし")){
                    /*「すべての欄に入力してください」というポップアップを表示*/
                    Toast.makeText(view.getContext(), "すべての欄に入力してください", Toast.LENGTH_SHORT).show();
                }else{
                    textView.setText(String.format("タイトルは" + packTitle + ",説明文は" + packIntroduction + ",ジャンルは" + packGenre + "になります"));
                    makePackActivity.setPackTitle(packTitle);
                    makePackActivity.setPackIntroduction(packIntroduction);
                    makePackActivity.setPackGenre(packGenre);

                    /*makeNewPackFragmentをスタックしてeditQuizFragmentを起動させる*/
                    /* フラグメントマネージャーの取得*/
                    FragmentManager manager = makePackActivity.getSupportFragmentManager();
                    /* フラグメントトランザクションの開始*/
                    FragmentTransaction transaction = manager.beginTransaction();
                    /* レイアウトをfragmentに置き換え（追加）*/
                    transaction.replace(R.id.container,new editQuizFragment());
                    /* 置き換えのトランザクションをバックスタックに保存する*/
                    transaction.addToBackStack(null);
                    /* フラグメントトランザクションをコミット*/
                    transaction.commit();
                }
            }
        });
        return view;
    }
}