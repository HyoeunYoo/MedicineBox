package com.example.medicinebox;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class AddMediActivity extends AppCompatActivity {

    String id;

    int typedayVal = 1, typecycleVal = 0, daySunVal = 0, dayMonVal = 0, dayTueVal = 0, dayWedVal = 0, dayThuVal = 0, dayFriVal = 0, daySatVal = 0;
    int y, m, d;
    int i=0, j=0;

    ImageView btnBack;
    LinearLayout layoutDay, layoutCycle, layoutType, layoutTake, lAddTime1, lAddTime2, lAddTime3;
    Button btnMediAdd, btnDay, btnCycle, btnStartdate, btnDaySun, btnDayMon, btnDayTue, btnDayWed, btnDayThu, btnDayFri, btnDaySat, btnAddTime1, btnAddTime2, btnAddTime3, btnExpiredate;
    //Toolbar toolbar;
    Spinner spinSlot, spinCycle, spinPerDay;

    TextView textType;

    AutoCompleteTextView mediName;

    ArrayList<String> slotArray = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();

    String mediNum, storageNum, noneNum, type, start, time1, time2, time3, time4, time5, expire;
    int cycle = 0, fre = 0, slot = 0;

    String noneStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmedi_activity);

        // 세션 id 받아오기
        id = Session.getUserID(getApplicationContext());

        btnBack = findViewById(R.id.btnBack);

        spinSlot = findViewById(R.id.spinSlot);

        mediName = findViewById(R.id.mediName);

        layoutType = findViewById(R.id.layoutType);
        textType = findViewById(R.id.textType);
        layoutTake = findViewById(R.id.layoutTake);

        btnDaySun = findViewById(R.id.btnDaySun);
        btnDayMon = findViewById(R.id.btnDayMon);
        btnDayTue = findViewById(R.id.btnDayTue);
        btnDayWed = findViewById(R.id.btnDayWed);
        btnDayThu = findViewById(R.id.btnDayThu);
        btnDayFri = findViewById(R.id.btnDayFri);
        btnDaySat = findViewById(R.id.btnDaySat);

        lAddTime1 = findViewById(R.id.layoutAddTime1);
        lAddTime2 = findViewById(R.id.layoutAddTime2);
        lAddTime3 = findViewById(R.id.layoutAddTime3);
        btnAddTime1 = findViewById(R.id.btnAddTime1);
        btnAddTime2 = findViewById(R.id.btnAddTime2);
        btnAddTime3 = findViewById(R.id.btnAddTime3);

        //toolbar = findViewById(R.id.toolbar_addMedicine);
        layoutDay = findViewById(R.id.layoutAddDay);
        layoutCycle = findViewById(R.id.layoutAddCycle);
        btnDay = findViewById(R.id.btnType_Day);
        btnCycle = findViewById(R.id.btnType_Cycle);
        spinCycle = findViewById(R.id.spinnerCycle);
        spinPerDay = findViewById(R.id.spinnerPerDay);
        btnStartdate = findViewById(R.id.btnStartdate);
        btnExpiredate = findViewById(R.id.btnExpiredate);

        btnMediAdd = findViewById(R.id.btnMediAdd);

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        TimeZone time = TimeZone.getTimeZone("Asia/Seoul");
        format.setTimeZone(time);
        String date = format.format(today);
        Log.i("date", date);
        String[] splitText = date.split("-");
        final int year = Integer.parseInt(splitText[0]);
        final int month = Integer.parseInt(splitText[1]);
        final int day = Integer.parseInt(splitText[2]);

