package com.example.myapplication4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class report extends AppCompatActivity {

    private Button mReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        mReport = findViewById(R.id.available_report);

        mReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openImagesActivity1();
            }
        });
    }
    private void openImagesActivity1(){
        Intent intent =new Intent(this,Retrive.class);
        startActivity(intent);
    }
}