package com.example.medicinebox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;


public class Device_auth extends Activity {

    LinearLayout step1, step2, step3, step4, step2_1;
    ImageView img1, img2, img3, img4, imgMain;
    ProgressBar pbar1, pbar2, pbar3, pbar4;
    Button btnStep2;

    String user_id, wifi_id, wifi_pw, device_id;

    Handler handler;
    Integer step;
    Thread tStep1, tStep3, tStep4;

    private final static String TAG = Device_auth.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_auth);

        step1 = findViewById(R.id.auth_layout1);
        step2 = findViewById(R.id.auth_layout2);
        step3 = findViewById(R.id.auth_layout3);
        step4 = findViewById(R.id.auth_layout4);
        step2_1 = findViewById(R.id.auth_layout2_1);
        img1 = findViewById(R.id.auth_imgview1);
        img2 = findViewById(R.id.auth_imgview2);
        img3 = findViewById(R.id.auth_imgview3);
        img4 = findViewById(R.id.auth_imgview4);
        imgMain = findViewById(R.id.auth_imgview_main);
        pbar1 = findViewById(R.id.auth_progress1);
        pbar2 = findViewById(R.id.auth_progress2);
        pbar3 = findViewById(R.id.auth_progress3);
        pbar4 = findViewById(R.id.auth_progress4);
        btnStep2 = findViewById(R.id.auth_btnStep2);