/*
//        액션바 설정
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("약 추가");
*/
        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//      slot spinner item 설정
        final ArrayAdapter<CharSequence> slotAdapter = ArrayAdapter.createFromResource(this, R.array.array_slot, R.layout.spinner_item1);
        spinSlot.setAdapter(slotAdapter);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    slotArray = storeload(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("slotArray", String.valueOf(slotArray));
            }
        });

        spinSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int intSlot = spinSlot.getSelectedItemPosition();
                if (intSlot == 6) {
                    textType.setVisibility(View.GONE);
                    layoutType.setVisibility(View.GONE);
                    layoutTake.setVisibility(View.GONE);
                    layoutCycle.setVisibility(View.GONE);
                    layoutDay.setVisibility(View.GONE);
                } else {
                    textType.setVisibility(View.VISIBLE);
                    layoutType.setVisibility(View.VISIBLE);
                    layoutTake.setVisibility(View.VISIBLE);
                    layoutDay.setVisibility(View.VISIBLE);
                }

                String slot = String.valueOf(spinSlot.getSelectedItemPosition()+1);
                int i;
                for (i=0; i<slotArray.size(); i++) {
                    if (slot.equals(slotArray.get(i)) && intSlot != 6) {
                        new AlertDialog.Builder(AddMediActivity.this)
                                .setTitle("이미 보관중인 공간입니다. 다른 곳을 선택해 주세요.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which){
                                    }
                                })
                                .show();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 의약품명
        AsyncTask.execute(new Runnable() {       // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
            @Override
            public void run() {
                ArrayList<String> listArray = new ArrayList<>();

                try {
                    listArray = listload();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("db list", String.valueOf(listArray));
                int i;
                for (i=0; i < listArray.size(); i++) {
                    list.add(listArray.get(i));
                }
                for (i = 1; i < 30; i++) {
                    getXmlData(i);
                }
                AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                    @Override
                    public void run() {
                        ArrayAdapter adapter = new ArrayAdapter(AddMediActivity.this, android.R.layout.simple_list_item_1, list);
                        mediName.setAdapter(adapter);
                    }
                });
            }
        });

//        복용 타입 요일별 선택했을 때
        btnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typedayVal = 1;
                typecycleVal = 0;
                v.setBackgroundResource(R.drawable.button_select);
                btnDay.setTextColor(Color.WHITE);
                btnCycle.setBackgroundResource(R.drawable.button);
                btnCycle.setTextColor(Color.BLACK);
                layoutDay.setVisibility(View.VISIBLE);
                layoutCycle.setVisibility(View.GONE);
            }
        });

//        복용 타입 주기별 선택했을 때
        btnCycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typedayVal = 0;
                typecycleVal = 1;
                v.setBackgroundResource(R.drawable.button_select);
                btnCycle.setTextColor(Color.WHITE);
                btnDay.setBackgroundResource(R.drawable.button);
                btnDay.setTextColor(Color.BLACK);
                layoutCycle.setVisibility(View.VISIBLE);
                layoutDay.setVisibility(View.GONE);
            }
        });

//      복용 주기, 복용 횟수 spinner item 설정
        ArrayAdapter<CharSequence> cycleAdapter = ArrayAdapter.createFromResource(this, R.array.array_cycle, R.layout.spinner_item1);
        spinCycle.setAdapter(cycleAdapter);
        ArrayAdapter<CharSequence> perdayAdapter = ArrayAdapter.createFromResource(this, R.array.array_perday, R.layout.spinner_item1);
        spinPerDay.setAdapter(perdayAdapter);

//        주기별일 때 복용 날짜 시작 설정
        btnStartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMediActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        y = year;
                        m = month + 1;
                        d = dayOfMonth;
                        btnStartdate.setText(y + " - " + m + " - " + d);
                    }
                }, year, month-1, day).show();
                Log.d("TAG", "onClick: asd");
            }
        });


