package com.example.quizapp;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
    private List<Integer> selectedQuizzes;
    private LinearLayout scrollLayout;
    private Button btnDeletePack, btnDeleteQuiz, btnEditQuiz;

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
        this.btnDeletePack = view.findViewById(R.id.btnDeletePack);
        this.btnDeletePack.setTag("deletePack");
        this.btnDeleteQuiz = view.findViewById(R.id.btnDeleteQuiz);
        this.btnDeleteQuiz.setTag("deleteQuiz");
        this.btnDeleteQuiz.setActivated(false);
        this.btnEditQuiz = view.findViewById(R.id.btnEditQuizInEPF);
        this.btnEditQuiz.setTag("editQuiz");
        this.btnEditQuiz.setActivated(false);

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
        /*
        * パックデータ取得
         */
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view){
        if(view.getTag().toString().contains("quizSelect")){
            //クイズ選択ボタン
            int quizNum = Integer.parseInt(view.getTag().toString().substring(10));
            if(this.selectedQuizzes.contains(quizNum)){
                //選択済みの場合
                this.selectedQuizzes.remove(this.selectedQuizzes.indexOf(quizNum));
            }else{
                //未選択の場合
                this.selectedQuizzes.add(this.selectedQuizzes.indexOf(quizNum));
            }

            /*
            * 編集・削除ボタンの有効・無効化
             */
            if(selectedQuizzes.size() == 0){
                //編集・削除無効
                this.btnDeleteQuiz.setActivated(false);
                this.btnEditQuiz.setActivated(false);
            }else if(selectedQuizzes.size() == 1){
                //編集・削除有効
                this.btnDeleteQuiz.setActivated(true);
                this.btnEditQuiz.setActivated(true);
            }else{
                //削除有効　編集無効
                this.btnDeleteQuiz.setActivated(true);
                this.btnEditQuiz.setActivated(false);
            }

        }else if(view.getTag().equals("back")){
            /*
            * 戻るボタン
            * makePackActivityの再読み込み
             */
            mainApplication.setSelectPack(true);
            this.mpActivity.reload();
        }else if(view.getTag().equals("deletePack")){
            /*
            * パック削除ボタン
            * パックidファイルの削除
             */
            this.mainApp.clearFile(this.mainApp.getPackId());
            List<String> lstPackDataFile = this.mainApp.getAllList();
            for (String i:lstPackDataFile) {
                String packId = i.substring(0,i.indexOf(","));
                if(packId.equals(this.mainApp.getPackId())){

                }
            }
            /*
            * パックデータファイルの該当行削除
             */
            String linePackData;
            String packId;
            for(int i = lstPackDataFile.size() - 1; i >= 0; i--){
                linePackData = lstPackDataFile.get(i);
                packId = linePackData.substring(0,linePackData.indexOf(","));
                if(packId.equals(this.mainApp.getPackId())){
                    lstPackDataFile.remove(i);
                }
            }
            this.mainApp.saveFileByList(this.mainApp.PACK_DATA_FILE_NAME, lstPackDataFile);

            //activity再起動
            mainApplication.setSelectPack(true);
            this.mpActivity.reload();

        }else if(view.getTag().equals("deleteQuiz")){
            /*
            * クイズ削除ボタン
             */
            List<String> lstPackIdFile = this.mainApp.readFileAsList(this.mainApp.getPackId());
            for (int i:this.selectedQuizzes) {
                lstPackIdFile.remove(i);
            }
            this.mainApp.saveFileByList(this.mainApp.getPackId(), lstPackIdFile);

            /*
            * クイズリスト再読み込み
             */
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.container, new editPackFragment());
        }else if(view.getTag().equals("editQuiz")){
            /*
            * クイズ編集ボタン
             */
            this.mainApp.setQuizNum(this.selectedQuizzes.get(0));

            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.addToBackStack(null);
            ft.replace(R.id.container, new editQuizFragment());
        }
    }


    public boolean canDelete(){
        boolean isDeleteConfirmed=false;
//toast
        return isDeleteConfirmed;
    }
}