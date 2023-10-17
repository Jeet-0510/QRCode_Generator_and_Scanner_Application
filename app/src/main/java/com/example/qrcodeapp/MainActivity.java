package com.example.qrcodeapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.qrgenerator));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.qrscanner));

        bottomNavigation.show(1, true);
        loadFrame(new QRCodeGenerator(),true);

        bottomNavigation.setOnClickMenuListener(model -> {

            switch (model.getId()){

                case 1:
                    loadFrame(new QRCodeGenerator(),false);
                    break;

                case 2:
                    loadFrame(new QRCodeScanner(),false);
            }
            return null;
        });
    }

    void loadFrame(Fragment FragmentUser, Boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (flag) {
            ft.add(R.id.frameLayout, FragmentUser);
        } else {
            ft.replace(R.id.frameLayout, FragmentUser);
        }
        ft.commit();
    }
}
