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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AccountActivity extends AppCompatActivity {

    ImageView btnBack, btnHome;
    Button btnEdit;
    private EditText editName, editOldpwd,  editNewpwd, editNewpwd2, editPhone;

    String name, id, oldpwd, newpwd, newpwd2, phone;
    boolean flag = false;
    boolean flag2 = false;
    String[] array = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);
        btnEdit = findViewById(R.id.btnEdit);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editName = findViewById(R.id.editName);
        editOldpwd = findViewById(R.id.editOldpwd);
        editNewpwd = findViewById(R.id.editNewpwd);
        editNewpwd2 = findViewById(R.id.editNewpwd2);
        editPhone = findViewById(R.id.editPhone);


        AsyncTask.execute(new Runnable() {          // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
            @Override
            public void run() {
                id = Session.getUserID(getApplicationContext());
                try {
                    flag = accountload(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("IN ASYNC", String.valueOf(flag));
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("session",Session.getUserID(getApplicationContext()));
                id = Session.getUserID(getApplicationContext());
                name = editName.getText().toString();
                oldpwd = editOldpwd.getText().toString();
                newpwd = editNewpwd.getText().toString();
                newpwd2 = editNewpwd2.getText().toString();
                phone = editPhone.getText().toString();

                if (newpwd.equals(newpwd2)) {
                    AsyncTask.execute(new Runnable() {          // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
                        @Override
                        public void run() {
                            flag = accountchk(id, oldpwd);
                            Log.d("IN ASYNC", String.valueOf(flag));
                            if(flag) {
                                flag2 = account(id, name, newpwd, phone);
                                if(flag2) {
                                    AccountActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                        @Override
                                        public void run() {
                                            Toast.makeText(AccountActivity.this, "수정되었습니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                                    startActivity(intent);
                                    AccountActivity.this.finish();
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),"새 비밀번호가 다릅니다.",Toast.LENGTH_LONG).show();
                }



            }
        });
    }

    private boolean accountload(String id) throws JSONException {
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);

        REST_API accountload = new REST_API("accountload");

        String json = "{\"id\" : \"" + id + "\"}";

        String result = accountload.post(json);
        Log.d("ACCOUNTload", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0 ; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String userName = jsonObject.getString("user_name");
            String userPhone = jsonObject.getString("user_phone");

            editName.setText(userName);
            editPhone.setText(userPhone);
        }


        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("ACCOUNTload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            AccountActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AccountActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("ACCOUNTload", "FAIL!!!!!");
            AccountActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AccountActivity.this, "ID 또는 Password를 확인해 주세요", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result != null) {
            Log.d("ACCOUNTload", "SUCCESS!!!!!");
            return true;
        }

        return false;
    }

    private boolean accountchk(String id, String oldpwd) {
        REST_API accountchk = new REST_API("accountchk");

        String json = "{\"id\" : \"" + id + "\", \"oldpwd\" : \"" + oldpwd + "\"}";

        String result = accountchk.post(json);

        Log.d("ACCOUNT", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("ACCOUNTchk", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            AccountActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AccountActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("ACCOUNTchk", "FAIL!!!!!");
            AccountActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AccountActivity.this, "기존 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("ACCOUNTchk", "SUCCESS!!!!!");
            return true;
        } else if(result.equals("false\n")) {
            AccountActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AccountActivity.this, "기존 비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                }
            });
        }

        return false;
    }


    private boolean account(String id, String name, String newpwd, String phone) {

        REST_API account = new REST_API("account");

        String json = "{\"id\" : \"" + id + "\", \"name\" : \"" + name + "\" , \"newpwd\" : \"" + newpwd + "\", \"phone\" : \"" + phone + "\"}";
        // json에서 변수명도 큰따옴표로 감싸야함.

        String result = account.put(json);
        Log.d("ACCOUNT", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("ACCOUNT", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            AccountActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AccountActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("ACCOUNT", "FAIL!!!!!");
            AccountActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AccountActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("ACCOUNT", "SUCCESS!!!!!");
            return true;
        }

        return false;
    }
}
