package com.example.quizapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class YesNoDialog extends DialogFragment {
    String title = getArguments().getString("title", "Dialog");
    String msg = getArguments().getString("msg", "Message");
    String yes = getArguments().getString("yes", "Yes");
    String no = getArguments().getString("no", "No");

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Fragment fragment = getParentFragment();
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // ボタンを押した時の処理
                    }
                })
                .setNegativeButton(no, null);
        return builder.create();
    }
}

