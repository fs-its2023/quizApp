package com.example.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    /*MainaActivityをインスタンス化するための変数*/
    MainActivity maActivity;
    /*MakePackActivityをインスタンス化するための変数*/
    MakePackActivity makePackActivity;
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
        maActivity=(MainActivity) getActivity();
        mainApplication= (com.example.quizapp.mainApplication) maActivity.getMainApplication();
        makePackActivity=(MakePackActivity) getActivity();
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

        /*
        *ボタンの処理の中に書いては意味がないのではいだろうか(無くても動くと思われる)
        * Button btnSend = view.findViewById(R.id.btnSend);
         */

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
         *多分タイトルと説明文とスピナーの値ははMakePackActivityに渡す必要があるのでその処理を追加してください
         *             ↓今のところMakePackActivityにセッターとゲッターがない状況なので仮に書くとしたらこんな感じになると思う
         * makePackActivity.setPackTitle(packTitle);
         * makePackActivity.setPackIntroduction(packIntroduction);
         * makePackActivity.setPackGenre(packGenre);
         */

        if(!packTitle.equals("")||!packIntroduction.equals("")||!packGenre.equals("なし")){
            textView.setText(String.format("タイトルは" + packTitle + ",説明文は" + packIntroduction + ",ジャンルは" + packGenre + "になります"));
            //makeNewPackFragmentをスタックしてeditQuizFragmentを起動させる
        }else{
            Toast.makeText(view.getContext(), "すべての欄に入力してください", Toast.LENGTH_SHORT).show();
            //「すべての欄に入力してください」というポップアップを表示
            //×を押してポップアップが消える
        }
    }

}