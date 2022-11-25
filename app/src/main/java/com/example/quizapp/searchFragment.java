package com.example.quizapp;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class searchFragment extends Fragment {
    String spinnerNum;//問題数
    String spinnerGen;//ジャンル
    String keyword;
    int maxQuiz;
    int minQuiz ;
    int index;
    com.example.quizapp.selectPackActivity selectPackActivity;
    mainApplication mainApplication;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Button searchButton = view.findViewById(R.id.searchButton);
        EditText keywordInSub = view.findViewById(R.id.keywordIn);
        Spinner spGen = (Spinner) view.findViewById(R.id.spinnerGen);
        Spinner spNum = (Spinner) view.findViewById(R.id.spinnerNum);

        selectPackActivity=(com.example.quizapp.selectPackActivity) getActivity();
        mainApplication= (com.example.quizapp.mainApplication) selectPackActivity.getMainApplication();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View view) {
                keyword = keywordInSub.getText().toString();//タイトルのテキストエディットに入力された値を取得
                spinnerGen = spGen.getSelectedItem().toString();//スピナーで選択された値を取得
                spinnerNum = spNum.getSelectedItem().toString();//スピナーで選択された値を取得
                if (keyword.equals("") && spinnerNum.equals("なし") && spinnerGen.equals("なし")) {
                    Toast.makeText(view.getContext(), "欄に入力してください", Toast.LENGTH_SHORT).show();
                    //「欄に入力してください」というポップアップを表示
                    //×を押してポップアップが消える

                } else {

                    /*listUpPackを起動*/
                    listUpPack(view);

                }
            }
        });
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void listUpPack(View view){
        List<String> lines = mainApplication.getAllList();
        List<String> selectLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String[] data = lines.get(i).split(",");
            if (!spinnerGen.equals("なし") && !spinnerGen.equals(data[4])){
                continue;
            }

            index = spinnerNum.indexOf("~");
            maxQuiz = 0;
            minQuiz = 0;

            int linesGet = Integer.parseInt(data[2]);//Listからとりだした値（String型）をint型の変数として宣言
            maxQuiz = Integer.parseInt(spinnerNum.substring(index+1,spinnerNum.length()));//spinner1で入力したString型の値（~の後の数字）をint型にしmaxQuizに代入
            minQuiz = Integer.parseInt(spinnerNum.substring(0,index));//spinner1で入力したString型の値（~の前の数字）をint型にしmixQuizに代入


            if (!spinnerNum.equals("なし") && (maxQuiz < linesGet || minQuiz > linesGet)) {
                continue;
            }
            if (!keyword.equals("") && !data[1].contains(keyword) && !data[3].contains(keyword)) {
                continue;
            }


            selectLines.add(lines.get(i));
        }

        if (selectLines == null || selectLines.size() == 0){
           // Toast.makeText(view.getContext(), "該当する検索結果がありません", Toast.LENGTH_LONG).show();
            Toast.makeText(selectPackActivity, "該当する検索結果がありません", Toast.LENGTH_LONG).show();
        }else {
            mainApplication.setSelectList(selectLines);
            //getFragmentManager().beginTransaction().remove(this).commit();
            selectPackActivity.reload();
        }

    }


}
