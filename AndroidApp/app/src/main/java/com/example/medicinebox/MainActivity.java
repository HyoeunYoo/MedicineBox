package com.example.medicinebox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {


/*    LinearLayout bHome, bSearch, bSetting;
    ImageButton bBtnHome, bBtnSearch, bBtnSetting;
    View bottomBar;*/

//    firebase 관련
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String id, device_ip;
    private final static String TAG = ConnDevice.class.getSimpleName();

    TextView editSearch;
    TextView slotNum1, slotNum2, slotNum3, slotNum4, slotNum5, slotNum6, slotNum7;
    TextView mediNum1, mediNum2, mediNum3, mediNum4, mediNum5, mediNum6, mediNum7;
    TextView mediName1, mediName2, mediName3, mediName4, mediName5, mediName6, mediName7;
    TextView todayChk1, todayChk2, todayChk3, todayChk4, todayChk5, todayChk6, todayChk7;
    TextView time1_1, time1_2, time1_3, time2_1, time2_2, time2_3, time3_1, time3_2, time3_3, time4_1, time4_2, time4_3, time5_1, time5_2, time5_3, time6_1, time6_2, time6_3;
    ImageView mediPhoto1, mediPhoto2, mediPhoto3, mediPhoto4, mediPhoto5, mediPhoto6, mediPhoto7;
    FloatingActionButton btnAddmedi;
    ImageView btnSetting, btnSearch, btnTake, btnTrash;
    TableRow row1, row2, row3, row4, row5, row6, row7;
    View baselayout;

    int alarm = 0;
    int k = 0;

    boolean takef, storagef;

    ArrayList<String> trashArray = new ArrayList<>();
    ArrayList<String> trashNumArray = new ArrayList<>();
    ArrayList<String> trashNameArray = new ArrayList<>();
    ArrayList<Integer> trashSlotArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        editSearch = findViewById(R.id.editSearch);
        btnSetting = findViewById(R.id.btnSetting);
        btnSearch = findViewById(R.id.btnSearch);
        btnTake = findViewById(R.id.btnTake);
        btnTrash = findViewById(R.id.btnTrash);

        row1 = findViewById(R.id.row1);
        row2 = findViewById(R.id.row2);
        row3 = findViewById(R.id.row3);
        row4 = findViewById(R.id.row4);
        row5 = findViewById(R.id.row5);
        row6 = findViewById(R.id.row6);
        row7 = findViewById(R.id.row7);

        slotNum1 = findViewById(R.id.slotNum1);
        slotNum2 = findViewById(R.id.slotNum2);
        slotNum3 = findViewById(R.id.slotNum3);
        slotNum4 = findViewById(R.id.slotNum4);
        slotNum5 = findViewById(R.id.slotNum5);
        slotNum6 = findViewById(R.id.slotNum6);
        slotNum7 = findViewById(R.id.slotNum7);

        mediNum1 = findViewById(R.id.mediNum1);
        mediNum2 = findViewById(R.id.mediNum2);
        mediNum3 = findViewById(R.id.mediNum3);
        mediNum4 = findViewById(R.id.mediNum4);
        mediNum5 = findViewById(R.id.mediNum5);
        mediNum6 = findViewById(R.id.mediNum6);
        mediNum7 = findViewById(R.id.mediNum7);

        mediName1 = findViewById(R.id.mediName1);
        mediName2 = findViewById(R.id.mediName2);
        mediName3 = findViewById(R.id.mediName3);
        mediName4 = findViewById(R.id.mediName4);
        mediName5 = findViewById(R.id.mediName5);
        mediName6 = findViewById(R.id.mediName6);
        mediName7 = findViewById(R.id.mediName7);

        mediPhoto1 = findViewById(R.id.mediPhoto1);
        mediPhoto2 = findViewById(R.id.mediPhoto2);
        mediPhoto3 = findViewById(R.id.mediPhoto3);
        mediPhoto4 = findViewById(R.id.mediPhoto4);
        mediPhoto5 = findViewById(R.id.mediPhoto5);
        mediPhoto6 = findViewById(R.id.mediPhoto6);
        mediPhoto7 = findViewById(R.id.mediPhoto7);

        todayChk1 = findViewById(R.id.todayChk1);
        todayChk2 = findViewById(R.id.todayChk2);
        todayChk3 = findViewById(R.id.todayChk3);
        todayChk4 = findViewById(R.id.todayChk4);
        todayChk5 = findViewById(R.id.todayChk5);
        todayChk6 = findViewById(R.id.todayChk6);
        todayChk7 = findViewById(R.id.todayChk7);

        time1_1 = findViewById(R.id.time1_1);
        time1_2 = findViewById(R.id.time1_2);
        time1_3 = findViewById(R.id.time1_3);

        time2_1 = findViewById(R.id.time2_1);
        time2_2 = findViewById(R.id.time2_2);
        time2_3 = findViewById(R.id.time2_3);

        time3_1 = findViewById(R.id.time3_1);
        time3_2 = findViewById(R.id.time3_2);
        time3_3 = findViewById(R.id.time3_3);

        time4_1 = findViewById(R.id.time4_1);
        time4_2 = findViewById(R.id.time4_2);
        time4_3 = findViewById(R.id.time4_3);

        time5_1 = findViewById(R.id.time5_1);
        time5_2 = findViewById(R.id.time5_2);
        time5_3 = findViewById(R.id.time5_3);

        time6_1 = findViewById(R.id.time6_1);
        time6_2 = findViewById(R.id.time6_2);
        time6_3 = findViewById(R.id.time6_3);


        btnAddmedi = findViewById(R.id.btnAddmedi);
        baselayout = findViewById(R.id.main_baselayout);

        // 세션 id 받아오기
        id = Session.getUserID(getApplicationContext());
        device_ip = Session.getDeviceIP(getApplicationContext());

        Toast.makeText(getApplicationContext(),id+"님 안녕하세요!", Toast.LENGTH_SHORT).show();

        //설정
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });


        //검색
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("search",editSearch.getText().toString()); //검색어 검색 결과화면으로 넘겨주기
                startActivity(intent);
                editSearch.setText("");
            }
        });

        //오늘 요일
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("EE");
        TimeZone time = TimeZone.getTimeZone("Asia/Seoul");
        format.setTimeZone(time);
        final String day = format.format(today);
        Log.i("day", day);

        //오늘 날짜
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
        dformat.setTimeZone(time);
        final String date = dformat.format(today);
        Log.i("date", date);

        //버리기
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    trashArray = trash(id, date); // 유통기한 지난 약 slot num
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        btnTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("trashArray", String.valueOf(trashArray.size()));

                // 버릴 약 없는 경우
                if (trashArray.size() == 0) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("유통기한이 지난 약이 없습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which){
                                }
                            })
                            .show();
                }
                // 버릴 약 있는 경우
                else {
                    for (int i = 0; i < trashArray.size(); i++) {
                        if (trashArray.get(i).equals("1")) {
                            trashNumArray.add(mediNum1.getText().toString());
                            trashNameArray.add(mediName1.getText().toString());
                            trashSlotArray.add(1);
                        } else if (trashArray.get(i).equals("2")) {
                            trashNumArray.add(mediNum2.getText().toString());
                            trashNameArray.add(mediName2.getText().toString());
                            trashSlotArray.add(2);
                        } else if (trashArray.get(i).equals("3")) {
                            trashNumArray.add(mediNum3.getText().toString());
                            trashNameArray.add(mediName3.getText().toString());
                            trashSlotArray.add(3);
                        } else if (trashArray.get(i).equals("4")) {
                            trashNumArray.add(mediNum4.getText().toString());
                            trashNameArray.add(mediName4.getText().toString());
                            trashSlotArray.add(4);
                        } else if (trashArray.get(i).equals("5")) {
                            trashNumArray.add(mediNum5.getText().toString());
                            trashNameArray.add(mediName5.getText().toString());
                            trashSlotArray.add(5);
                        } else if (trashArray.get(i).equals("6")) {
                            trashNumArray.add(mediNum6.getText().toString());
                            trashNameArray.add(mediName6.getText().toString());
                            trashSlotArray.add(6);
                        } else if (trashArray.get(i).equals("7")) {
                            trashNumArray.add(mediNum7.getText().toString());
                            trashNameArray.add(mediName7.getText().toString());
                            trashSlotArray.add(7);
                        }
                    }
                    k = trashSlotArray.size();

                    if (k == 1) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("약을 버리시겠습니까?")
                                .setMessage(trashNameArray.get(0))
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                        Log.i("trash - k", String.valueOf(k));
                                        trashOk(trashNumArray.get(0), trashSlotArray.get(0));

                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                    }
                                })
                                .show();
                    } else if (k == 2) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("약을 버리시겠습니까?")
                                .setMessage(trashNameArray.get(0) + ", " + trashNameArray.get(1))
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                        Log.i("trash - k", String.valueOf(k));
                                        trashOk(trashNumArray.get(0), trashSlotArray.get(0));
                                        trashOk(trashNumArray.get(1), trashSlotArray.get(1));
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                    }
                                })
                                .show();
                    } else if (k == 3) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("약을 버리시겠습니까?")
                                .setMessage(trashNameArray.get(0) + ", " + trashNameArray.get(1) + ", " + trashNameArray.get(2))
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                        Log.i("trash - k", String.valueOf(k));
                                        trashOk(trashNumArray.get(0), trashSlotArray.get(0));
                                        trashOk(trashNumArray.get(1), trashSlotArray.get(1));
                                        trashOk(trashNumArray.get(2), trashSlotArray.get(2));
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                    }
                                })
                                .show();
                    }

                }

            }
        });

        //보관의약품 조회
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> timeloadArray = timeload(id, day);
                    Log.i("timeloadArray", String.valueOf(timeloadArray));

                    alarm = alarmload(id);

                    if (alarm == 1) {
                        // 요일에 따른 푸시알림 설정
                        int k;
                        for (k = 0; k < timeloadArray.size(); k++) {
                            new AlarmHATT(getApplicationContext()).Alarm(timeloadArray.get(k));
                        }

                    }

                    //주기에 따른 푸시알림 설정
                    ArrayList<String> cycleloadArray = cycleload(id, date, alarm); //cycleloadArray : 주기별 오늘 복용 약 medi_num
                    Log.i("cycleloadArray", String.valueOf(cycleloadArray));

                    // 오늘 복용 약 조회
                    ArrayList<String> takeloadArray = takeload(id, day); //takeloadArray : 요일별 오늘 복용 약 medi_num
                    Log.i("takeloadArray", String.valueOf(takeloadArray));

                    ArrayList<String> storeloadArray = storeload(id); //storeloadArray : storage_slot
                    Log.i("storeloadArray", String.valueOf(storeloadArray));

                    String num1 = mediNum1.getText().toString();
                    String num2 = mediNum2.getText().toString();
                    String num3 = mediNum3.getText().toString();
                    String num4 = mediNum4.getText().toString();
                    String num5 = mediNum5.getText().toString();
                    String num6 = mediNum6.getText().toString();

                    int i;
                    for (i=0; i < takeloadArray.size(); i++) {
                        if (takeloadArray.get(i).equals(num1)) {
                            todayChk1.setText("V");
                            todaytake1(id, num1);
                        } else if (takeloadArray.get(i).equals(num2)) {
                            todayChk2.setText("V");
                            todaytake2(id, num2);
                        } else if (takeloadArray.get(i).equals(num3)) {
                            todayChk3.setText("V");
                            todaytake3(id, num3);
                        } else if (takeloadArray.get(i).equals(num4)) {
                            todayChk4.setText("V");
                            todaytake4(id, num4);
                        } else if (takeloadArray.get(i).equals(num5)) {
                            todayChk5.setText("V");
                            todaytake5(id, num5);
                        } else if (takeloadArray.get(i).equals(num6)) {
                            todayChk6.setText("V");
                            todaytake6(id, num6);
                        }
                    }
                    for (i=0; i < cycleloadArray.size(); i++) {
                        if (cycleloadArray.get(i).equals(num1)) {
                            todayChk1.setText("V");
                            todaytake1(id, num1);
                        } else if (cycleloadArray.get(i).equals(num2)) {
                            todayChk2.setText("V");
                            todaytake2(id, num2);
                        } else if (cycleloadArray.get(i).equals(num3)) {
                            todayChk3.setText("V");
                            todaytake3(id, num3);
                        } else if (cycleloadArray.get(i).equals(num4)) {
                            todayChk4.setText("V");
                            todaytake4(id, num4);
                        } else if (cycleloadArray.get(i).equals(num5)) {
                            todayChk5.setText("V");
                            todaytake5(id, num5);
                        } else if (cycleloadArray.get(i).equals(num6)) {
                            todayChk6.setText("V");
                            todaytake6(id, num6);
                        }
                    }

                    // 보관 약 목록
                    int j;
                    for (j=0; j < storeloadArray.size(); j++) {
                        //list.add(storeloadArray.get(i));
                        if (storeloadArray.get(j).equals("1")) {
                            String num = mediNum1.getText().toString();
                            String name;
                            if (num.equals("0")) {
                                name = nonenameload(storeloadArray.get(j));
                            } else {
                                name = mediload(num);
                            }
                            final String finalName = name;
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row1.setVisibility(View.VISIBLE);
                                    mediName1.setText(finalName);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://www.medicinebox.site/project/medicine/img/"+ finalName +".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto1.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });

                        } else if (storeloadArray.get(j).equals("2")) {
                            String num = mediNum2.getText().toString();
                            String name;
                            if (num.equals("0")) {
                                name = nonenameload(storeloadArray.get(j));
                            } else {
                                name = mediload(num);
                            }
                            final String finalName = name;
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row2.setVisibility(View.VISIBLE);
                                    mediName2.setText(finalName);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://www.medicinebox.site/project/medicine/img/"+ finalName +".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto2.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        } else if (storeloadArray.get(j).equals("3")) {
                            String num = mediNum3.getText().toString();
                            String name;
                            if (num.equals("0")) {
                                name = nonenameload(storeloadArray.get(j));
                            } else {
                                name = mediload(num);
                            }
                            final String finalName = name;
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row3.setVisibility(View.VISIBLE);
                                    mediName3.setText(finalName);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://www.medicinebox.site/project/medicine/img/"+ finalName +".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto3.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        } else if (storeloadArray.get(j).equals("4")) {
                            String num = mediNum4.getText().toString();
                            String name;
                            if (num.equals("0")) {
                                name = nonenameload(storeloadArray.get(j));
                            } else {
                                name = mediload(num);
                            }
                            final String finalName = name;
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row4.setVisibility(View.VISIBLE);
                                    mediName4.setText(finalName);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://www.medicinebox.site/project/medicine/img/"+ finalName +".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto4.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        } else if (storeloadArray.get(j).equals("5")) {
                            String num = mediNum5.getText().toString();
                            String name;
                            if (num.equals("0")) {
                                name = nonenameload(storeloadArray.get(j));
                            } else {
                                name = mediload(num);
                            }
                            final String finalName = name;
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row5.setVisibility(View.VISIBLE);
                                    mediName5.setText(finalName);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://www.medicinebox.site/project/medicine/img/"+ finalName +".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto5.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        } else if (storeloadArray.get(j).equals("6")) {
                            String num = mediNum6.getText().toString();
                            String name;
                            if (num.equals("0")) {
                                name = nonenameload(storeloadArray.get(j));
                            } else {
                                name = mediload(num);
                            }
                            final String finalName = name;
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row6.setVisibility(View.VISIBLE);
                                    mediName6.setText(finalName);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://www.medicinebox.site/project/medicine/img/"+ finalName +".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto6.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        } else if (storeloadArray.get(j).equals("7")) {
                            String num = mediNum7.getText().toString();
                            String name;
                            if (num.equals("0")) {
                                name = nonenameload(storeloadArray.get(j));
                            } else {
                                name = mediload(num);
                            }
                            final String finalName = name;
                            MainActivity.this.runOnUiThread(new Runnable() {                                     // UI 쓰레드에서 실행
                                @Override
                                public void run() {
                                    row7.setVisibility(View.VISIBLE);
                                    mediName7.setText(finalName);

                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                URL imgurl = new URL("http://www.medicinebox.site/project/medicine/img/"+ finalName +".png");
                                                Log.i("imgurl", String.valueOf(imgurl));
                                                InputStream is = imgurl.openStream();
                                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                                MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                                                    @Override
                                                    public void run() {
                                                        mediPhoto7.setImageBitmap(bm);
                                                    }
                                                });

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                                    thread.start();
                                }
                            });
                        }
                    }

                    } catch (JSONException | ParseException ex) {
                    ex.printStackTrace();
                }

            }
        });

        //현재 시간
        final SimpleDateFormat tformat = new SimpleDateFormat("HH:mm:ss");
        tformat.setTimeZone(time);
        final String ctime = tformat.format(today);
        Log.i("현재시간", ctime);

        //복용하기
        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String take1_1 = time1_1.getText().toString();
                String take1_2 = time1_2.getText().toString();
                String take1_3 = time1_3.getText().toString();
                String take2_1 = time2_1.getText().toString();
                String take2_2 = time2_2.getText().toString();
                String take2_3 = time2_3.getText().toString();
                String take3_1 = time3_1.getText().toString();
                String take3_2 = time3_2.getText().toString();
                String take3_3 = time3_3.getText().toString();
                String take4_1 = time4_1.getText().toString();
                String take4_2 = time4_2.getText().toString();
                String take4_3 = time4_3.getText().toString();
                String take5_1 = time5_1.getText().toString();
                String take5_2 = time5_2.getText().toString();
                String take5_3 = time5_3.getText().toString();
                String take6_1 = time6_1.getText().toString();
                String take6_2 = time6_2.getText().toString();
                String take6_3 = time6_3.getText().toString();
                try {
                    boolean diff1_1 = diffTime(ctime, take1_1);
                    boolean diff1_2 = diffTime(ctime, take1_2);
                    boolean diff1_3 = diffTime(ctime, take1_3);
                    boolean diff2_1 = diffTime(ctime, take2_1);
                    boolean diff2_2 = diffTime(ctime, take2_2);
                    boolean diff2_3 = diffTime(ctime, take2_3);
                    boolean diff3_1 = diffTime(ctime, take3_1);
                    boolean diff3_2 = diffTime(ctime, take3_2);
                    boolean diff3_3 = diffTime(ctime, take3_3);
                    boolean diff4_1 = diffTime(ctime, take4_1);
                    boolean diff4_2 = diffTime(ctime, take4_2);
                    boolean diff4_3 = diffTime(ctime, take4_3);
                    boolean diff5_1 = diffTime(ctime, take5_1);
                    boolean diff5_2 = diffTime(ctime, take5_2);
                    boolean diff5_3 = diffTime(ctime, take5_3);
                    boolean diff6_1 = diffTime(ctime, take6_1);
                    boolean diff6_2 = diffTime(ctime, take6_2);
                    boolean diff6_3 = diffTime(ctime, take6_3);

                    if (diff1_1 == true | diff1_2 == true | diff1_3 == true) { // 복용할 약 있는 경우
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("복용 하시겠습니까?");
                        builder.setMessage(mediName1.getText());
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // slot 1 복용 신호 송신
                                Log.e(TAG, "slot 1 복용 ");
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        ConnDevice connDevice = new ConnDevice(MainActivity.this);
                                        try {
                                            Log.d(TAG, "slot 1 dosing run()");
                                            connDevice.dosing(1);
                                        } catch (NullPointerException n) {
                                            n.printStackTrace();
                                            showToast("디바이스와 동일한 네트워크에 연결해 주세요");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        builder.show();
                    } else if (diff2_1 == true | diff2_2 == true | diff2_3 == true) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("복용 하시겠습니까?")
                                .setMessage(mediName2.getText())
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                        // slot 2 복용 신호 송신
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                ConnDevice connDevice = new ConnDevice(MainActivity.this);
                                                try {
                                                    Log.d(TAG, "slot 2 dosing run()");
                                                    connDevice.dosing(2);
                                                } catch (NullPointerException n) {
                                                    n.printStackTrace();
//                                                    Toast.makeText(MainActivity.this, "디바이스와 동일한 네트워크에 연결해 주세요", Toast.LENGTH_SHORT).show();
                                                    showToast("디바이스와 동일한 네트워크에 연결해 주세요");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
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
                    } else if (diff3_1 == true | diff3_2 == true | diff3_3 == true) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("복용 하시겠습니까?")
                                .setMessage(mediName3.getText())
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                        // slot 3 복용 신호 송신
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                ConnDevice connDevice = new ConnDevice(MainActivity.this);
                                                try {
                                                    Log.d(TAG, "slot 3 dosing run()");
                                                    connDevice.dosing(3);
                                                } catch (NullPointerException n) {
                                                    n.printStackTrace();
//                                                    Toast.makeText(MainActivity.this, "디바이스와 동일한 네트워크에 연결해 주세요", Toast.LENGTH_SHORT).show();
                                                    showToast("디바이스와 동일한 네트워크에 연결해 주세요");
                                                } catch (Exception e) {
                                                    e.printStackTrace();
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
                    } else if (diff4_1 == true | diff4_2 == true | diff4_3 == true) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("복용 하시겠습니까?")
                                .setMessage(mediName4.getText())
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                        // slot 3 복용 신호 송신
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                ConnDevice connDevice = new ConnDevice(MainActivity.this);
                                                try {
                                                    Log.d(TAG, "slot 4 dosing run()");
                                                    connDevice.dosing(4);
                                                } catch (NullPointerException n) {
                                                    n.printStackTrace();
                                                    Toast.makeText(MainActivity.this, "디바이스와 동일한 네트워크에 연결해 주세요", Toast.LENGTH_SHORT).show();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
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
                    } else if (diff5_1 == true | diff5_2 == true | diff5_3 == true) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("복용 하시겠습니까?")
                                .setMessage(mediName5.getText())
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                        // slot 3 복용 신호 송신
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                ConnDevice connDevice = new ConnDevice(MainActivity.this);
                                                try {
                                                    Log.d(TAG, "slot 5 dosing run()");
                                                    connDevice.dosing(5);
                                                } catch (NullPointerException n) {
                                                    n.printStackTrace();
                                                    Toast.makeText(MainActivity.this, "디바이스와 동일한 네트워크에 연결해 주세요", Toast.LENGTH_SHORT).show();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
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
                    } else if (diff6_1 == true | diff6_2 == true | diff6_3 == true) {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("복용 하시겠습니까?")
                                .setMessage(mediName6.getText())
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                        // slot 3 복용 신호 송신
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                ConnDevice connDevice = new ConnDevice(MainActivity.this);
                                                try {
                                                    Log.d(TAG, "slot 6 dosing run()");
                                                    connDevice.dosing(6);
                                                } catch (NullPointerException n) {
                                                    n.printStackTrace();
                                                    Toast.makeText(MainActivity.this, "디바이스와 동일한 네트워크에 연결해 주세요", Toast.LENGTH_SHORT).show();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
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
                    } else {
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("지금은 복용 시간이 아닙니다.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                    }
                                })
                                .show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


       //목록에서 의약품 선택
        row1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum1.getText().toString());
                intent.putExtra("name",mediName1.getText().toString());
                intent.putExtra("slot",slotNum1.getText().toString());
                startActivity(intent);
            }
        });

        row2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum2.getText().toString());
                intent.putExtra("name",mediName2.getText().toString());
                intent.putExtra("slot",slotNum2.getText().toString());
                startActivity(intent);
            }
        });

        row3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum3.getText().toString());
                intent.putExtra("name",mediName3.getText().toString());
                intent.putExtra("slot",slotNum3.getText().toString());
                startActivity(intent);
            }
        });

        row4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum4.getText().toString());
                intent.putExtra("name",mediName4.getText().toString());
                intent.putExtra("slot",slotNum4.getText().toString());
                startActivity(intent);
            }
        });

        row5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum5.getText().toString());
                intent.putExtra("name",mediName5.getText().toString());
                intent.putExtra("slot",slotNum5.getText().toString());
                startActivity(intent);
            }
        });

        row6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum6.getText().toString());
                intent.putExtra("name",mediName6.getText().toString());
                intent.putExtra("slot",slotNum6.getText().toString());
                startActivity(intent);
            }
        });

        row7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                intent.putExtra("num",mediNum7.getText().toString());
                intent.putExtra("name",mediName7.getText().toString());
                intent.putExtra("slot",slotNum7.getText().toString());
                startActivity(intent);
            }
        });




        // 보관 의약품 추가
        btnAddmedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMediActivity.class);
                startActivity(intent);
            }
        });


        baselayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                baselayout.setFocusable(true);
                baselayout.requestFocus();
                return true;
            }
        });



