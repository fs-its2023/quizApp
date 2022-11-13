package com.example.quizapp;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link editPackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class editPackFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private mainApplication mainApp;
    private makePackActivity mpActivity;
    private List<String> selectedQuizzes;

    public editPackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment editPackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static editPackFragment newInstance(String param1, String param2) {
        editPackFragment fragment = new editPackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        String[] lineDataFile = new String[6];
        /*
        * 該当行の特定
         */
        for(int i = 0; i < lstPackDataFile.size(); i++){
            lineDataFile = lstPackDataFile.get(i).split(",");
            if(lineDataFile[0].equals(this.mainApp.getPackId())){
                break;
            }
        }
        this.mpActivity.setPackTitle(lineDataFile[2]);
        this.mpActivity.setQuizTotalNum(Integer.parseInt(lineDataFile[3]));
        this.mpActivity.setPackIntroduction(lineDataFile[4]);
        this.mpActivity.setPackGenre(lineDataFile[5]);

        /*
        * クイズ情報読み込み
         */
        List<String> lstPackIdFile = this.mainApp.readFileAsList(this.mainApp.getPackId());
        String[] lineIdFile = new String[6];
        String quizSentence;
        for(int i = 0; i < lstPackIdFile.size(); i++){
            lineIdFile = lstPackIdFile.get(i).split(",");
            quizSentence = lineIdFile[0];
            /*
            * ボタン作成
             */
        }
    }

    public boolean canDelete(){
        boolean isDeleteConfirmed=false;

        return isDeleteConfirmed;
    }
}