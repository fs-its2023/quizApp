package com.example.quizapp;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class selectPackActivity extends AppCompatActivity {

    mainApplication mainApplication;

    LinearLayout vLayout;

    /*
     *フィールドリストの定義
     */
    List<String> allList=new ArrayList<String>();
    List<String> selectList=new ArrayList<String>();

    /*
     *フィールド変数の定義
     */
    int pageCurrent;
    int pageAll;
    int pageInitialNum;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pack);

        mainApplication=(com.example.quizapp.mainApplication) getApplication();

        mainApplication.deleteFile("packData.csv");
        for(int i=1;i<=20;i++){
            mainApplication.saveFile("packData.csv",""+i+",パック名"+i+",20,パック"+i+"説明,ジャンル1\n");
        }

        allList= mainApplication.getAllList();
        /*selectListに値を入れ、新着順に変更*/
        mainApplication.deleteSelectList();
        Collections.reverse(allList);
        mainApplication.setSelectList(allList);

        selectList=mainApplication.getSelectList();

        pageCurrent=1;
        pageAll=((selectList.size()-1)/10)+1;

        /*パック情報の表示*/
        showPackList();
    }



    /*
     *パックの情報を表示する
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showPackList(){
        /*selectListの取得*/
        selectList=mainApplication.getSelectList();

        /*ページ数を表示するために変数に値を代入*/


        /*レイアウトの取得*/
        vLayout=findViewById(R.id.verticalLayout);

        /*ページ数の表示*/
        TextView page=findViewById(R.id.pageNum);
        page.setText(""+pageCurrent+"ページ/"+pageAll+"ページ");

        /*パックの情報を出力する（修正必要）*/
        for(int i=10*pageCurrent-10;i<10*pageCurrent;i++){
            String[] listData=selectList.get(i).split(",");  //ここが悪さしてそう

            //ここより下、確認してないけどなんかミスってそう


                Button packButton=new Button(this);
                packButton.setText(listData[3]);
                packButton.setTextSize(20);
                packButton.setTag(i);
                packButton.setOnClickListener(onClickSetPackId);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                packButton.setLayoutParams(buttonLayoutParams);
                vLayout.addView(packButton);
        }

    }


    /*
     *パックが選択された時の処理
     */
    private View.OnClickListener onClickSetPackId=new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };


    /*
    *次のページに行く処理と前のページに戻る処理
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextPage(View view){
        if(pageCurrent<pageAll){
            this.pageCurrent++;
            showPackList();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void backPage(View view){
        if(1<pageCurrent){
            this.pageCurrent--;
            showPackList();
        }
    }


    /*
    *前の画面に戻る
     */
    public void backMenu(View view){
        finish();
    }

}