//        요일 눌렀을때 설정
        btnDaySun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daySunVal == 0) {
                    daySunVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDaySun.setTextColor(Color.WHITE);
                } else if (daySunVal == 1) {
                    daySunVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDaySun.setTextColor(Color.BLACK);
                }
            }
        });
        btnDayMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayMonVal == 0) {
                    dayMonVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDayMon.setTextColor(Color.WHITE);
                } else if (dayMonVal == 1) {
                    dayMonVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDayMon.setTextColor(Color.BLACK);
                }
            }
        });
        btnDayTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayTueVal == 0) {
                    dayTueVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDayTue.setTextColor(Color.WHITE);
                } else if (dayTueVal == 1) {
                    dayTueVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDayTue.setTextColor(Color.BLACK);
                }
            }
        });
        btnDayWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayWedVal == 0) {
                    dayWedVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDayWed.setTextColor(Color.WHITE);
                } else if (dayWedVal == 1) {
                    dayWedVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDayWed.setTextColor(Color.BLACK);
                }
            }
        });
        btnDayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayThuVal == 0) {
                    dayThuVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDayThu.setTextColor(Color.WHITE);
                } else if (dayThuVal == 1) {
                    dayThuVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDayThu.setTextColor(Color.BLACK);
                }
            }
        });
        btnDayFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dayFriVal == 0) {
                    dayFriVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDayFri.setTextColor(Color.WHITE);
                } else if (dayFriVal == 1) {
                    dayFriVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDayFri.setTextColor(Color.BLACK);
                }
            }
        });
        btnDaySat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daySatVal == 0) {
                    daySatVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDaySat.setTextColor(Color.WHITE);
                } else if (daySatVal == 1) {
                    daySatVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDaySat.setTextColor(Color.BLACK);
                }
            }
        });


