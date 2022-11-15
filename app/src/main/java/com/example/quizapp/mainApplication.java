package com.example.quizapp;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class mainApplication extends Application {
    private static int packNum=0;
    private static List<String> allList = new ArrayList<String>();
    private static List<String> selectList = new ArrayList<String>();
    private static int quizNum;
    private static String packId;
    private static boolean fromMakePackActivity=false;
    private static boolean fromTakeQuizPackActivity =false;
    private static boolean fromTakeQuizFragment=false;
    private static boolean fromResultFragment=false;
    private static boolean selectPack=false;
    public static final String PACK_DATA_FILE_NAME = "packData";

    /*
    * ファイル削除
    * isRemainedでファイル自体を残すか選択
     */
    public void deleteFile(String fileName, boolean isRemained){
        if(isRemained){
            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName, false)) {
                //空白で置き換え
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            File file = new File(fileName);
            file.delete();
        }
    }

    /*
    *ファイルに書き込みをする機能
     */
    public void saveFile(String fileName, String strSaveData){
        try {
            /*
            *書き込みをするファイルの指定、指定されたファイルがなければ新規作成
             */
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE | MODE_APPEND);
            /*
            *指定したファイルに書き込み
             */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     *リストからファイルに書き込みをする機能
     */
    public void saveFileByList(String fileName, List<String> lstSaveData){
        try {
            /*
             *書き込みをするファイルの指定、指定されたファイルがなければ新規作成
             */
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE | MODE_APPEND);

            /*
             *指定したファイルに書き込み
             */
            String buffer;
            for(int i = 0; i < lstSaveData.size(); i++){
                buffer = lstSaveData.get(i) + "\n";
                fileOutputStream.write(buffer.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    *ファイルの読み込みをするメソッド String
    String型で全データ扱わないからいらない？
     */
    public String readFileAsText(String fileName){
        String text=null;
        try{
            /*
             *読み込むファイルの指定
             */
            FileInputStream fileInputStream = openFileInput(fileName);

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
    ・allListに最新のファイル情報を入れる
     */
    public List<String> readFileAsList(String fileName) {
        List<String> list=new ArrayList<>();
        try {
            /*
            *読み込むファイルの指定
             */
            FileInputStream fileInputStream = openFileInput(fileName);

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

    //selectリストの初期化する
    public static void deleteSelectList(){
        selectList.clear();
    }

    public static void setSelectList(List<String> inputSelectList) {
        mainApplication.selectList = inputSelectList;
    }

    public static List<String> getSelectList(){
        return mainApplication.selectList;
    }

    public static int getPackNum() {
        return packNum;
    }

    public static void setPackNum(int puckNum) {
        mainApplication.packNum=puckNum;
    }

    public List<String> getAllList() {
        allList.clear();
        allList=readFileAsList(PACK_DATA_FILE_NAME);
        return allList;
    }

    public static int getQuizNum() {
        return quizNum;
    }

    public static void setQuizNum(int quizNum) {
        mainApplication.quizNum = quizNum;
    }

    public static String getPackId() {
        return packId;
    }

    public static void setPackId(String packId) {
        mainApplication.packId = packId;
    }

    public static boolean getFromTakeQuizFragment() {
        return fromTakeQuizFragment;
    }

    public static void setFromTakeQuizFragment(boolean fromTakeQuizFragment) {
        mainApplication.fromTakeQuizFragment = fromTakeQuizFragment;
    }

    public static boolean getFromResultFragment() {
        return fromResultFragment;
    }

    public static void setFromResultFragment(boolean fromResultFragment) {
        mainApplication.fromResultFragment = fromResultFragment;
    }
    
    public static boolean getFromMakePackActivity() {
        return fromMakePackActivity;
    }

    public static void setFromMakePackActivity(boolean fromMakePackActivity) {
        mainApplication.fromMakePackActivity = fromMakePackActivity;
    }

    public static boolean getFromTakeQuizPackActivity() {
        return fromTakeQuizPackActivity;
    }

    public static void setFromTakeQuizPackActivity(boolean fromTakeQuizPackActivity) {
        mainApplication.fromTakeQuizPackActivity = fromTakeQuizPackActivity;
    }

    public static boolean getSelectPack() {
        return selectPack;
    }

    public static void setSelectPack(boolean selectPack) {
        mainApplication.selectPack = selectPack;
    }
}
