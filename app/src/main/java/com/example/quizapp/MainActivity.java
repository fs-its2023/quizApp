package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //activity遷移ボタン
        Button button0 = findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), takeQuizPackActivity.class);
                startActivity(intent);
            }
        });
        //テス
<<<<<<< HEAD

=======
>>>>>>> c02405fc0dbc20f860469e80eb78c4a661f26ddd
        //ささがわです
        //岡部>_<
<<<<<<< HEAD

        //岡部

=======
        //岡部
>>>>>>> c02405fc0dbc20f860469e80eb78c4a661f26ddd
    }
}