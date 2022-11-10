package com.example.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link makeNewPackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class makeNewPackFragment extends Fragment {
    /*MainActivityをインスタンス化するための変数*/
    mainActivity maActivity;
    /*MakePackActivityをインスタンス化するための変数*/
    com.example.quizapp.makePackActivity makePackActivity;
    /*mainApplicationをインスタンス化するための変数*/
    mainApplication mainApplication;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public makeNewPackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment makeNewPackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static makeNewPackFragment newInstance(String param1, String param2) {
        makeNewPackFragment fragment = new makeNewPackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*
     *このフラグメントが生成された時に行われる処理
     * フィールド変数を定義している
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        maActivity=(mainActivity) getActivity();
        mainApplication= (com.example.quizapp.mainApplication) maActivity.getMainApplication();
        makePackActivity=(com.example.quizapp.makePackActivity) getActivity();
    }

    @Override

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_make_new_pack,container,false);
        return view;
    }

    /*
    *入力確定ボタンが押された時の処理
     */
    public void onClickEnter(View view) {
        EditText packTitleSub = view.findViewById(R.id.packTitle);
        EditText packIntroductionSub = view.findViewById(R.id.packIntroduction);
        TextView textView = view.findViewById(R.id.textView);

        /*スピナーを取得*/
        Spinner sp = (Spinner) view.findViewById(R.id.spinner);

        /*タイトルのテキストエディットに入力された値を取得*/
        String packTitle = packTitleSub.getText().toString();

        /*説明文のテキストエディットに入力された値を取得*/
        String packIntroduction = packIntroductionSub.getText().toString();

        /*スピナーで選択された値を取得*/
        String packGenre = sp.getSelectedItem().toString();

        /*
         *多分タイトルと説明文とスピナーの値はMakePackActivityに渡す必要があるのでその処理を追加してください
         *             ↓今のところMakePackActivityにセッターとゲッターがない状況なので仮に書くとしたらこんな感じになると思う
         * makePackActivity.setPackTitle(packTitle);
         * makePackActivity.setPackIntroduction(packIntroduction);
         * makePackActivity.setPackGenre(packGenre);
         */

        if(!packTitle.equals("")||!packIntroduction.equals("")||!packGenre.equals("なし")){
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
        }else{
            /*「すべての欄に入力してください」というポップアップを表示*/
            Toast.makeText(view.getContext(), "すべての欄に入力してください", Toast.LENGTH_SHORT).show();


        }
    }

}