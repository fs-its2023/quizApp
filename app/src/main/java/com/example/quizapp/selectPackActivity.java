package com.example.quizapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class selectPackActivity extends AppCompatActivity {

    mainApplication mainApplication;



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
    LinearLayout vLayout;
    boolean reverseChecker=false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pack);

        mainApplication=(com.example.quizapp.mainApplication) getApplication();

//        mainApplication.deleteFile("packData");
//        mainApplication.testPackDataFileMaker();

        vLayout=(LinearLayout) findViewById(R.id.verticalLayout);

        //mainApplication.saveFile("packData","1,パック名,20,パック1説明,ジャンル1\n");

        /*selectlistが空の場合のみ新着順表示　(Activity呼び出し前にリストを初期化*/
        if(mainApplication.getSelectList().size() == 0){
            allList= mainApplication.getAllList();
            /*selectListに値を入れ、新着順に変更*/
            mainApplication.deleteSelectList();
            Collections.reverse(allList);
            this.reverseChecker=true;
            mainApplication.setSelectList(allList);
        }

        selectList=mainApplication.getSelectList();

        pageCurrent=1;
        pageAll=((selectList.size()-1)/10)+1;

        mainApplication.setMustSelectPack(true);

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
        pageAll=((selectList.size()-1)/10)+1;

        /*ページ数の表示*/
        TextView page=findViewById(R.id.pageNum);
        page.setText(""+pageCurrent+"ページ/"+pageAll+"ページ");

        /*パックの情報を出力する*/
        vLayout.removeAllViews();
        if(reverseChecker){
            for(int i=10*pageCurrent-10;i<10*pageCurrent;i++){

                if(i>=selectList.size()){
                    break;
                }
                String[] listData=selectList.get(i).split(",");
                Button packButton=new Button(this);
                packButton.setText(listData[3]);
                packButton.setTextSize(20);
                packButton.setTag(selectList.size()-1-i);
                packButton.setOnClickListener(onClickSetPackId);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                packButton.setLayoutParams(buttonLayoutParams);
                vLayout.addView(packButton);
            }
        }else{
            for(int i=10*pageCurrent-10;i<10*pageCurrent;i++){

                if(i>=selectList.size()){
                    break;
                }
                String[] listData=selectList.get(i).split(",");
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
    }


    /*
     *パックが選択された時の処理
     */
    private View.OnClickListener onClickSetPackId=new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            String[] selectListData;
            if(reverseChecker){
                selectListData=selectList.get((selectList.size()-1)-(int)view.getTag()).split(",");
                reverseChecker=false;
            }else{
                selectListData=selectList.get((int)view.getTag()).split(",");
            }
            mainApplication.setPackNum((int)view.getTag());
            mainApplication.setQuizNum(Integer.parseInt(selectListData[2]));
            mainApplication.setPackId(selectListData[0]);
            mainApplication.setMustSelectPack(false);
            /*makePackActivityから来ていた場合*/
            if(mainApplication.getFromMakePackActivity()){
                mainApplication.setFromMakePackActivity(false);
                Intent intent =new Intent(getApplication(), makePackActivity.class);
                startActivity(intent);
                finish();
            }

            /*takeQuizPackActivityから来ていた場合*/
            if(mainApplication.getFromTakeQuizPackActivity()){
                mainApplication.setFromTakeQuizPackActivity(false);
                Intent intent =new Intent(getApplication(), takeQuizPackActivity.class);
                startActivity(intent);
                finish();
            }
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
    *検索ボタンが押された時の処理
     */
    public void showSearchFragment(View view){
        reverseChecker=false;
        /*レイアウトの上のボタンをすべて削除*/
        vLayout.removeAllViews();
        /*Activity上にある”検索ボタン”、”ページを変更するボタン”、”現在ページと全ページを表示しているテキストの削除”*/
        Button search=(Button) findViewById(R.id.search);
        Button backPage=(Button) findViewById(R.id.backPage);
        Button nextPage=(Button) findViewById(R.id.nextPage);
        TextView pageNum=(TextView) findViewById(R.id.pageNum);
        search.setVisibility(View.INVISIBLE);
        backPage.setVisibility(View.INVISIBLE);
        nextPage.setVisibility(View.INVISIBLE);
        pageNum.setVisibility(View.INVISIBLE);
        /*searchFragmentの表示*/
        searchFragment searchFragment=new searchFragment();
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.verticalLayout, searchFragment);
        transaction.commit();
    }


    /*
    *前の画面に戻る
     */
    public void backMenu(View view){
        finish();
    }

    public mainApplication getMainApplication(){
        return mainApplication;
    }

    public void reload(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}