package com.example.quizapp;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class searchFragment extends Fragment {
    String spinner1;//問題数
    String spinner2;//ジャンル
    String keyword;
    int maxQuiz;
    int minQuiz ;
    int index;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Button searchButton = view.findViewById(R.id.searchButton);
        EditText keywordInSub = view.findViewById(R.id.keywordIn);
        Spinner sp1 = (Spinner) view.findViewById(R.id.spinner1);
        Spinner sp2 = (Spinner) view.findViewById(R.id.spinner2);
        TextView textView = view.findViewById(R.id.textView);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword = keywordInSub.getText().toString();//タイトルのテキストエディットに入力された値を取得
                spinner1 = sp1.getSelectedItem().toString();//スピナーで選択された値を取得
                spinner2 = sp2.getSelectedItem().toString();//スピナーで選択された値を取得
                if (keyword.equals("") && spinner1.equals("なし") && spinner2.equals("なし")) {
                    Toast.makeText(view.getContext(), "欄に入力してください", Toast.LENGTH_SHORT).show();
                    //「欄に入力してください」というポップアップを表示
                    //×を押してポップアップが消える

                } else {
                    textView.setText(String.format("ジャンルは" + spinner1 + ",問題数は" + spinner2 + ",キーワードは" + keyword + "になります"));
        //listUpPackを起動

                    listUpPack();

                }
            }
        });
        return view;
    }

    public void listUpPack(){
        List<String> lines = new ArrayList<String>(Arrays.asList("ワンピース大全","ワンピースのクイズだよ","24","アニメ","0001","アニマル百科","動物のクイズだよ","46","動物","0021"));

        for (int i = 0; i < lines.size(); i++) {
            //String[] data = lines.get(i).split(",");
            if (!spinner2.equals("なし") && spinner2 != lines.get(3)){
                continue;
            }

            index = lines.indexOf("~");
            maxQuiz = 0;
            minQuiz = 0;

            int linesGet = Integer.parseInt(lines.get(2));//Listからとりだした値（String型）をint型の変数として宣言
            maxQuiz = Integer.parseInt(spinner1.substring(index-1));//spinner1で入力したString型の値（~の後の数字）をint型にしmaxQuizに代入
            minQuiz = Integer.parseInt(spinner1.substring(index-1));//spinner1で入力したString型の値（~の前の数字）をint型にしmixQuizに代入


            if (!spinner1.equals("なし") && (maxQuiz < linesGet || minQuiz > linesGet)) {
                continue;
            }
            if (!keyword.equals("") && !lines.get(0).contains(keyword) && !lines.get(0).contains(keyword)){
                continue;
            }


        }
    }
}
