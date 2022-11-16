package com.example.quizapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class editQuizFragment extends Fragment {
    View layout;
    private Button btnSaveAndNext;
    private Button btnSaveAndExit;
    private EditText editTxtQuizSentence;
    private EditText editTxtCorrectOption;
    private EditText editTxtIncorrectOption1;
    private EditText editTxtIncorrectOption2;
    private EditText editTxtIncorrectOption3;
    private EditText editTxtQuizExplanation;
    private TextView txtPackTitle;

    int quizNum;
    List<String> quizData;

    //MainApplicationの取得
    //MainActivity MainActivity=(MainActivity)getActivity();
    makePackActivity makePackActivity;
    //mainApplication mainApplication= (com.example.quizapp.mainApplication) MainActivity.getMainApplication();
    mainApplication mainApplication;

    //新規か編集かの判定、trueなら新規
    boolean isMakeNewPack;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.fragment_edit_quiz,container,false);
        return inflater.inflate(R.layout.fragment_edit_quiz,container,false);
    }

    //フラグメントが生成されたときに行う処理
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        makePackActivity =(makePackActivity)getActivity();
        mainApplication=(com.example.quizapp.mainApplication) makePackActivity.getMainApplication();

        Button btnSaveAndNext=layout.findViewById(R.id.btnSaveAndNext);
        Button btnSaveAndExit=layout.findViewById(R.id.btnSaveAndExit);
        EditText editTxtQuizSentence=layout.findViewById(R.id.editTxtQuizSentence);
        EditText editTxtCorrectOption=layout.findViewById(R.id.editTxtCorrectOption);
        EditText editTxtIncorrectOption1=layout.findViewById(R.id.editTxtIncorrectOption1);
        EditText editTxtIncorrectOption2=layout.findViewById(R.id.editTxtIncorrectOption2);
        EditText editTxtIncorrectOption3=layout.findViewById(R.id.editTxtIncorrectOption3);
        EditText editTxtQuizExplanation=layout.findViewById(R.id.editTxtQuizExplanation);
        TextView txtPackTitle=layout.findViewById(R.id.txtPackTitle);

        //新規か編集かの判定
        if(makePackActivity.getQuizTotalNum()==0){
            isMakeNewPack=true;
        }else{
            isMakeNewPack=false;
        }

        //新規だった場合。パックタイトルを表示する、packIdの新規作成を行う
        if(isMakeNewPack){
            //packIdの新規作成 ファイル最終行のId+1のIdを作成する
            List<String> allList = mainApplication.getAllList();
            String[] strLastRowAllList = allList.get(allList.size()-1).split(",");
            String newPackId = String.valueOf(Integer.parseInt(strLastRowAllList[0])+1);
            mainApplication.setPackId(newPackId);

            //パックタイトルの表示
            String packTitle = makePackActivity.getPackTitle();
            txtPackTitle.setText(packTitle);
        }

        //編集だった場合。入力欄に、保存されていた情報を表示する
        if(!isMakeNewPack){
            //保存されている情報を取り出す
            String packTitle = makePackActivity.getPackTitle();
            quizData = makePackActivity.getQuizData();
            quizNum = mainApplication.getQuizNum();
            String strQuizData;
            String[] arrayQuizData;
            strQuizData = quizData.get(quizNum-1);
            arrayQuizData = strQuizData.split(",");

            //テキストを表示
            txtPackTitle.setText(packTitle);
            editTxtQuizSentence.setText(arrayQuizData[0]);
            editTxtCorrectOption.setText(arrayQuizData[1]);
            editTxtIncorrectOption1.setText(arrayQuizData[2]);
            editTxtIncorrectOption2.setText(arrayQuizData[3]);
            editTxtIncorrectOption3.setText(arrayQuizData[4]);
            editTxtQuizExplanation.setText(arrayQuizData[5]);
        }
    }


    //「保存して終了」を押された時の処理。
    //保存出来たらフラグメントを閉じる、保存できなかったら何もしない
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSaveAndClose(View view){
        if(saveQuiz()){
            //フラグメントを閉じる
        }
    }

    //「保存して次の問題」を作成を押された時の処理。
    // 保存出来たら入力欄の削除、保存できなかったら何もしない
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClickSaveAndNext(View view){
        if(saveQuiz()){
            //editTxtたちを消す
            editTxtQuizSentence.setVisibility(View.INVISIBLE);
            editTxtCorrectOption.setText("");
            editTxtIncorrectOption1.setText("");
            editTxtIncorrectOption2.setText("");
            editTxtIncorrectOption3.setText("");
            editTxtQuizExplanation.setText("");
        }
    }


    //クイズデータを保存する
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean saveQuiz(){
        if(isSavePossible()){
            //入力情報の取得
            String quizSentence=editTxtQuizSentence.getText().toString();
            String correctOption=editTxtCorrectOption.getText().toString();
            String inCorrectOption1=editTxtIncorrectOption1.getText().toString();
            String inCorrectOption2=editTxtIncorrectOption2.getText().toString();
            String inCorrectOption3=editTxtIncorrectOption3.getText().toString();
            String quizExplanation=editTxtQuizExplanation.getText().toString();

            String strQuizData = quizSentence+","+correctOption+","+inCorrectOption1+","+inCorrectOption2+","+inCorrectOption3+","+quizExplanation;
            //保存する
            quizData.set(quizNum-1, strQuizData);
            String packId = mainApplication.getPackId();
            mainApplication.deleteFile(packId,false);
            mainApplication.saveFileByList(packId,quizData);
            return true;
        }else{
            return false;
        }
    }

    //保存可能かどうか判定する、保存できない場合は警告を出す
    public boolean isSavePossible(){
        boolean isSavePossible = true;
        //保存可能かどうか判定する
        if(editTxtQuizSentence.getText().toString().length()==0){
            isSavePossible = false;
        }
        if(editTxtCorrectOption.getText().toString().length()==0){
            isSavePossible = false;
        }
        if(editTxtIncorrectOption1.getText().toString().length()==0){
            isSavePossible = false;
        }
        if(editTxtIncorrectOption2.getText().toString().length()==0){
            isSavePossible = false;
        }
        if(editTxtIncorrectOption3.getText().toString().length()==0){
            isSavePossible = false;
        }
        if(editTxtQuizExplanation.getText().toString().length()==0){
            isSavePossible = false;
        }
        //保存不可の場合に警告を出す
        if(isSavePossible){
            Toast myToast = Toast.makeText(
                    makePackActivity.getApplicationContext(),
                    "全ての項目を入力してください",
                    Toast.LENGTH_SHORT
            );
            myToast.show();
        }
        return isSavePossible;
    }

    //パックのデータを保存する、新規と編集で保存内容が増減する
    public void savePack(){

    }
}
