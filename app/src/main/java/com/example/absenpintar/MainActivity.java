package com.example.absenpintar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_masuk, btn_keluar, btn_izin, btn_riwayat, btn_lainyna;
    Dialog dialog;
    TextView tvNama, tvNo, tvFoto;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_masuk = findViewById(R.id.btn_masuk);
        btn_keluar = findViewById(R.id.btn_keluar);
        btn_riwayat = findViewById(R.id.btn_riwayat);
        btn_lainyna = findViewById(R.id.btn_lainnya);
        tvNama = findViewById(R.id.tv_nama);
        tvNo = findViewById(R.id.tv_nidn);

        dialog=new Dialog(this);

        sharedPreferences = getSharedPreferences("absen.conf", MODE_PRIVATE);
        tvNama.setText(sharedPreferences.getString("shared_nama",null));
        tvNo.setText(sharedPreferences.getString("shared_nidn",null));
        tvFoto.setText(sharedPreferences.getString("shared_foto", null));


        //Aksi Klik Masuk
        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, QRCode.class));

            }
        });

        //Aksi Klik keluar
        btn_keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, QRCode.class));

            }
        });


        //Aksi Klik lainnya
        btn_lainyna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }



        private void openRiwayatDialog () {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.100.12/userWeb/"));
            startActivity(browserIntent);
        }

    }

