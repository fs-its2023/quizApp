package com.example.quizapp;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class mainApplication extends Application {
    private int packNum=0;
    private String fileName = "packData.csvのことかしら";
    private List<String> allList = new ArrayList<String>();
    private List<String> selectList = new ArrayList<String>();
    private int quizNum;
    private String puckId="0";
    private boolean fromMakePackActivity=false;
    private boolean fromTakeQuizFragment=false;
    private boolean fromResultFragment=false;
    private String PACK_DATA_PATH = "packData.csv";//パスでなくファイル名を使うならfileNameだけでいい

    public void setSelectList(List<String> inputSelectList) {
        this.selectList = inputSelectList;
    }

    public List<String> getSelectList(){
        return this.selectList;
    }

    public int getPackNum() {
        return packNum;
    }

    public void setPackNum(int puckNum) {
        this.packNum=puckNum;
    }

    //修正必要
    //allListに代入→returnでよさそう？　
    public List<String> getAllList() {
        String str= readFileAsText(fileName);
        return allList;
    }

    //修正必要
    //いる？
    public void setAllList(){
        deleteFile(fileName);
        saveFile(fileName, "0,ワンピースクイズ,50,ワンピースのクイズです,漫画\n");
    }

    /*
    *ファイルに書き込みをする機能
     */
    public void saveFile(String file, String str){
        try {
            /*
            *書き込みをするファイルの指定、指定されたファイルがなければ新規作成
             */
            FileOutputStream fileOutputStream = openFileOutput(file, MODE_PRIVATE | MODE_APPEND);

            /*
            *指定したファイルに書き込み
             */
            fileOutputStream.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *ファイルの読み込みをするメソッド String
    String型で全データ扱わないからいらない？
     */
    public String readFileAsText(String file){
        String text=null;
        try{
            /*
             *読み込むファイルの指定
             */
            FileInputStream fileInputStream = openFileInput(file);

            /*
             *指定したファイルを読み込む機能を使うために宣言
             */
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));

            /*
             *一行ずつ読み込んだファイルを入れる変数
             */
            String lineBuffer;

            /*
             *指定したファイルを一行ずつ読み込む繰り返し
             */
            while (true){
                lineBuffer = reader.readLine();
                if (lineBuffer != null){
                    allList.add(lineBuffer);
                    text += lineBuffer;
                }
                else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return text;
    }

    /*
    *ファイルの読み込みをするメソッド　List
    ・allListを作る
    ・クイズ一覧のリストを作る？
     */
    public List<String> readFileAsList(String file) {
        List<String> list=new ArrayList<>();
        try {
            /*
            *読み込むファイルの指定
             */
            FileInputStream fileInputStream = openFileInput(file);

            /*
            *指定したファイルを読み込む機能を使うために宣言
             */
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));

            /*
            *一行ずつ読み込んだファイルを入れる変数
             */
            String lineBuffer;

            /*
            *指定したファイルを一行ずつ読み込む繰り返し
             */
            while (true){
                lineBuffer = reader.readLine();
                if (lineBuffer != null){
                    list.add(lineBuffer);
                }
                else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public int getQuizNum() {
        return quizNum;
    }

    public void setQuizNum(int quizNum) {
        this.quizNum = quizNum;
    }

    public String getPuckId() {
        return puckId;
    }

    public void setPuckId(String puckId) {
        this.puckId = puckId;
    }

    public boolean getFromTakeQuizFragment() {
        return fromTakeQuizFragment;
    }

    public void setFromTakeQuizFragment(boolean fromTakeQuizFragment) {
        this.fromTakeQuizFragment = fromTakeQuizFragment;
    }

    public boolean getFromResultFragment() {
        return fromResultFragment;
    }

    public void setFromResultFragment(boolean fromResultFragment) {
        this.fromResultFragment = fromResultFragment;
    }
    
    public boolean getFromMakePackActivity() {
        return fromMakePackActivity;
    }

    public void setFromMakePackActivity(boolean fromMakePackActivity) {
        this.fromMakePackActivity = fromMakePackActivity;
    }

}
