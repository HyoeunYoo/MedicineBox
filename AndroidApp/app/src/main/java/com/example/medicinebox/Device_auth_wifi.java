package com.example.medicinebox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Device_auth_wifi extends Activity {

    EditText edtWifi, edtPasswd, edtDeviceId;
    String wifi_id, wifi_pw, device_id;
    Button btnNext;
    View baselayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_auth_wifi);


        edtWifi = findViewById(R.id.auth_edtWifi);
        edtPasswd = findViewById(R.id.auth_edtPasswd);
        edtDeviceId = findViewById(R.id.auth_edtDeviceId);
        btnNext = findViewById(R.id.auth_btnWifi);
        baselayout = findViewById(R.id.auth_wifi_baselayout);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifi_id = edtWifi.getText().toString();
                wifi_pw = edtPasswd.getText().toString();
                device_id = edtDeviceId.getText().toString();

                if(wifi_id.equals("")) {
                    Toast.makeText(getApplicationContext(), "wifi 정보를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                } else if(device_id.equals("")) {
                    Toast.makeText(getApplicationContext(), "디바이스 정보를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent = new Intent(getApplicationContext(), Device_init_network.class);
                    intent.putExtra("wifi_id", wifi_id);
                    intent.putExtra("wifi_pw", wifi_pw);
                    intent.putExtra("device_id", device_id);
                    startActivity(intent);
                }
            }
        });

        baselayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                baselayout.requestFocus();
                hideKeyboard();
                return false;
            }
        });

    }



    public void hideKeyboard() {
        View view = getCurrentFocus();
        if(view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
