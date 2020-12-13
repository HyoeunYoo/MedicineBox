package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_auth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_auth);

        Handler hd = new Handler();
        hd.postDelayed(new Splash_auth.splashhandler(), 3000);
    }

    public class splashhandler implements Runnable  {
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class));           // 로딩이 끝난 후 MainActivity로 이동
            Splash_auth.this.finish();                                                       // 로딩페이지를 activity stack에서 제거
        }
    }

    @Override
    public void onBackPressed() {
        // 초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게함
    }
}
