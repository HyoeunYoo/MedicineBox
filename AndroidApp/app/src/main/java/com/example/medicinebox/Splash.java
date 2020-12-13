package com.example.medicinebox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

public class Splash extends Activity {

    boolean login = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Handler hd = new Handler();

        if(Session.getUserID(getApplicationContext()).length() != 0) {// 로그인 정보가 있을 때
            //Log.d("session : ",Session.getUserData(getApplicationContext()));
            login = true;
        }
        hd.postDelayed(new splashhandler(), 3000);                              // 3초 후에 splashhandler 실행

    }


    public class splashhandler implements Runnable  {
        public void run() {
            Intent intent;
            if(login) {                                                                 // 로그인 정보가 있을 때
                intent = new Intent(getApplication(), MainActivity.class);              // MainActivity로 이동
            } else {                                                                    // 로그인 정보가 없을 때
                intent = new Intent(getApplication(), LoginActivity.class);             // loginActivity로 이동
            }
            startActivity(intent);                                                      // 지정한 액티비티로 이동
            Splash.this.finish();                                                       // 로딩페이지를 activity stack에서 제거
        }
    }



    @Override
    public void onBackPressed() {
        // 초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게함
    }
}
