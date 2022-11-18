package com.example.quizapp;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class editPackFragment extends Fragment implements View.OnClickListener {

    private mainApplication mainApp;
    private makePackActivity mpActivity;
    private List<String> selectedQuizzes;
    private LinearLayout scrollLayout;

    public editPackFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static editPackFragment newInstance() {
        editPackFragment fragment = new editPackFragment();
/*        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_pack, container, false);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mpActivity = (makePackActivity)getActivity();
        this.mainApp = mpActivity.getMainApplication();

        /*
         * パック情報読み込み
         */
        List<String> lstPackDataFile = this.mainApp.getAllList();
        String[] lineDataFile = new String[5];
        this.mainApp.setPackId("0000");
        /*
        * 該当行の特定
         */
        for(int i = 0; i < lstPackDataFile.size(); i++){
            lineDataFile = lstPackDataFile.get(i).split(",");
            if(lineDataFile[0].equals(this.mainApp.getPackId())){
                break;
            }
        }

        this.mpActivity.setPackTitle(lineDataFile[1]);
        this.mpActivity.setQuizTotalNum(Integer.parseInt(lineDataFile[2]));
        this.mpActivity.setPackIntroduction(lineDataFile[3]);
        this.mpActivity.setPackGenre(lineDataFile[4]);

        /*
        * クイズ情報読み込み
         */
        List<String> lstPackIdFile = this.mainApp.readFileAsList(this.mainApp.getPackId());
        String[] lineIdFile = new String[6];
        String quizSentence;
        scrollLayout = view.findViewById(R.id.quizListScroll);
        Button btnSelectQuiz;
        for(int i = 0; i < lstPackIdFile.size(); i++){
            lineIdFile = lstPackIdFile.get(i).split(",");
            quizSentence = lineIdFile[0];
            /*
            * ボタン作成
             */
            btnSelectQuiz = new Button(this.mpActivity);
            btnSelectQuiz.setText(lineIdFile[0]);
            btnSelectQuiz.setTextSize(30);
            btnSelectQuiz.setTag("quizSelect" + i);
            btnSelectQuiz.setOnClickListener(this);
            //ボタンの幅、高さの設定
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            btnSelectQuiz.setLayoutParams(buttonLayoutParams);
            scrollLayout.addView(btnSelectQuiz);
        }
    }

    @Override
    public void onClick(View view){

    }

    public boolean canDelete(){
        boolean isDeleteConfirmed=false;
//toast
        return isDeleteConfirmed;
    }
}