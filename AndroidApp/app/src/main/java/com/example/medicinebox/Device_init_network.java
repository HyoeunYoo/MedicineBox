package com.example.medicinebox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class Device_init_network extends Activity {

    Button btnHotspotNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_auth_network);

        btnHotspotNext = findViewById(R.id.auth_btnHotspot);

        Intent intent = getIntent();
        final String wifi_id = intent.getStringExtra("wifi_id");
        final String wifi_pw = intent.getStringExtra("wifi_pw");
        final String device_id = intent.getStringExtra("device_id");


        btnHotspotNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Device_auth.class);
                intent.putExtra("wifi_id", wifi_id);
                intent.putExtra("wifi_pw", wifi_pw);
                intent.putExtra("device_id", device_id);
                startActivity(intent);
            }
        });

    }
}