//        하루 복용 횟수 spinner 값에 따른 변경
        spinPerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int intPerDay = spinPerDay.getSelectedItemPosition();
                switch (intPerDay) {
                    case 0:
                        lAddTime1.setVisibility(View.VISIBLE);
                        lAddTime2.setVisibility(View.GONE);
                        lAddTime3.setVisibility(View.GONE);
                        break;
                    case 1:
                        lAddTime1.setVisibility(View.VISIBLE);
                        lAddTime2.setVisibility(View.VISIBLE);
                        lAddTime3.setVisibility(View.GONE);
                        break;
                    case 2:
                        lAddTime1.setVisibility(View.VISIBLE);
                        lAddTime2.setVisibility(View.VISIBLE);
                        lAddTime3.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        복용 시간 button 클릭시
        btnAddTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(btnAddTime1);
            }
        });
        btnAddTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(btnAddTime2);
            }
        });
        btnAddTime3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(btnAddTime3);
            }
        });


        //사용기한 설정
        btnExpiredate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datedialog = new DatePickerDialog(AddMediActivity.this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        y = year;
                        m = month + 1;
                        d = dayOfMonth;
                        btnExpiredate.setText(y + " - " + m + " - " + d);
                    }
                }, year, month - 1, day);

                datedialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                datedialog.show();
                //Log.d("TAG", "onClick: asd");
            }
        });


        //저장
        btnMediAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // slot
                slot = spinSlot.getSelectedItemPosition() + 1;

                // 사용기한
                expire = (String) btnExpiredate.getText();
                expire = expire.replaceAll(" ", "");

                if (slot != 7) { // 1~6번 칸
                    // 복용시간
                    final ArrayList<String> timeArray = new ArrayList<>();
                    fre = spinPerDay.getSelectedItemPosition()+1;
                    if (fre == 1) {
                        time1 = editTime((String) btnAddTime1.getText());
                        timeArray.add(time1);
                    } else if (fre == 2) {
                        time1 = editTime((String) btnAddTime1.getText());
                        time2 = editTime((String) btnAddTime2.getText());
                        timeArray.add(time1);
                        timeArray.add(time2);
                    } else if (fre == 3) {
                        time1 = editTime((String) btnAddTime1.getText());
                        time2 = editTime((String) btnAddTime2.getText());
                        time3 = editTime((String) btnAddTime3.getText());
                        timeArray.add(time1);
                        timeArray.add(time2);
                        timeArray.add(time3);
                    }
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("timeArray", String.valueOf(timeArray));

                            try {
                                Log.i("mediName", mediName.getText().toString());
                                mediNum = mediname(mediName.getText().toString());

                                if (mediNum.equals("0")) {
                                    // none 테이블에 같은 이름으로 의약품 있는 지 검색
                                    noneStore = noneload(mediName.getText().toString());
                                    if (noneStore.equals("-1")) { // 없으면
                                        // 새로 추가
                                        noneNum = nonestoreadd(slot, mediName.getText().toString());
                                        storageNum = storageadd(id, mediNum, slot, expire, noneNum);
                                    } else { // 있으면
                                        // 보관 수 증가
                                        nonestoreup(Integer.parseInt(noneStore) + 1, mediName.getText().toString());
                                    }
                                }
                                else {
                                    // storage table에 추가
                                    storageNum = storageadd(id, mediNum, slot, expire, "");

                                    // 보관 수 증가
                                    medistoreup(mediNum);
                                }

                                if (typedayVal == 1) {
                                    type = "요일별";
                                    final ArrayList<String> dayArray = new ArrayList<>();
                                    if (daySunVal == 1) {
                                        dayArray.add("일");
                                    }
                                    if (dayMonVal == 1) {
                                        dayArray.add("월");
                                    }
                                    if (dayTueVal == 1) {
                                        dayArray.add("화");
                                    }
                                    if (dayWedVal == 1) {
                                        dayArray.add("수");
                                    }
                                    if (dayThuVal == 1) {
                                        dayArray.add("목");
                                    }
                                    if (dayFriVal == 1) {
                                        dayArray.add("금");
                                    }
                                    if (daySatVal == 1) {
                                        dayArray.add("토");
                                    }
                                    Log.i("dayArray", String.valueOf(dayArray));

                                    for (i = 0; i < timeArray.size(); i ++) {
                                        for (j = 0; j < dayArray.size(); j++) {
                                            takedayadd(id, mediNum, storageNum, type, dayArray.get(j), fre, timeArray.get(i));
                                        }
                                    }

                                } else if (typecycleVal == 1) {

                                    type = "주기별";
                                    start = (String) btnStartdate.getText();
                                    start = start.replaceAll(" ","");
                                    cycle = spinCycle.getSelectedItemPosition()+1;

                                    for (i = 0; i < timeArray.size(); i++) {
                                        takecycleadd(id, mediNum, storageNum, type, start, cycle, fre, timeArray.get(i));
                                    }
                                }

                                // 잠금 해제 신호 송신 - slot : slot


                                ConnDevice connDevice = new ConnDevice(AddMediActivity.this);
                                connDevice.openSlot(slot);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Toast.makeText(getApplicationContext(),"추가 되었습니다.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else { // 7번 칸
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.i("mediName", mediName.getText().toString());
                                mediNum = mediname(mediName.getText().toString());

                                if (mediNum.equals("0")) {
                                    // none 테이블에 같은 이름으로 의약품 있는 지 검색
                                    noneStore = noneload(mediName.getText().toString());
                                    if (noneStore.equals("-1")) { // 없으면
                                        // 새로 추가
                                        noneNum = nonestoreadd(slot, mediName.getText().toString());
                                        storageNum = storageadd(id, mediNum, slot, expire, noneNum);
                                    } else { // 있으면
                                        // 보관 수 증가
                                        nonestoreup(Integer.parseInt(noneStore) + 1, mediName.getText().toString());
                                    }
                                }
                                else {
                                    // storage table에 추가
                                    storageNum = storageadd(id, mediNum, slot, expire, "");

                                    // 보관 수 증가
                                    medistoreup(mediNum);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Toast.makeText(getApplicationContext(),"추가 되었습니다.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


            }
        });


    }

    // slot
    private ArrayList<String> storeload(String id) throws JSONException {

        REST_API storeload = new REST_API("storeload");

        String json = "{\"id\" : \"" + id + "\"}";

        String result = storeload.post(json);

        ArrayList<String> slotArray = new ArrayList<>();

        Log.d("STOREload", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0 ; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String storage_slot = jsonObject.getString("storage_slot");
            slotArray.add(storage_slot);
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("STOREload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result != null) {
            Log.d("STOREload", "SUCCESS!!!!!");
            return slotArray;
        }

        return null;
    }

    // 자동완성 리스트 - db
    private ArrayList<String> listload() throws JSONException {

        REST_API listload = new REST_API("listload");

        String result = listload.get("listload");

        ArrayList<String> listArray = new ArrayList<>();

        Log.d("listload", "result : " + result); //쿼리 결과값
        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0 ; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String mediName = jsonObject.getString("medi_name");
            listArray.add(mediName);
        }
        //Log.i("listArray", String.valueOf(listArray));

//        Log.d("LOGIN", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("listload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("listload", "SUCCESS!!!!!");
            return listArray;
        }

        return null;
    }

    // 자동완성 리스트 - api
    void getXmlData(int num) {

        StringBuffer buffer = new StringBuffer();

        //String name = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String key = "NNlPSgKdYOzfOQTW0r5g1EGKaq43lm%2FmEwfx%2Fyi09i6FtGJCPyG1fB2yIEvE4lCLfxs7X6O%2FXtZ89jslBzW%2BWw%3D%3D";
        String queryUrl = "http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList?" + "ServiceKey=" + key + "&numOfRows=100&pageNo=" + num;
        Log.i("url", queryUrl);
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();
            Log.i("eventType", String.valueOf(eventType));

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//테그 이름 얻어오기
                        if (tag.equals("item")) ;// 첫번째 검색결과
                        else if (tag.equals("ITEM_NAME")) {
                            buffer.append("품목명 : ");
                            xpp.next();
                            list.add(xpp.getText());
                            Log.i("api list",xpp.getText());
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //테그 이름 얻어오기

                        if (tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
            e.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        //return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //    복용 시간 설정할때 시용하는 TimePicker
    void selectTime(final Button btn) {
        String val = btn.getText().toString();
        int hour = 12;
        int min = 00;
        if (!val.split(" ")[0].equals("--")) {
            hour = Integer.parseInt(val.split(" ")[1]);
            min = Integer.parseInt(val.split(" ")[3]);
        }
        if (val.split(" ")[0].equals("PM")) {
            hour += 12;
        }
        TimePickerDialog dialog = new TimePickerDialog(AddMediActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 12) {
                    btn.setText("AM " + hourOfDay + " : ");
                } else {
                    btn.setText("PM " + (hourOfDay - 12) + " : ");
                }
                if (minute < 10) {
                    btn.setText(btn.getText().toString() + "0" + minute);
                } else {
                    btn.setText(btn.getText().toString() + minute);
                }
            }
        }, hour, min, false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    String editTime(String time) {
        String t;
        String[] splitText = time.split(" ");
        String ampm = splitText[0];
        int hour = Integer.parseInt(splitText[1]);
        String min = splitText[3];

        if (ampm.equals("PM")) {
            hour = hour + 12;
            t = hour + ":" + min;
        } else {
            t = hour + ":" + min;
        }
        return t;
    }

    private String mediname(String name) throws JSONException {

        REST_API mediname = new REST_API("mediname");

        String json = "{\"name\" : \"" + name + "\"}";

        String result = mediname.post(json);
        Log.d("MEDIname", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);
        String medi_num = null;

        Log.i("resultjson", String.valueOf(jsonArray.length()));
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("MEDIname", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (jsonArray.length() == 0) {
            return "0";
        } else {
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                medi_num = jsonObject.getString("medi_num");
            }
            return medi_num;
        }

        return "0";
    }

    private String storageadd(String id, String mediNum, int slotNum, String expire, String noneNum) throws JSONException {

        REST_API storageadd = new REST_API("storageadd");

        String json = "{\"id\" : \"" + id + "\", \"mediNum\" : \"" + mediNum + "\", \"slotNum\" : \"" + slotNum + "\", \"expire\" : \"" + expire + "\", \"noneNum\" : \"" + noneNum + "\"}";
        // json에서 변수명도 큰따옴표로 감싸야함.

        String result = storageadd.post(json);
        Log.d("storageadd", "result : " + result);

        JSONArray jsonArray = new JSONArray(result);
        String storage_num = null;

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            storage_num = jsonObject.getString("storage_num");
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("storageadd", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("storageadd", "FAIL!!!!!");
            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        return storage_num;
    }

    private boolean takedayadd(String id, String mediNum, String storageNum, String type, String day, int fre, String time) {

        REST_API takedayadd = new REST_API("takedayadd");

        String json = "{\"id\" : \"" + id + "\", \"mediNum\" : \"" + mediNum + "\", \"storageNum\" : \"" + storageNum + "\", \"type\" : \"" + type + "\", \"day\" : \"" + day + "\", \"fre\" : \"" + fre + "\", \"time\" : \"" + time + "\"}";
        // json에서 변수명도 큰따옴표로 감싸야함.

        String result = takedayadd.post(json);
        Log.d("takedayadd", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("takedayadd", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("takedayadd", "FAIL!!!!!");
            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("takedayadd", "SUCCESS!!!!!");
            return true;
        }

        return false;
    }

    private boolean takecycleadd(String id, String mediNum, String storageNum, String type, String start, int cycle, int fre, String time) {

        REST_API takecycleadd = new REST_API("takecycleadd");

        String json = "{\"id\" : \"" + id + "\", \"mediNum\" : \"" + mediNum + "\", \"storageNum\" : \"" + storageNum + "\", \"type\" : \"" + type + "\", \"start\" : \"" + start + "\", \"cycle\" : \"" + cycle + "\", \"fre\" : \"" + fre + "\", \"time\" : \"" + time + "\"}";
        // json에서 변수명도 큰따옴표로 감싸야함.

        String result = takecycleadd.post(json);
        Log.d("takecycleadd", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("takecycleadd", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("takecycleadd", "FAIL!!!!!");
            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("takecycleadd", "SUCCESS!!!!!");
            return true;
        }

        return false;
    }

    private String noneload(String name) throws JSONException {

        REST_API noneload = new REST_API("noneload");

        String json = "{\"name\" : \"" + name + "\"}";

        String result = noneload.post(json);
        Log.d("noneload", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);
        String none_store = null;

        Log.i("resultjson", String.valueOf(jsonArray.length()));
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("noneload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
            return "timeout";
        }
        else if (jsonArray.length() == 0) {
            return "-1";
        }
        else {
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                none_store = jsonObject.getString("none_store");
            }
            return none_store;
        }
    }

    private String nonestoreadd(int num, String name) throws JSONException {

        REST_API nonestoreadd = new REST_API("nonestoreadd");

        String json = "{\"num\" : \"" + num + "\", \"name\" : \"" + name + "\"}";


        String result = nonestoreadd.post(json);
        Log.d("nonestoreadd", "result : " + result); //쿼리 결과값

        JSONArray jsonArray = new JSONArray(result);

        String none_num = null;

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            none_num = jsonObject.getString("none_num");
        }

        if(result.equals("timeout")) {                                                         // 서버 연결 시간(5초) 초과시
            Log.d("nonestoreadd", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });

            return "timeout";
        } else {
            return none_num;
        }
    }

    private boolean nonestoreup(int num, String name) throws JSONException {

        REST_API nonestoreup = new REST_API("nonestoreup");

        String json = "{\"num\" : \"" + num + "\", \"name\" : \"" + name + "\"}";

        String result = nonestoreup.post(json);
        Log.d("nonestoreup", "result : " + result); //쿼리 결과값

        if(result.equals("timeout")) {                                                         // 서버 연결 시간(5초) 초과시
            Log.d("nonestoreup", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });

            return false;
        } else if (result.equals("false")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean medistoreup(String num) throws JSONException {

        REST_API medistoreup = new REST_API("medistoreup");

        String json = "{\"num\" : \"" + num + "\"}";

        String result = medistoreup.post(json);
        Log.d("medistoreup", "result : " + result); //쿼리 결과값

        if(result.equals("timeout")) {                                                         // 서버 연결 시간(5초) 초과시
            Log.d("medistoreup", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            AddMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(AddMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });

            return false;
        } else if (result.equals("false")) {
            return false;
        } else {
            return true;
        }
    }
}

