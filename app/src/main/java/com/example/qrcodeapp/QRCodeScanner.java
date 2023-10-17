package com.example.qrcodeapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeScanner extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scannerView;
    private static final int CAMERA_PERMISSION_REQUEST = 101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_r_code_scanner, container, false);

        scannerView = view.findViewById(R.id.scannerView);

        // Request camera permission
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        }

        Button scanButton = view.findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the QR code scanner
                scannerView.setResultHandler(QRCodeScanner.this);
                scannerView.startCamera();
            }
        });

        return view;
    }

    @Override
    public void handleResult(Result result) {
        // Handle the QR code scan result here
        String qrCodeText = result.getText();

        // Display the scanned result (customize as needed)
        Toast.makeText(requireContext(), "Scanned Successfully!!! ", Toast.LENGTH_LONG).show();

        try {
            Uri uri = Uri.parse(qrCodeText);

            if(uri.toString().contains("https://")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }else {
                Intent intent = new Intent(getContext(),QRContent.class);
                intent.putExtra("content",qrCodeText);
                startActivity(intent);
            }
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // Optionally, you can stop the camera after a successful scan
        scannerView.stopCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start the camera
                scannerView.setResultHandler(QRCodeScanner.this);
                scannerView.startCamera();
            } else {
                // Permission denied, show a message
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Pause the scanner when the Fragment is paused
        scannerView.stopCamera();
    }
}
