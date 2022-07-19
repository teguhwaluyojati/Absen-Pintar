package com.example.absenpintar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class activity_login extends AppCompatActivity {
    private Button login;
    private EditText nidn,pass;
    private ProgressBar loading;


    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String shared_nidn, shared_nama, shared_foto;
    private RelativeLayout loading_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences("absen.conf", Context.MODE_PRIVATE);
        editor = pref.edit();

        shared_nidn = pref.getString("shared_nidn","");
        clsGlobal.shared_nidn = shared_nidn;

        shared_nama = pref.getString("shared_nama","");
        clsGlobal.shared_nama = shared_nama;

        shared_foto = pref.getString("shared_foto","");
        clsGlobal.shared_foto = shared_foto;

        nidn = findViewById(R.id.et_nidn_login);
        pass = findViewById(R.id.et_pass_login);

        loading = findViewById(R.id.login_loading);
        loading_screen = findViewById(R.id.loading_screen);

        login = findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempt_login();
            }
        });
    }

    public void attempt_login(){
        String nidn_z       = nidn.getText().toString();
        String pass_z       = pass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(nidn_z)) {
            nidn.setError("Masukkan NIK terlebih dahulu!");
            focusView = nidn;
            cancel = true;
        }else if (TextUtils.isEmpty(pass_z)) {
            pass.setError("Password harus di isi!");
            focusView = pass;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }else{
            login.setVisibility(View.GONE);
            loading_screen.animate().alpha(0.5f).setDuration(500);
            loading.setVisibility(View.VISIBLE);
            nidn.setEnabled(false);
            pass.setEnabled(false);
            user_login(nidn_z,pass_z);
            //Toast.makeText(this, "Goot to go", Toast.LENGTH_SHORT).show();
        }

    }


    private void user_login(final String nidn_z, final String pass_z){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, clsGlobal.LOGIN_USER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("respone_new",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");

                    if (success.equals("1")){
                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String nidn_z = object.getString("nidn").trim();
                            String nama = object.getString("nama").trim();
                            String foto = object.getString("foto").trim();

                            editor.putString("shared_nidn",nidn_z);
                            editor.putString("shared_nama",nama);
                            editor.putString("shared_foto",foto);
                            editor.putString("shared_fix",nidn_z+"#"+nama);
                            editor.putString("shared_foto_fix","http://192.168.100.12/adminWeb/file/"+foto);
                            editor.apply();

                            new CountDownTimer(2500, 4000) {

                                @Override
                                public void onTick(long millisUntilFinished) {
                                }
                                @Override
                                public void onFinish() {
                                    loading_screen.animate().alpha(0f).setDuration(500);
                                    loading.setVisibility(View.GONE);
                                    login.setVisibility(View.VISIBLE);
                                    nidn.setEnabled(true);
                                    pass.setEnabled(true);
                                    Toast.makeText(activity_login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(activity_login.this,MainActivity.class));
                                    finish();
                                }

                            }.start();

                        }
                    }else {
                        new CountDownTimer(2500, 4000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                            }
                            @Override
                            public void onFinish() {
                                loading_screen.animate().alpha(0f).setDuration(500);
                                loading.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                                nidn.setEnabled(true);
                                pass.setEnabled(true);
                                Toast.makeText(activity_login.this, "Incorrect details", Toast.LENGTH_SHORT).show();
                                Toast.makeText(activity_login.this, "Please try again", Toast.LENGTH_SHORT).show();
                            }

                        }.start();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    new CountDownTimer(2500, 4000) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                        }
                        @Override
                        public void onFinish() {
                            loading_screen.animate().alpha(0f).setDuration(500);
                            loading.setVisibility(View.GONE);
                            login.setVisibility(View.VISIBLE);
                            nidn.setEnabled(true);
                            pass.setEnabled(true);
                            Toast.makeText(activity_login.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                            Toast.makeText(activity_login.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }.start();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CountDownTimer(2500, 4000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                            }
                            @Override
                            public void onFinish() {
                                loading_screen.animate().alpha(0f).setDuration(500);
                                loading.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                                nidn.setEnabled(true);
                                pass.setEnabled(true);
                                Toast.makeText(activity_login.this, "Failed to Login", Toast.LENGTH_SHORT).show();
                                Toast.makeText(activity_login.this, "Please try again", Toast.LENGTH_SHORT).show();
                            }

                        }.start();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nidn",nidn_z);
                params.put("pass",pass_z);
                return params;
            }
        };

        RequestQueue requestQueus = Volley.newRequestQueue(this);
        requestQueus.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(activity_login.this,MainActivity.class));
        finish();
    }
}