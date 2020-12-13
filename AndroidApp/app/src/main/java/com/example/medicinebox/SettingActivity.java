package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class SettingActivity extends AppCompatActivity {

    ImageView btnBack, btnHome;
    Switch switchAlarm;
    TextView btnAccount, btnLogout, btnLeave;

    String id;
    int alarm;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);
        switchAlarm = findViewById(R.id.switchAlarm);
        btnAccount = findViewById(R.id.btnAccount);
        btnLogout = findViewById(R.id.btnLogout);
        btnLeave = findViewById(R.id.btnLeave);

        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //홈
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switchAlarm.setChecked(true);

        // 디비대로 알림 설정
        AsyncTask.execute(new Runnable() {          // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
            @Override
            public void run() {
                id = Session.getUserID(getApplicationContext());
                try {
                    alarm = alarmload(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.d("IN ASYNC", String.valueOf(alarm));
                if (alarm == 1) {
                    Log.d("IN ASYNC", String.valueOf(alarm));

                    SettingActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                        @Override
                        public void run() {
                            switchAlarm.setChecked(true);
                        }
                    });
                } else {
                    Log.d("IN ASYNC", String.valueOf(alarm));

                    SettingActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                        @Override
                        public void run() {
                            switchAlarm.setChecked(false);
                        }
                    });
                }
            }
        });

        //알림 설정 바꾸기
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("알림","활성화");
                    AsyncTask.execute(new Runnable() {          // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
                        @Override
                        public void run() {
                            id = Session.getUserID(getApplicationContext());
                            flag = alarmedit(id, 1);
                            Log.d("IN ASYNC", String.valueOf(flag));
                        }
                    });
                } else {
                    Log.i("알림","비활성화");
                    AsyncTask.execute(new Runnable() {          // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
                        @Override
                        public void run() {
                            id = Session.getUserID(getApplicationContext());
                            flag = alarmedit(id, 0);
                            Log.d("IN ASYNC", String.valueOf(flag));
                        }
                    });
                }
            }
        });

        //내정보수정
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
            }
        });

        //로그아웃
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.clearUserData(getApplicationContext());                                     // 사용자 데이터 삭제
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);           // login activity로 이동
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);   // 새로운 액티비티 태스크? 만들고 이미 있던 스택 삭제
                startActivity(intent);
            }
        });

        //탈퇴
        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaveActivity.class);
                startActivity(intent);
            }
        });
    }


    private int alarmload(String id) throws JSONException {
        REST_API alarmload = new REST_API("alarmload");

        String json = "{\"id\" : \"" + id + "\"}";

        String result = alarmload.post(json);
        Log.d("ALARMload", "result : " + result); //쿼리 결과값

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("ALARMload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            SettingActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SettingActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("ALARMload", "FAIL!!!!!");
            SettingActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SettingActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
            return 0;
        } else if(result != null) {
            Log.d("ALARMload", "SUCCESS!!!!!");

            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int alarm = jsonObject.getInt("user_alarm");
                Log.d("result", String.valueOf(alarm));

                if (alarm == 1) {
                    return 1;
                }
                else
                    return 0;
            }
        }

        return 1;
    }

    private boolean alarmedit(String id, int alarm) {

        REST_API alarmedit = new REST_API("alarmedit");

        String json = "{\"id\" : \"" + id + "\", \"alarm\" : \"" + alarm + "\"}";
        // json에서 변수명도 큰따옴표로 감싸야함.

        String result = alarmedit.put(json);
        Log.d("ALARM", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("ALARM", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            SettingActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SettingActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("ALARM", "FAIL!!!!!");
            SettingActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SettingActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("ALARM", "SUCCESS!!!!!");
            return true;
        }

        return false;
    }



}


