package com.example.qrcodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class QRContent extends AppCompatActivity {

    TextView txtContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcontent);

        txtContent = findViewById(R.id.txtContent);

        txtContent.setText(getIntent().getStringExtra("content"));
    }
}