//        시작시엔 스텝 2, 3, 4 숨기기
        step2.setVisibility(View.INVISIBLE);
        step3.setVisibility(View.INVISIBLE);
        step4.setVisibility(View.INVISIBLE);
        step2_1.setVisibility(View.INVISIBLE);


        Intent getIntent = getIntent();
        wifi_id = getIntent.getStringExtra("wifi_id");
        wifi_pw = getIntent.getStringExtra("wifi_pw");
        device_id = getIntent.getStringExtra("device_id");
        user_id = Session.getUserID(getApplicationContext());
        step = 1;


        tStep1 = new Thread(new Runnable(){
            @Override
            public void run() {
                if(sendDeviceInfo(device_id, wifi_id, wifi_pw)) {
                    updateProgressStage(1);
                }

            }
        });

        tStep3 = new Thread(new Runnable() {
            @Override
            public void run() {
//                while(true) {
//                    if(!syncDevice(device_id)){
//                        toastMsg("서버에서 디바이스 접속 상태를 다시 확인중입니다.");
//                        handler.postDelayed(null, 1000);
//                    } else {
//                        updateProgressStage(3);
//                    }
////                }

                try {
                    while (true) {
                        if(getDeviceIp(device_id)) {
                            Log.d(TAG, "tStep4 : getDeviceIP SUCCESS!");
                            updateProgressStage(3);
                            break;
                        } else {
                            Log.d(TAG, "tStep4: RETRYING to GET device IP..");
                            Thread.sleep(500);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        tStep4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        if (deleteServerWifi(device_id)) {
                            Log.d(TAG, "tStep4 : delete wifi info on server SUCCESS!");
                            break;
                        } else {
                            Log.d(TAG, "tStep4: RETRYING to DELETE Wifi info on server..");
                            Thread.sleep(500);
                        }
                    }
                    Log.d(TAG, "tStep4: UPDATE USER DATA!!!");
                    while (true) {
                        if (updateUserdata(user_id, device_id)) {
                            Log.d(TAG, "tStep4: UPDATE UserData SUCCESS!!!");
                            updateProgressStage(4);
                            break;
                        } else {
                            Log.d(TAG, "tStep4: RETRYING to UPDATE UserData..");
                            Thread.sleep(500);
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnStep2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProgressStage(2);
                btnStep2.setEnabled(false);
            }
        });



        tStep1.start();

    }

    //    진행 단계 업데이트
    private void updateProgressStage(final int s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (s) {
                    case 1:
                        pbar1.setVisibility(View.GONE);
                        img1.setVisibility(View.VISIBLE);
                        step2.setVisibility(View.VISIBLE);
                        step2_1.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        step2_1.setVisibility(View.INVISIBLE);
                        pbar2.setVisibility(View.GONE);
                        img2.setVisibility(View.VISIBLE);
                        step3.setVisibility(View.VISIBLE);
                        tStep3.start();
                        break;
                    case 3:
                        pbar3.setVisibility(View.GONE);
                        img3.setVisibility(View.VISIBLE);
                        step4.setVisibility(View.VISIBLE);
                        tStep4.start();
                        break;
                    case 4:
                        pbar4.setVisibility(View.GONE);
                        img4.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getApplicationContext(), Splash_auth.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }


    private boolean sendDeviceInfo(String device_id, String wifi_id, String wifi_pw) {
        String json = "{ \"device_id\" : \"" + device_id + "\", \"wifi_id\" : \"" + wifi_id + "\", \"wifi_pw\" : \"" + wifi_pw + "\" }";

        REST_API conn_server = new REST_API("wifi");
        String result = conn_server.post(json);


        Log.d(TAG, "RETURN : " + result);
        if(result.equals("true\n")) {
            return true;
        } else if(result.equals("false")) {
            toastMsg("전송 실패! 다시 시도합니다.");
            return false;
        } else {
            toastMsg("전송 실패! 네트워크 환경이나 전송할 데이터를 확인해주세요.");
            Log.e(TAG, "return data : " + result);
            return false;
        }

    }

    private boolean syncDevice(String device_id) {
        String param = "device_id=" + device_id;

        REST_API conn_server = new REST_API("wifi");
        String result = "";
        int num = 0;

        try {
            while (true) {
                if (result.equals("[]\n")) {
                    Log.d(TAG, "syncDevice: matched!!");
                    return true;
                }
                if (num > 10) {
                    break;
                } else {
                    result = conn_server.get(param);
                    Thread.sleep(2000);

                    num++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    private boolean updateUserdata(String user_id, String device_id){
        String json = "{ \"user_id\" : \"" + user_id + "\" , \"user_device\" : \"" + device_id + "\" }";

        REST_API conn_server = new REST_API("account_addDevice");
        String result = conn_server.post(json);

        if(result.equals("true\n")) {
            return true;
        } else {
            toastMsg("정보 업데이트 실패 ");
            return false;
        }
    }

    private boolean getDeviceIp(String device_id) {
        String param = "device_id=" + device_id;

        REST_API conn_server = new REST_API("deviceIp");
        String result = "";
        int num = 0;

        try {
            while (true) {
                if (num > 90) {
                    break;
                } else {
                    result = conn_server.get(param);
                    if(result.contains("[")) {
                        result = result.replace("[", "");
                    }
                    if(result.contains("]")) {
                        result = result.replace("]", "");
                    }
                    JSONObject jsonObject = new JSONObject(result);
                    String device_ip = jsonObject.getString("device_ip");
                    device_ip = device_ip.trim();

                    if(device_ip.equals("") || device_ip == null || device_ip.equals("null")) {
                        Thread.sleep(2000);
                        num++;
                    } else {
                        Log.e("received device ip : ", device_ip);
                        Session.setDeviceIP(getApplicationContext(), device_ip);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private boolean deleteServerWifi(String device_id) {
        String param = "device_id=" + device_id;
        String json = "{ \"device_id\" : \"" + device_id + "\" }";

        REST_API conn_server = new REST_API("wifi");
        String result = conn_server.delete(json);
        result = result.trim();

        Log.d(TAG, "deleteServerWifi:"+result);
        if(result.equals("true")) {
            return true;
        } else {
            Log.e(TAG, "Fail to DELETE WIFI INFO on SERVER");
            return false;
        }
    }

    private void toastMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Device_auth.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }





















}
