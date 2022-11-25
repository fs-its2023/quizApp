package com.example.quizapp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class editPackFragment extends Fragment implements View.OnClickListener {

    private mainApplication mainApp;
    private makePackActivity mpActivity;
    private List<Integer> selectedQuizzes = new ArrayList<>();
    private LinearLayout scrollLayout;
    private Button btnResetSelection, btnDeletePack, btnAddQuiz, btnDeleteQuiz, btnEditQuiz;
    private ConstraintLayout deleteMsgBox;
    private Button btnDeleteOk, btnDeleteCancel;
    private String deleteMode;
    private boolean isEnabled = true;

    public editPackFragment() {
        // Required empty public constructor
    }

    public static editPackFragment newInstance() {
        editPackFragment fragment = new editPackFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        * ボタン初期設定
         */
        this.btnResetSelection = view.findViewById(R.id.btnResetSelection);
        this.btnResetSelection.setTag("resetSelection");
        this.btnResetSelection.setEnabled(false);
        this.btnResetSelection.setOnClickListener(this);
        this.btnDeletePack = view.findViewById(R.id.btnDeletePack);
        this.btnDeletePack.setTag("deletePack");
        this.btnDeletePack.setOnClickListener(this);
        this.btnDeleteQuiz = view.findViewById(R.id.btnDeleteQuiz);
        this.btnDeleteQuiz.setTag("deleteQuiz");
        this.btnDeleteQuiz.setOnClickListener(this);
        this.btnDeleteQuiz.setEnabled(false);
        this.btnEditQuiz = view.findViewById(R.id.btnEditQuizInEPF);
        this.btnEditQuiz.setTag("editQuiz");
        this.btnEditQuiz.setOnClickListener(this);
        this.btnEditQuiz.setEnabled(false);
        this.btnAddQuiz = view.findViewById(R.id.btnAddQuiz);
        this.btnAddQuiz.setTag("addQuiz");
        this.btnAddQuiz.setOnClickListener(this);

        this.deleteMsgBox = view.findViewById(R.id.deleteMsgBox);
        this.deleteMsgBox.setBackgroundColor(Color.rgb(220,220,220));
        this.btnDeleteOk = view.findViewById(R.id.btnDeleteOk);
        this.btnDeleteOk.setOnClickListener(this);
        this.btnDeleteOk.setTag("deleteOk");
        this.btnDeleteCancel = view.findViewById(R.id.btnDeleteCancel);
        this.btnDeleteCancel.setOnClickListener(this);
        this.btnDeleteCancel.setTag("deleteCancel");

        /*
         * パック情報読み込み
         */
        List<String> lstPackDataFile = this.mainApp.getAllList();
        String[] lineDataFile = new String[5];

        /*
        * 該当行の特定
         */
        for(int i = 0; i < lstPackDataFile.size(); i++){
            lineDataFile = lstPackDataFile.get(i).split(",");
            if(lineDataFile[0].equals(this.mainApp.getPackId())){
                break;
            }
        }
        /*
        * パックデータ取得
         */
        this.mpActivity.setPackTitle(lineDataFile[1]);
        this.mpActivity.setQuizTotalNum(Integer.parseInt(lineDataFile[2]));
        this.mpActivity.setPackIntroduction(lineDataFile[3]);
        this.mpActivity.setPackGenre(lineDataFile[4]);

        TextView txtPackTitle = view.findViewById(R.id.txtPackTitleEPF);
        txtPackTitle.setText(lineDataFile[1]);

        /*
        * クイズ情報読み込み
         */
        List<String> lstPackIdFile = this.mainApp.readFileAsList(this.mainApp.getPackId());
        String[] lineIdFile = new String[6];
        scrollLayout = view.findViewById(R.id.quizListScroll);
        Button btnSelectQuiz;
        TextView space;

        for(int i = 0; i < lstPackIdFile.size(); i++){
            lineIdFile = lstPackIdFile.get(i).split(",");
            /*
            * ボタン作成
             */
            btnSelectQuiz = new Button(this.mpActivity);
            btnSelectQuiz.setText(lineIdFile[0]);
            btnSelectQuiz.setTextSize(20);
            btnSelectQuiz.setTag("quizSelect" + i);
            btnSelectQuiz.setBackgroundColor(Color.rgb(200,200,200));
            btnSelectQuiz.setOnClickListener(this);

            //ボタンの幅、高さの設定
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    120);
            btnSelectQuiz.setLayoutParams(buttonLayoutParams);
            scrollLayout.addView(btnSelectQuiz);
            space = new TextView(this.mpActivity);
            space.setHeight(30);
            scrollLayout.addView(space);
        }
    }

    /*
    * パック削除メソッド
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deletePack(){
        //packIdファイル削除
        this.mainApp.clearFile(this.mainApp.getPackId());

        //パックデータファイルの該当行削除
        List<String> lstFileData = this.mainApp.readFileAsList(this.mainApp.PACK_DATA_FILE_NAME);
        String line;
        String packId;

        for(int i = 0; i < lstFileData.size(); i++){
            line = lstFileData.get(i);
            packId = line.substring(0,line.indexOf(","));
            if(packId.equals(this.mainApp.getPackId())){
                lstFileData.remove(i);
                break;
            }
        }
        this.mainApp.clearFile(this.mainApp.PACK_DATA_FILE_NAME);
        this.mainApp.saveFileByList(this.mainApp.PACK_DATA_FILE_NAME, lstFileData);

        //activity再起動
        this.mpActivity.reload();
    }

    /*
    * クイズ削除メソッド
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteQuiz(){
        //選択されたクイズ番号を降順ソート
        Collections.sort(this.selectedQuizzes, Collections.reverseOrder());

        //ファイルから該当行削除
        List<String> lstPackIdFile = this.mainApp.readFileAsList(this.mainApp.getPackId());
        for (int i:this.selectedQuizzes) {
            lstPackIdFile.remove(i);
        }
        this.mainApp.clearFile(this.mainApp.getPackId());
        this.mainApp.saveFileByList(this.mainApp.getPackId(), lstPackIdFile);

        if(lstPackIdFile.size() == 0){
            //activity再起動
            this.mpActivity.reload();
        }else{
            //クイズリスト再読み込み
            this.reloadFragment();
        }
    }

    public void reloadFragment(){
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, new editPackFragment());
        ft.commit();
    }

    public void editQuiz(){
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.container, new editQuizFragment());
        ft.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view){
        if(view.getTag().toString().contains("quizSelect") && this.isEnabled){
            //クイズ選択ボタン
            Integer quizNum = Integer.parseInt(view.getTag().toString().substring(10));
            if(this.selectedQuizzes.contains(quizNum)){
                //選択済みの場合
                this.selectedQuizzes.remove(this.selectedQuizzes.indexOf(quizNum));
                view.setBackgroundColor(Color.rgb(200,200,200));
            }else{
                //未選択の場合
                this.selectedQuizzes.add(quizNum);
                view.setBackgroundColor(Color.rgb(150,150,150));
            }

            /*
            * 編集・削除ボタンの有効・無効化
             */
            if(this.selectedQuizzes.size() == 0){
                //編集・削除無効
                this.btnResetSelection.setEnabled(false);
                this.btnDeleteQuiz.setEnabled(false);
                this.btnEditQuiz.setEnabled(false);
            }else if(this.selectedQuizzes.size() == 1){
                //編集・削除有効
                this.btnResetSelection.setEnabled(true);
                this.btnDeleteQuiz.setEnabled(true);
                this.btnEditQuiz.setEnabled(true);
            }else{
                //削除有効　編集無効
                this.btnResetSelection.setEnabled(true);
                this.btnDeleteQuiz.setEnabled(true);
                this.btnEditQuiz.setEnabled(false);
            }

        }else if(view.getTag().toString().equals("deletePack") && this.isEnabled){
            /*
            * パック削除ボタン
             */
            this.deleteMode = "pack";
            this.deleteMsgBox.setVisibility(View.VISIBLE);
            this.isEnabled = false;

        }else if(view.getTag().toString().equals("deleteQuiz") && this.isEnabled){
            /*
            * クイズ削除ボタン
             */
            this.deleteMode = "quiz";
            this.deleteMsgBox.setVisibility(View.VISIBLE);
            this.isEnabled = false;

        }else if(view.getTag().toString().equals("addQuiz") && this.isEnabled){
            /*
             * クイズ編集ボタン
             */
            this.mainApp.setQuizNum(this.mpActivity.getQuizTotalNum());

            this.editQuiz();

        }else if(view.getTag().toString().equals("editQuiz") && this.isEnabled){
            /*
            * クイズ編集ボタン
             */
            this.mainApp.setQuizNum(this.selectedQuizzes.get(0));

            this.editQuiz();
            
        }else if(view.getTag().toString().equals("resetSelection") && this.isEnabled){
            /*
             * 選択解除ボタン
             */
            this.reloadFragment();
        }else if(view.getTag().toString().equals("deleteOk")){
            /*
             * 削除OKボタン
             */
            this.deleteMsgBox.setVisibility(View.GONE);
            if(this.deleteMode.equals("quiz")){
                this.deleteQuiz();
            }else if(this.deleteMode.equals("pack")){
                this.deletePack();
            }
        }else if(view.getTag().toString().equals("deleteCancel")){
            /*
             * 削除cancelボタン
             */
            this.deleteMsgBox.setVisibility(View.GONE);
            this.isEnabled = true;
        }
    }
}