//        Toast.makeText(getApplicationContext(), "Device IP : " + device_ip, Toast.LENGTH_SHORT).show();

    }

    // 버리기 확인 눌렀을 때
    private void trashOk(final String mediNum, int slotNum) {
        Log.i("trash - mediNum", mediNum);
        Log.i("trash - slotNum", String.valueOf(slotNum));
        // 7번 칸이면
        if (slotNum == 7) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    // 디비에서 삭제
                    storagef = storagedelete(mediNum);
                    if (storagef == true) {
                        MainActivity.this.runOnUiThread(new Runnable() {
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
            });
        } else { // 1~6번 칸이면
            final int slot = slotNum;
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    // 디비에서 삭제
                    takef = takedelete(mediNum);
                    storagef = storagedelete(mediNum);

                    // 잠금해제 신호 송신 - slot : slotNum
                    ConnDevice connDevice = new ConnDevice(MainActivity.this);
                    connDevice.openSlot(slot);


                    if (takef == true && storagef == true) {
                        MainActivity.this.runOnUiThread(new Runnable() {
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
            });
        }

    }

    // 현재시간과 복용 시간 비교
    boolean diffTime(String ctime, String dtime) throws ParseException {
        SimpleDateFormat tformat = new SimpleDateFormat("HH:mm:ss");
        Date current = tformat.parse(ctime); // 현재시간 날짜형으로
        if (dtime.equals("")) {
            return false;
        } else {
            Date taketime = tformat.parse(dtime);
            Log.i("current", String.valueOf(current));
            Log.i("taketime", String.valueOf(taketime));

            long diff = taketime.getTime() - current.getTime();
            long difftime = Math.abs(diff / (1000*60));
            Log.i("difftime", String.valueOf(difftime));

            if (difftime <= 60) {
                return true;
            } else {
                return false;
            }
        }
    }


//    키보드 숨김
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if(view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private ArrayList<String> trash(String id, String date) throws JSONException, ParseException {

        SimpleDateFormat old_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        old_format.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd");

        Date today = new_format.parse(date);

        REST_API storeload = new REST_API("storeload");

        String json = "{\"id\" : \"" + id + "\"}";

        String result = storeload.post(json);

        ArrayList<String> trashArray = new ArrayList<>();

        Log.d("trashload", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0 ; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String storage_slot = jsonObject.getString("storage_slot");
            String medi_num = jsonObject.getString("medi_num");
            String storage_expire = jsonObject.getString("storage_expire");

            Date old_date = old_format.parse(storage_expire);
            old_date.setTime ( old_date.getTime ( ) + ( (long) 1000 * 60 * 60 * 24 ) );
            String new_date = new_format.format(old_date);
            Date expire = new_format.parse(new_date);
            Log.i("trash expire", String.valueOf(expire));
            Log.i("trash today", String.valueOf(today));

            long calDate = expire.getTime() - today.getTime();
            Log.i("trash calDate", String.valueOf(calDate));
            if (calDate < 0) {
                trashArray.add(storage_slot);
            }
        }


        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("trashload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("trashload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "보관의약품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("trashload", "SUCCESS!!!!!");
            return trashArray;
        }

        return null;
    }

    private ArrayList<String> takeload(String id, String day) throws JSONException {
        REST_API takeload = new REST_API("takeload");

        String json = "{\"id\" : \"" + id + "\", \"day\" : \"" + day + "\"}";

        String result = takeload.post(json);

        ArrayList<String> takeArray = new ArrayList<>();

        Log.d("takeload", "result : " + result);
        JSONArray jsonArray = new JSONArray(result);

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String medi_num = jsonObject.getString("medi_num");
            takeArray.add(medi_num);
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("Takeload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("Takeload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "보관의약품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("Takeload", "SUCCESS!!!!!");
            return takeArray;
        }

        return null;
    }

    private ArrayList<String> cycleload(String id, String date, int alarm) throws JSONException, ParseException {
        SimpleDateFormat old_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        old_format.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd");

        Date today = new_format.parse(date);


        REST_API cycleload = new REST_API("cycleload");

        String json = "{\"id\" : \"" + id + "\"}";

        String result = cycleload.post(json);

        ArrayList<String> cycleArray = new ArrayList<>();

        Log.d("cycleload", "result : " + result);
        JSONArray jsonArray = new JSONArray(result);

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String medi_num = jsonObject.getString("medi_num");
            String take_start = jsonObject.getString("take_start");
            int take_cycle = Integer.parseInt(jsonObject.getString("take_cycle"));
            String take_time = jsonObject.getString("take_time");

            Date old_date = old_format.parse(take_start);
            old_date.setTime ( old_date.getTime ( ) + ( (long) 1000 * 60 * 60 * 24 ) );
            String new_date = new_format.format(old_date);
            Date start = new_format.parse(new_date);
            Log.i("start", String.valueOf(start));

            long calDate = today.getTime() - start.getTime();
            int calDateDays = (int) (calDate / ( 24*60*60*1000));
            calDateDays = Math.abs(calDateDays);
            Log.i("calDateDays", String.valueOf(calDateDays));
            Log.i("calDateDays % take_cycle", String.valueOf(calDateDays % take_cycle));

            if (alarm == 1 && calDateDays % take_cycle == 0) {
                cycleArray.add(medi_num);

                // 주기별 알림 설정
                new AlarmHATT(getApplicationContext()).Alarm(take_time);
            } else if (alarm == 0 && calDateDays % take_cycle == 0) {
                cycleArray.add(medi_num);
            }
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("cycleload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("cycleload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("cycleload", "SUCCESS!!!!!");
            return cycleArray;
        }

        return null;
    }

    private ArrayList<String> timeload(String id, String day) throws JSONException {
        REST_API timeload = new REST_API("timeload");

        String json = "{\"id\" : \"" + id + "\", \"day\" : \"" + day + "\"}";

        String result = timeload.post(json);

        ArrayList<String> timeArray = new ArrayList<>();

        Log.d("timeload", "result : " + result);
        JSONArray jsonArray = new JSONArray(result);

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String take_time = jsonObject.getString("take_time");
            timeArray.add(take_time);
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("timeload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("timeload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "보관의약품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("timeload", "SUCCESS!!!!!");
            return timeArray;
        }

        return null;
    }

    private ArrayList<String> storeload(String id) throws JSONException {

        REST_API storeload = new REST_API("storeload");

        String json = "{\"id\" : \"" + id + "\"}";

        String result = storeload.post(json);

        ArrayList<String> storeArray = new ArrayList<>();

        Log.d("STOREload", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0 ; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String storage_slot = jsonObject.getString("storage_slot");
            String medi_num = jsonObject.getString("medi_num");
            storeArray.add(storage_slot);

            if (storage_slot.equals("1")) {
                slotNum1.setText(storage_slot);
                mediNum1.setText(medi_num);
                mediName1.setText(medi_num);
            }
            else if (storage_slot.equals("2")) {
                slotNum2.setText(storage_slot);
                mediNum2.setText(medi_num);
                mediName2.setText(medi_num);
            }
            else if (storage_slot.equals("3")) {
                slotNum3.setText(storage_slot);
                mediNum3.setText(medi_num);
                mediName3.setText(medi_num);
            }
            else if (storage_slot.equals("4")) {
                slotNum4.setText(storage_slot);
                mediNum4.setText(medi_num);
                mediName4.setText(medi_num);
            }
            else if (storage_slot.equals("5")) {
                slotNum5.setText(storage_slot);
                mediNum5.setText(medi_num);
                mediName5.setText(medi_num);
            }
            else if (storage_slot.equals("6")) {
                slotNum6.setText(storage_slot);
                mediNum6.setText(medi_num);
                mediName6.setText(medi_num);
            }
            else if (storage_slot.equals("7")) {
                slotNum7.setText(storage_slot);
                mediNum7.setText(medi_num);
                mediName7.setText(medi_num);
            }
        }


        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("STOREload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("STOREload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "보관의약품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("STOREload", "SUCCESS!!!!!");
            return storeArray;
        }

        return null;
    }

    // slot 1 복용 시간
    private boolean todaytake1(String id, String num) throws JSONException {
        REST_API todaytake = new REST_API("todaytake");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";               // json에서 변수명도 큰따옴표로 감싸야함.

        String result = todaytake.post(json);
        Log.d("todaytake1", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);

        ArrayList<String> timeArray = new ArrayList<>();

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String take_time = jsonObject.getString("take_time");
            timeArray.add(take_time);
        }
        Log.d("timeArray1", String.valueOf(timeArray.size()));

        if (timeArray.size() == 0) {
            return true;
        } else if (timeArray.size() == 1) {
            time1_1.setText(timeArray.get(0));

            return true;
        } else if (timeArray.size() == 2) {
            time1_1.setText(timeArray.get(0));
            time1_2.setText(timeArray.get(1));

            return true;
        } else if (timeArray.size() == 3) {
            time1_1.setText(timeArray.get(0));
            time1_2.setText(timeArray.get(1));
            time1_3.setText(timeArray.get(2));

            return true;
        }


        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("todaytake1", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result.equals("true\n")) {
            Log.d("todaytake1", "SUCCESS!!!!!");
            return true;
        }
        return true;
    }

    // slot 2 복용 시간
    private boolean todaytake2(String id, String num) throws JSONException {
        REST_API todaytake = new REST_API("todaytake");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";               // json에서 변수명도 큰따옴표로 감싸야함.

        String result = todaytake.post(json);
        JSONArray jsonArray = new JSONArray(result);
        Log.d("todaytake2", "result : " + result); //쿼리 결과값

        ArrayList<String> timeArray = new ArrayList<>();

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String take_time = jsonObject.getString("take_time");
            timeArray.add(take_time);
        }
        Log.d("timeArray2", String.valueOf(timeArray.size()));

        if (timeArray.size() == 0) {
            return true;
        } else if (timeArray.size() == 1) {
            time2_1.setText(timeArray.get(0));

            return true;
        } else if (timeArray.size() == 2) {
            time2_1.setText(timeArray.get(0));
            time2_2.setText(timeArray.get(1));

            return true;
        } else if (timeArray.size() == 3) {
            time2_1.setText(timeArray.get(0));
            time2_2.setText(timeArray.get(1));
            time2_3.setText(timeArray.get(2));

            return true;
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("todaytake2", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result.equals("true\n")) {
            Log.d("todaytake2", "SUCCESS!!!!!");
            return true;
        }
        return true;
    }

    // slot 3 복용 시간
    private boolean todaytake3(String id, String num) throws JSONException {
        REST_API todaytake = new REST_API("todaytake");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";               // json에서 변수명도 큰따옴표로 감싸야함.

        String result = todaytake.post(json);
        Log.d("todaytake3", "result : " + result); //쿼리 결과값
        JSONArray jsonArray = new JSONArray(result);

        ArrayList<String> timeArray = new ArrayList<>();

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String take_time = jsonObject.getString("take_time");
            timeArray.add(take_time);
        }
        Log.d("timeArray3", String.valueOf(timeArray.size()));

        if (timeArray.size() == 0) {
            return true;
        } else if (timeArray.size() == 1) {
            time3_1.setText(timeArray.get(0));

            return true;
        } else if (timeArray.size() == 2) {
            time3_1.setText(timeArray.get(0));
            time3_2.setText(timeArray.get(1));

            return true;
        } else if (timeArray.size() == 3) {
            time3_1.setText(timeArray.get(0));
            time3_2.setText(timeArray.get(1));
            time3_3.setText(timeArray.get(2));

            return true;
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("todaytake3", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result.equals("true\n")) {
            Log.d("todaytake3", "SUCCESS!!!!!");
            return true;
        }
        return true;
    }

    // slot 4 복용 시간
    private boolean todaytake4(String id, String num) throws JSONException {
        REST_API todaytake = new REST_API("todaytake");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";               // json에서 변수명도 큰따옴표로 감싸야함.

        String result = todaytake.post(json);
        Log.d("todaytake4", "result : " + result); //쿼리 결과값
        JSONArray jsonArray = new JSONArray(result);

        ArrayList<String> timeArray = new ArrayList<>();

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String take_time = jsonObject.getString("take_time");
            timeArray.add(take_time);
        }
        Log.d("timeArray4", String.valueOf(timeArray.size()));

        if (timeArray.size() == 0) {
            return true;
        } else if (timeArray.size() == 1) {
            time4_1.setText(timeArray.get(0));

            return true;
        } else if (timeArray.size() == 2) {
            time4_1.setText(timeArray.get(0));
            time4_2.setText(timeArray.get(1));

            return true;
        } else if (timeArray.size() == 3) {
            time4_1.setText(timeArray.get(0));
            time4_2.setText(timeArray.get(1));
            time4_3.setText(timeArray.get(2));

            return true;
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("todaytake4", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result.equals("true\n")) {
            Log.d("todaytake4", "SUCCESS!!!!!");
            return true;
        }
        return true;
    }

    // slot 5 복용 시간
    private boolean todaytake5(String id, String num) throws JSONException {
        REST_API todaytake = new REST_API("todaytake");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";               // json에서 변수명도 큰따옴표로 감싸야함.

        String result = todaytake.post(json);
        Log.d("todaytake5", "result : " + result); //쿼리 결과값
        JSONArray jsonArray = new JSONArray(result);

        ArrayList<String> timeArray = new ArrayList<>();

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String take_time = jsonObject.getString("take_time");
            timeArray.add(take_time);
        }
        Log.d("timeArray5", String.valueOf(timeArray.size()));

        if (timeArray.size() == 0) {
            return true;
        } else if (timeArray.size() == 1) {
            time5_1.setText(timeArray.get(0));

            return true;
        } else if (timeArray.size() == 2) {
            time5_1.setText(timeArray.get(0));
            time5_2.setText(timeArray.get(1));

            return true;
        } else if (timeArray.size() == 3) {
            time5_1.setText(timeArray.get(0));
            time5_2.setText(timeArray.get(1));
            time5_3.setText(timeArray.get(2));

            return true;
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("todaytake5", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result.equals("true\n")) {
            Log.d("todaytake5", "SUCCESS!!!!!");
            return true;
        }
        return true;
    }

    // slot 6 복용 시간
    private boolean todaytake6(String id, String num) throws JSONException {
        REST_API todaytake = new REST_API("todaytake");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";               // json에서 변수명도 큰따옴표로 감싸야함.

        String result = todaytake.post(json);
        Log.d("todaytake6", "result : " + result); //쿼리 결과값
        JSONArray jsonArray = new JSONArray(result);

        ArrayList<String> timeArray = new ArrayList<>();

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String take_time = jsonObject.getString("take_time");
            timeArray.add(take_time);
        }
        Log.d("timeArray6", String.valueOf(timeArray.size()));

        if (timeArray.size() == 0) {
            return true;
        } else if (timeArray.size() == 1) {
            time6_1.setText(timeArray.get(0));

            return true;
        } else if (timeArray.size() == 2) {
            time6_1.setText(timeArray.get(0));
            time6_2.setText(timeArray.get(1));

            return true;
        } else if (timeArray.size() == 3) {
            time6_1.setText(timeArray.get(0));
            time6_2.setText(timeArray.get(1));
            time6_3.setText(timeArray.get(2));

            return true;
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("todaytake6", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result.equals("true\n")) {
            Log.d("todaytake6", "SUCCESS!!!!!");
            return true;
        }
        return true;
    }

    private String mediload(String num) throws JSONException {

        REST_API mediload = new REST_API("mediload");

        String json = "{\"num\" : \"" + num + "\"}";

        String result = mediload.post(json);
        Log.d("MEDIload", "result : " + result); //쿼리 결과값
        String medi_name = null;
        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0 ; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            medi_name = jsonObject.getString("medi_name");
        }


        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("MEDIload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("MEDIload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "보관의약품이 없습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("MEDIload", "SUCCESS!!!!!");
            return medi_name;
        }

        return null;
    }

    private String nonenameload(String num) throws JSONException {
        REST_API nonenameload = new REST_API("nonenameload");

        String json = "{\"num\" : \"" + num + "\"}";

        String result = nonenameload.post(json);
        Log.d("nonenameload", "result : " + result); //쿼리 결과값
        String none_name = null;

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("nonenameload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("nonenameload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
            return none_name;
        } else if(result != null) {
            Log.d("nonenameload", "SUCCESS!!!!!");

            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                none_name = jsonObject.getString("none_name");
                Log.d("none_name", String.valueOf(none_name));
            }
            return none_name;
        }
        return none_name;
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
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("storagedelete", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("storagedelete", "SUCCESS!!!!!");
            return true;
        } else if(result.equals("false\n")) {
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
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
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("takedelete", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("takedelete", "SUCCESS!!!!!");
            return true;
        } else if(result.equals("false\n")) {
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return false;
    }

    // 알림 설정
    private int alarmload(String id) throws JSONException {
        REST_API alarmload = new REST_API("alarmload");

        String json = "{\"id\" : \"" + id + "\"}";

        String result = alarmload.post(json);
        Log.d("ALARMload", "result : " + result); //쿼리 결과값

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("ALARMload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("ALARMload", "FAIL!!!!!");
            MainActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
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

    //푸시 알림
    public class AlarmHATT {
        private Context context;
        public AlarmHATT(Context context) {
            this.context=context;
        }
        public void Alarm(String time) {
            String[] split = time.split(":");

            int hour = Integer.parseInt(split[0]);
            int min = Integer.parseInt(split[1]);

            Log.i("hour", String.valueOf(hour));
            Log.i("min", String.valueOf(min));

            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, BroadcastD.class);

            PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, min, 0);

            //알람 예약
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
    }

    public void showToast(final String msg) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
