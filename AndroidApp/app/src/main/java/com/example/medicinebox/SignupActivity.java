package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignupActivity extends AppCompatActivity {

    Button btnSignup;
    private EditText editName, editId, editPwd, editPwd2, editPhone;

    String name, id, pwd, pwd2, phone;
    boolean flag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        btnSignup = findViewById(R.id.btnSignup);

        editName = findViewById(R.id.editName);
        editId = findViewById(R.id.editId);
        editPwd = findViewById(R.id.editPwd);
        editPwd2 = findViewById(R.id.editPwd2);
        editPhone = findViewById(R.id.editPhone);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editName.getText().toString();
                id = editId.getText().toString();
                pwd = editPwd.getText().toString();
                pwd2 = editPwd2.getText().toString();
                phone = editPhone.getText().toString();

                if (pwd.equals(pwd2)) {
                    AsyncTask.execute(new Runnable() {          // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
                        @Override
                        public void run() {
                            flag = signup(name, id, pwd, phone);
                            Log.d("IN ASYNC", String.valueOf(flag));
                            if(flag) {
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                SignupActivity.this.finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),"비밀번호가 다릅니다.",Toast.LENGTH_LONG).show();
                }


            }
        });
    }


    private boolean signup(String name, String id, String pw, String phone) {

        REST_API signup = new REST_API("signup");

        String json = "{\"name\" : \"" + name + "\", \"id\" : \"" + id + "\", \"password\" : \"" + pw + "\", \"phone\" : \"" + phone + "\"}";
        // json에서 변수명도 큰따옴표로 감싸야함.

        String result = signup.post(json);
        Log.d("SIGNUP", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("SIGNUP", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            SignupActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SignupActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("SIGNUP", "FAIL!!!!!");
            SignupActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SignupActivity.this, "이미 사용중인 아이디 입니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("SIGNUP", "SUCCESS!!!!!");
            SignupActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SignupActivity.this, "가입 되었습니다.", Toast.LENGTH_LONG).show();
                }
            });
            return true;
        } else if(result.equals("false\n")) {
            SignupActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SignupActivity.this, "이미 사용중인 아이디 입니다.", Toast.LENGTH_LONG).show();
                }
            });
        }

        return false;
    }
}
