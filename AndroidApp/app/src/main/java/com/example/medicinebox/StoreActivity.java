package com.example.medicinebox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class StoreActivity extends AppCompatActivity {

    String id;

    ImageView btnBack, btnMenu;

    TextView mediName, mediSlot, mediEffect, mediUse;
    ImageView mediPhoto;

    TextView takeType, textDay, takeDay, textStart, takeStart, textCycle, takeCycle, takeFre, takeTime1, takeTime2, takeTime3, takeExpire, storageNum;
    LinearLayout Takelayout, Timelayout;

    FloatingActionButton btnPilladd;

    boolean flag = false;
    boolean flag2 = false;
    boolean flag3 = false;
    boolean takef, storagef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_activity);

        // 세션 id 받아오기
        id = Session.getUserID(getApplicationContext());

        btnBack = findViewById(R.id.btnBack);
        btnMenu = findViewById(R.id.btnMenu);
        btnPilladd = findViewById(R.id.btnPilladd);

        mediName = findViewById(R.id.mediName);
        mediSlot = findViewById(R.id.slot);
        mediEffect = findViewById(R.id.mediEffect);
        mediUse = findViewById(R.id.mediUse);
        mediPhoto = findViewById(R.id.mediPhoto);

        takeType = findViewById(R.id.takeType);
        textDay = findViewById(R.id.textDay);
        takeDay = findViewById(R.id.takeDay);
        textStart = findViewById(R.id.textStart);
        takeStart = findViewById(R.id.takeStart);
        textCycle = findViewById(R.id.textCycle);
        takeCycle = findViewById(R.id.takeCycle);
        takeFre = findViewById(R.id.takeFre);
        takeTime1 = findViewById(R.id.takeTime1);
        takeTime2 = findViewById(R.id.takeTime2);
        takeTime3 = findViewById(R.id.takeTime3);
        takeExpire = findViewById(R.id.takeExpire);
        storageNum = findViewById(R.id.storageNum);

        Takelayout = findViewById(R.id.Takelayout);
        Timelayout = findViewById(R.id.Timelayout);

        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        final String num = intent.getExtras().getString("num");
        final String name = intent.getExtras().getString("name");
        final String slot = intent.getExtras().getString("slot");
        mediName.setText(name);
        mediSlot.setText(slot+"번");

        if (slot.equals("7")) {
            Takelayout.setVisibility(View.GONE);
            Timelayout.setVisibility(View.GONE);
            btnPilladd.setVisibility(View.GONE);
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL imgurl = new URL("http://www.medicinebox.site/project/medicine/img/"+name+".png");
                    Log.i("imgurl", String.valueOf(imgurl));
                    InputStream is = imgurl.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                        @Override
                        public void run() {
                            mediPhoto.setImageBitmap(bm);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    flag = mediname(name);
                    flag2 = expireload(slot);
                    flag3 = takedetail(id, num);
                    if (takeType.getText().equals("요일별")) {
                        textStart.setVisibility(View.GONE);
                        takeStart.setVisibility(View.GONE);
                        textCycle.setVisibility(View.GONE);
                        takeCycle.setVisibility(View.GONE);
                        takeday(id, num);
                    } else if (takeType.getText().equals("주기별")) {
                        textDay.setVisibility(View.GONE);
                        takeDay.setVisibility(View.GONE);
                        takecycle(id, num);
                    }
                    taketime(id, num);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
                Log.d("IN ASYNC", String.valueOf(flag));
            }
        });


        //메뉴 버튼
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(StoreActivity.this , btnMenu);

                MenuInflater inf = popup.getMenuInflater();
                inf.inflate(R.menu.menu, popup.getMenu());
                popup.show();

                //PopupMenu popup = new PopupMenu(getApplicationContext(),v);
                //getMenuInflater().inflate(R.menu.menu,popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.store_edit:
                                //Toast.makeText(getApplication(),"수정하기",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), EditMediActivity.class);
                                intent.putExtra("num", num);
                                intent.putExtra("name", mediName.getText().toString());
                                intent.putExtra("slot", slot);
                                intent.putExtra("storageNum",storageNum.getText().toString());
                                intent.putExtra("type", takeType.getText().toString());
                                intent.putExtra("start", takeStart.getText().toString());
                                intent.putExtra("cycle", takeCycle.getText().toString());
                                intent.putExtra("fre", takeFre.getText().toString());
                                intent.putExtra("expire", takeExpire.getText().toString());
                                intent.putExtra("time1", takeTime1.getText().toString());
                                intent.putExtra("time2", takeTime2.getText().toString());
                                intent.putExtra("time3", takeTime3.getText().toString());
                                startActivity(intent);
                                break;
                            case R.id.store_delete:
                                new AlertDialog.Builder(StoreActivity.this)
                                        .setTitle("정말 삭제하시겠습니까?")
                                        .setMessage("삭제 후 약을 모두 꺼내야 합니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which){
                                                AsyncTask.execute(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (slot.equals("7")) {
                                                            // 디비에서 삭제
                                                            storagef = storagedelete(num);
                                                            if (storagef == true) {
                                                                StoreActivity.this.runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Toast.makeText(getApplicationContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                        startActivity(intent);
                                                                    }
                                                                });
                                                            }

                                                        } else {
                                                            // 디비에서 삭제
                                                            takef = takedelete(num);
                                                            storagef = storagedelete(num);

                                                            // 잠금 해제 신호 송신 - slot : Integer.parseInt(slot)

                                                            int slotNum = Integer.parseInt(slot);
                                                            ConnDevice connDevice = new ConnDevice(StoreActivity.this);
                                                            connDevice.openSlot(slotNum);


                                                            if (takef == true && storagef == true) {

                                                                StoreActivity.this.runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        Toast.makeText(getApplicationContext(), "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                        startActivity(intent);
                                                                    }
                                                                });
                                                            }
                                                        }

                                                    }
                                                });

                                            }
                                        })
                                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which){
                                            }
                                        })
                                        .show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
            }
        });

        //약 추가
       btnPilladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 잠금 해제 신호 송신 - slot : Integer.parseInt(slot)

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        int slotNum = Integer.parseInt(slot);
                        ConnDevice connDevice = new ConnDevice(StoreActivity.this);
                        connDevice.openSlot(slotNum);
                    }
                });


                Toast.makeText(getApplicationContext(), "잠금이 해제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        TabHost tabHost = findViewById(R.id.tabHost) ;
        tabHost.setup();

        // 첫 번째 Tab. (탭 표시 텍스트:"TAB 1"), (페이지 뷰:"content1")
        TabHost.TabSpec ts1 = tabHost.newTabSpec("Tab Spec 1") ;
        ts1.setContent(R.id.tab1) ;
        ts1.setIndicator("의약품 정보") ;
        tabHost.addTab(ts1)  ;

        // 두 번째 Tab. (탭 표시 텍스트:"TAB 2"), (페이지 뷰:"content2")
        TabHost.TabSpec ts2 = tabHost.newTabSpec("Tab Spec 2") ;
        ts2.setContent(R.id.tab2) ;
        ts2.setIndicator("복용 정보") ;
        tabHost.addTab(ts2) ;


    }

    private boolean mediname(String name) throws JSONException {

        REST_API mediname = new REST_API("mediname");

        String json = "{\"name\" : \"" + name + "\"}";

        String result = mediname.post(json);
        Log.d("MEDIname", "result : " + result); //쿼리 결과값

        String medi_photo = null;
        String medi_effect = null;
        String medi_use = null;

        JSONArray jsonArray = new JSONArray(result);

        Log.i("resultjson", String.valueOf(jsonArray.length()));
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("MEDIname", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (jsonArray.length() == 0) {
            mediUse.setText("1일 1회 1정");
            return false;
        } else {
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                medi_photo = jsonObject.getString("medi_photo");
                medi_effect = jsonObject.getString("medi_effect");
                medi_use = jsonObject.getString("medi_use");


                mediEffect.setText(medi_effect);
                mediUse.setText(medi_use);

            }
            return true;
        }

        return false;
    }

    private boolean expireload(String slot) throws JSONException, ParseException {

        SimpleDateFormat old_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        old_format.setTimeZone(TimeZone.getTimeZone("KST"));
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy년 MM월 dd일");

        REST_API expireload = new REST_API("expireload");

        String json = "{\"slot\" : \"" + slot + "\"}";

        String result = expireload.post(json);
        Log.d("Expire", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);

        Log.i("resultjson", String.valueOf(jsonArray.length()));
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("Expire", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String storage_expire = jsonObject.getString("storage_expire");
                String storage_num = jsonObject.getString("storage_num");
                Date old_date = old_format.parse(storage_expire);
                //old_date.setTime ( old_date.getTime ( ) + ( (long) 1000 * 60 * 60 * 24 ) );
                old_date.setTime ( old_date.getTime ());
                String new_date = new_format.format(old_date);
                Log.i("expire", new_date);
                takeExpire.setText(new_date);
                storageNum.setText(storage_num);
            }
            return true;
        }

        return false;
    }

    private boolean takedetail(String id, String num) throws JSONException, ParseException {

        REST_API takedetail = new REST_API("takedetail");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";

        String result = takedetail.post(json);
        Log.d("takedetail", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);

        Log.i("resultjson", String.valueOf(jsonArray.length()));
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("takedetail", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String take_type = jsonObject.getString("take_type");
                String take_fre = jsonObject.getString("take_fre");

                takeType.setText(take_type);
                takeFre.setText(take_fre+"번");
                //takeDay.append(take_day+" ");
                //takeTime1.setText(take_time);
            }
            return true;
        }

        return false;
    }

    private boolean takeday(String id, String num) throws JSONException, ParseException {

        REST_API takeday = new REST_API("takeday");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";

        String result = takeday.post(json);
        Log.d("takeday", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);

        Log.i("resultjson", String.valueOf(jsonArray.length()));
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("takeday", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String take_day = jsonObject.getString("take_day");

                takeDay.append(take_day+" ");
            }
            return true;
        }

        return false;
    }

    private boolean takecycle(String id, String num) throws JSONException, ParseException {

        SimpleDateFormat old_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        old_format.setTimeZone(TimeZone.getTimeZone("KST"));
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy년 MM월 dd일");

        REST_API takecycle = new REST_API("takecycle");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";

        String result = takecycle.post(json);
        Log.d("takecycle", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);

        Log.i("resultjson", String.valueOf(jsonArray.length()));
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("takecycle", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String take_start = jsonObject.getString("take_start");
                String take_cycle = jsonObject.getString("take_cycle");

                Date old_date = old_format.parse(take_start);
                old_date.setTime ( old_date.getTime () );
                String new_date = new_format.format(old_date);

                takeStart.setText(new_date);
                takeCycle.setText(take_cycle+"일");
            }
            return true;
        }

        return false;
    }

    private boolean taketime(String id, String num) throws JSONException, ParseException {

        REST_API taketime = new REST_API("taketime");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";

        String result = taketime.post(json);
        Log.d("taketime", "result : " + result); //쿼리 결과값

        ArrayList<String> timeArray = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(result);

        Log.i("resultjson", String.valueOf(jsonArray.length()));
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("taketime", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String take_time = jsonObject.getString("take_time");

                timeArray.add(take_time);
            }

            if (timeArray.size() == 1){
                takeTime1.setText(timeArray.get(0));
            } else if (timeArray.size() == 2) {
                takeTime2.setVisibility(View.VISIBLE);

                takeTime1.setText(timeArray.get(0));
                takeTime2.setText(timeArray.get(1));
            } else if (timeArray.size() == 3) {
                takeTime2.setVisibility(View.VISIBLE);
                takeTime3.setVisibility(View.VISIBLE);

                takeTime1.setText(timeArray.get(0));
                takeTime2.setText(timeArray.get(1));
                takeTime3.setText(timeArray.get(2));
            }
            return true;
        }

        return false;
    }


    // take table에서 삭제
    private boolean takedelete(String num) {

        REST_API takedelete = new REST_API("takedelete");

        String json = "{\"num\" : \"" + num + "\"}";

        String result = takedelete.delete(json);
//        Log.d("LOGIN", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("takedelete", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("takedelete", "FAIL!!!!!");
            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("takedelete", "SUCCESS!!!!!");
            return true;
        } else if(result.equals("false\n")) {
            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return false;
    }

    // storage table에서 삭제
    private boolean storagedelete(String num) {

        REST_API storagedelete = new REST_API("storagedelete");

        String json = "{\"num\" : \"" + num + "\"}";

        String result = storagedelete.delete(json);
//        Log.d("LOGIN", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("storagedelete", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("storagedelete", "FAIL!!!!!");
            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("storagedelete", "SUCCESS!!!!!");
            return true;
        } else if(result.equals("false\n")) {
            StoreActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(StoreActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return false;
    }
}
