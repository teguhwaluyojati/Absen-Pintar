package com.example.absenpintar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class IzinActivity extends AppCompatActivity {

    String[] izin_untuk = {"Izin Upah Dibayar", "Tidak Dibayar", "Dibayar Setengah"};
    String[] alasan_izin = {"Work From Home", "Cuti", "WFH (Sakit)","Lainnya"};

    AutoCompleteTextView autoCompleteText;
    ArrayAdapter<String> adapterItems;

    Button btn_izin_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izin);

    autoCompleteText= findViewById(R.id.autoCompleteTextView3);
    adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item,izin_untuk );
    autoCompleteText.setAdapter(adapterItems);

    autoCompleteText=findViewById(R.id.autoCompleteTextView4);
    adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item, alasan_izin);
    autoCompleteText.setAdapter(adapterItems);




      //  btn_izin_back = findViewById(R.id.btn_izin_back);
        
        //Aksi Klik Button Back
    //    btn_izin_back.setOnClickListener(new View.OnClickListener() {
      //      @Override
        //    public void onClick(View v) {
          //      backToMainMenu();
            //}
        //});
    }

    //private void backToMainMenu() {
      //  Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    //}
}