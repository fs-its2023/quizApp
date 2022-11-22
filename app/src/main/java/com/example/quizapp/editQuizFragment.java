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
    List<String> quizData = new ArrayList<>();
    int quizTotalNum;

    //MainApplicationの取得
    //MainActivity MainActivity=(MainActivity)getActivity();
    makePackActivity makePackActivity;
    //mainApplication mainApplication= (com.example.quizapp.mainApplication) MainActivity.getMainApplication();
    mainApplication mainApplication;

    //新規か編集かの判定、trueなら新規
    boolean isMakeNewQuiz;


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

        //test用パック及びクイズファイルの作成
//        mainApplication.testPackDataFileMaker();
//        mainApplication.testRandomDeleteFile();

        //レイアウトたちの取得
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
            isMakeNewQuiz =true;
        }else{
            isMakeNewQuiz =false;
        }

//        //テスト用,編集の場合
//        isMakeNewQuiz =false;
//        final int TEST_PACK_NUM = 2 ;
//        List<String> list1 = mainApplication.getAllList();
//        String[] testPackData = list1.get(TEST_PACK_NUM).split(",");
//        List<String> list2 = mainApplication.readFileAsList(testPackData[0]);
//        makePackActivity.setQuizData(list2);
//        makePackActivity.getMainApplication().setQuizNum(0);
//        mainApplication.setPackId(testPackData[0]);
//        makePackActivity.setPackTitle(testPackData[1]);

//        //テスト用,新規の場合
//        isMakeNewQuiz=true;
//        makePackActivity.setPackTitle("新規作成テスト：タイトル");
//        makePackActivity.setPackGenre("新しいパックのジャンル");
//        makePackActivity.setPackIntroduction("解説しよう！");

//        新規だった場合。パックタイトルを表示する、packIdの新規作成を行う
        if(isMakeNewQuiz){
            //packIdの新規作成 ファイル最終行のId+1のIdを作成する
            List<String> allList = mainApplication.getAllList();
            String[] strLastRowAllList = allList.get(allList.size()-1).split(",");
            String newPackId =String.format("%04d",Integer.parseInt(strLastRowAllList[0])+1);
            mainApplication.setPackId(newPackId);

            //パックタイトルの表示
            String packTitle = makePackActivity.getPackTitle();
            txtPackTitle.setText(packTitle);

            //クイズ数の初期化
            quizTotalNum = 0;
        }

        //編集だった場合。入力欄に、保存されていた情報を表示する
        if(!isMakeNewQuiz) {
            //保存されている情報を取り出す
            String packTitle = makePackActivity.getPackTitle();
            quizData = makePackActivity.getQuizData();
            quizNum = mainApplication.getQuizNum();
            String strQuizData= quizData.get(quizNum);
            String[] arrayQuizData= strQuizData.split(",");
            quizTotalNum = makePackActivity.getQuizTotalNum();

            //追加の場合
            if(quizData.size() > quizNum)

            //テキストを表示
            txtPackTitle.setText(packTitle);
            editTxtQuizSentence.setText(arrayQuizData[0]);
            editTxtCorrectOption.setText(arrayQuizData[1]);
            editTxtIncorrectOption1.setText(arrayQuizData[2]);
            editTxtIncorrectOption2.setText(arrayQuizData[3]);
            editTxtIncorrectOption3.setText(arrayQuizData[4]);
            editTxtQuizExplanation.setText(arrayQuizData[5]);
        }

        //保存して次の問題を作成するボタン　保存出来たら入力欄を空欄にする
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
        btnSaveAndExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveQuiz()){
                    //makePackActivityを再表示する
                    makePackActivity.reload();
                }
            }
        });
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

            //クイズデータをリストに追加、新規の場合
            if(isMakeNewQuiz){
                quizData.add(strQuizData);
            }


            //クイズデータをリストに追加、編集の場合
            if(!isMakeNewQuiz) {
                quizData.set(quizNum, strQuizData);
            }

            //クイズデータのリストを該当するpackIdのファイルに保存
            String packId = mainApplication.getPackId();
            mainApplication.clearFile(packId);
            mainApplication.saveFileByList(packId, quizData);

            //ポップアップの表示
            Toast myToast = Toast.makeText(
                    makePackActivity.getApplicationContext(),
                    "保存しました",
                    Toast.LENGTH_SHORT
            );
            myToast.show();

            //パックデータの更新
            savePack();

            //クイズ数を追加(編集の時は追加しない)
            if(!isMakeNewQuiz){
                quizTotalNum += 1;
                isMakeNewQuiz = true;
            }
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void savePack(){
        String packId = mainApplication.getPackId();
        //新規だった場合。パック情報をpackData.csvの最終行に追加
        if(isMakeNewQuiz){
            String packTitle = makePackActivity.getPackTitle();
            String packIntroduction = makePackActivity.getPackIntroduction();
            String packGenre = makePackActivity.getPackGenre();

            String newPackData =packId+","+packTitle+","+quizTotalNum+","+packIntroduction+","+packGenre+"\n";
            mainApplication.saveFile(mainApplication.PACK_DATA_FILE_NAME,newPackData);
        }
        //編集だった場合。パックリストを取得、合計クイズ数を変更して再度保存
        if(!isMakeNewQuiz){
            List<String> allList = mainApplication.getAllList();
            for(int i = 0 ; i < allList.size();i++){
                String[] quizData = allList.get(i).split(",");
                if(quizData[0].equals(packId)==true){
                    quizData[2] = String.valueOf(quizTotalNum);
                    String newQuizData = quizData[0]+","+quizData[1]+","+quizData[2]+","+quizData[3]+","+quizData[4];
                    allList.set(Integer.parseInt(packId),newQuizData);
                    mainApplication.clearFile(mainApplication.PACK_DATA_FILE_NAME);
                    mainApplication.saveFileByList(mainApplication.PACK_DATA_FILE_NAME,allList);
                    break;
                }
            }
        }
    }
}
