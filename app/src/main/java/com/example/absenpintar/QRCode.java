package com.example.absenpintar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.BarcodeFormat;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.qrcode.QRCodeWriter;

public class QRCode extends AppCompatActivity {

    SharedPreferences sharedPreferences;
     TextView TextV;
     ImageView imageView;
     Button btnQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        TextV = findViewById(R.id.edText);
        imageView = findViewById(R.id.imageView);
        btnQr = findViewById(R.id.btn_msk);
        sharedPreferences = getSharedPreferences("absen.conf", MODE_PRIVATE);
        TextV.setText(sharedPreferences.getString("shared_fix",null));

    }
    public void QRCodeButton(View view){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(TextV.getText().toString(), BarcodeFormat.QR_CODE, 200, 200);
            Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.RGB_565);
            for (int x = 0; x<200; x++){
                for (int y=0; y<200; y++){
                    bitmap.setPixel(x,y,bitMatrix.get(x,y)? Color.BLACK : Color.WHITE);
                }
            }
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}