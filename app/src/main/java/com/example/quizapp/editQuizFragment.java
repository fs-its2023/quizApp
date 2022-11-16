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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class editQuizFragment extends Fragment{
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
        return inflater.inflate(R.layout.fragment_edit_quiz,container,false);
    }

    //フラグメントが生成されたときに行う処理
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        makePackActivity =(makePackActivity)getActivity();
        mainApplication=(com.example.quizapp.mainApplication) makePackActivity.getMainApplication();

        btnSaveAndNext=view.findViewById(R.id.btnSaveAndNext);
        btnSaveAndExit=view.findViewById(R.id.btnSaveAndExit);
        editTxtQuizSentence=view.findViewById(R.id.editTxtQuizSentence);
        editTxtCorrectOption=view.findViewById(R.id.editTxtCorrectOption);
        editTxtIncorrectOption1=view.findViewById(R.id.editTxtIncorrectOption1);
        editTxtIncorrectOption2=view.findViewById(R.id.editTxtIncorrectOption2);
        editTxtIncorrectOption3=view.findViewById(R.id.editTxtIncorrectOption3);
        editTxtQuizExplanation=view.findViewById(R.id.editTxtQuizExplanation);
        txtPackTitle=view.findViewById(R.id.txtPackTitle);


        //新規か編集かの判定
        if(makePackActivity.getQuizTotalNum()==0){
            isMakeNewPack=true;
        }else{
            isMakeNewPack=false;
        }

        //テスト用,編集の場合
        isMakeNewPack=false;
        makePackActivity.setPackTitle("編集テスト：タイトル");
        List<String> list1 = new ArrayList<>();
        list1.add("title,aaa,bbb,ccc,ddd,kaisetsu");
        makePackActivity.setQuizData(list1);
        makePackActivity.getMainApplication().setQuizNum(1);

//        //テスト用,新規の場合
//        isMakeNewPack=true;
//        makePackActivity.setPackTitle("新規作成テスト：タイトル");

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
        if(!isMakeNewPack) {
            //保存されている情報を取り出す
            String packTitle = makePackActivity.getPackTitle();
            quizData = makePackActivity.getQuizData();
            quizNum = mainApplication.getQuizNum();
            String strQuizData;
            String[] arrayQuizData;
            strQuizData = quizData.get(quizNum - 1);
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

        //保存して新規作成ボタン　保存出来たら入力欄を空欄にする
        btnSaveAndNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveQuiz()) {
                        //editTxtたちを消す
                        editTxtQuizSentence.setText("");
                        editTxtCorrectOption.setText("");
                        editTxtIncorrectOption1.setText("");
                        editTxtIncorrectOption2.setText("");
                        editTxtIncorrectOption3.setText("");
                        editTxtQuizExplanation.setText("");
                    }
            }
        });

        //保存して終了ボタン　保存出来たらフラグメントを閉じる
        //フラグメント閉じれない
        btnSaveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (saveQuiz()) {
                    FragmentTransaction transaction;
                    FragmentManager fragmentManager = getFragmentManager();
                    editQuizFragment editQuizFragment = new editQuizFragment();
                    transaction = fragmentManager.beginTransaction();
                    transaction.remove(editQuizFragment);
                    transaction.commit();
                }
            }
        });

    }

    //クイズデータを保存する
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean saveQuiz(){
        if(isSavePossible()){
//            //入力情報の取得
//            String quizSentence=editTxtQuizSentence.getText().toString();
//            String correctOption=editTxtCorrectOption.getText().toString();
//            String inCorrectOption1=editTxtIncorrectOption1.getText().toString();
//            String inCorrectOption2=editTxtIncorrectOption2.getText().toString();
//            String inCorrectOption3=editTxtIncorrectOption3.getText().toString();
//            String quizExplanation=editTxtQuizExplanation.getText().toString();
//
//            String strQuizData = quizSentence+","+correctOption+","+inCorrectOption1+","+inCorrectOption2+","+inCorrectOption3+","+quizExplanation;
//            //保存する
//            quizData.set(quizNum-1, strQuizData);
//            String packId = mainApplication.getPackId();
//            mainApplication.deleteFile(packId,false);
//            mainApplication.saveFileByList(packId,quizData);
            return true;
        }
        return false;
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
        if(!isSavePossible){
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
        //新規だった場合。パック情報をpackData.csvの最終行に追加
        if(isMakeNewPack){

        }
        //編集だった場合。パックリストを取得、合計クイズ数を変更して再度保存
        if(!isMakeNewPack){

        }

    }
}
