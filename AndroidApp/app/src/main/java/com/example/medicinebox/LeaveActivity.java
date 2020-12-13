package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LeaveActivity extends AppCompatActivity {

    ImageView btnBack, btnHome;
    EditText editPwd;
    Button btnLeave;

    String id, passwd;
    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_activity);

        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);
        btnLeave = findViewById(R.id.btnLeave);
        editPwd = findViewById(R.id.editPwd);

        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //홈으로
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //탈퇴
        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = Session.getUserID(getApplicationContext());
                passwd = editPwd.getText().toString();

                if(passwd.equals("")) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                AsyncTask.execute(new Runnable() {          // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
                    @Override
                    public void run() {
                        flag = leave(id, passwd);
                        Log.d("IN ASYNC", String.valueOf(flag));
                        if(flag) {
                            LeaveActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    Toast.makeText(LeaveActivity.this, "탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Session.clearUserData(getApplicationContext());                                     // 사용자 데이터 삭제
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);           // login activity로 이동
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);   // 새로운 액티비티 태스크? 만들고 이미 있던 스택 삭제
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }


    private boolean leave(String id, String pw) {

        REST_API leave = new REST_API("leave");

        String json = "{\"id\" : \"" + id + "\", \"password\" : \"" + pw + "\"}";               // json에서 변수명도 큰따옴표로 감싸야함.

        String result = leave.delete(json);
//        Log.d("LOGIN", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("LEAVE", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            LeaveActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(LeaveActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("LEAVE", "FAIL!!!!!");
            LeaveActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(LeaveActivity.this, "비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("LEAVE", "SUCCESS!!!!!");
            return true;
        } else if(result.equals("false\n")) {
            LeaveActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(LeaveActivity.this, "비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return false;
    }
}
