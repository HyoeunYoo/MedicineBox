package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class EditMediActivity extends AppCompatActivity {

    String id;
    int typedayVal = 0, typecycleVal = 0, daySunVal = 0, dayMonVal = 0, dayTueVal = 0, dayWedVal = 0, dayThuVal = 0, dayFriVal = 0, daySatVal = 0;
    int exy, exm, exd, todayy, todaym, todayd;
    int i=0, j=0;

    String edittype, editstart, edittime1, edittime2, edittime3, edittime4, edittime5, editexpire;
    int editcycle = 0, editfre = 0;

    ImageView btnBack;
    LinearLayout layoutDay, layoutCycle, layoutType, layoutTake, lAddTime1, lAddTime2, lAddTime3;
    Button btnMediAdd, btnDay, btnCycle, btnStartdate, btnDaySun, btnDayMon, btnDayTue, btnDayWed, btnDayThu, btnDayFri, btnDaySat
            , btnAddTime1, btnAddTime2, btnAddTime3, btnExpiredate;
    Spinner spinCycle, spinPerDay;

    TextView slotNum, mediName, takeType;

    ArrayList<String> takeloadArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editmedi_activity);

        // 세션 id 받아오기
        id = Session.getUserID(getApplicationContext());

        btnBack = findViewById(R.id.btnBack);

        layoutType = findViewById(R.id.layoutType);
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

        layoutDay = findViewById(R.id.layoutAddDay);
        layoutCycle = findViewById(R.id.layoutAddCycle);
        btnDay = findViewById(R.id.btnType_Day);
        btnCycle = findViewById(R.id.btnType_Cycle);
        spinCycle = findViewById(R.id.spinnerCycle);
        spinPerDay = findViewById(R.id.spinnerPerDay);
        btnStartdate = findViewById(R.id.btnStartdate);
        btnExpiredate = findViewById(R.id.btnExpiredate);

        btnMediAdd = findViewById(R.id.btnMediAdd);

        slotNum = findViewById(R.id.slotNum);
        mediName = findViewById(R.id.mediName);
        takeType = findViewById(R.id.takeType);

        // 복용 주기, 복용 횟수 spinner item 설정
        ArrayAdapter<CharSequence> cycleAdapter = ArrayAdapter.createFromResource(this, R.array.array_cycle, R.layout.spinner_item1);
        spinCycle.setAdapter(cycleAdapter);
        ArrayAdapter<CharSequence> perdayAdapter = ArrayAdapter.createFromResource(this, R.array.array_perday, R.layout.spinner_item1);
        spinPerDay.setAdapter(perdayAdapter);

        Intent intent = getIntent();
        final String num = intent.getExtras().getString("num");
        final String name = intent.getExtras().getString("name");
        final String slot = intent.getExtras().getString("slot");
        final String storageNum = intent.getExtras().getString("storageNum");
        String type = intent.getExtras().getString("type");
        String start = intent.getExtras().getString("start");
        String cycle = intent.getExtras().getString("cycle");
        String fre = intent.getExtras().getString("fre");
        String expire = intent.getExtras().getString("expire");

        String time1 = intent.getExtras().getString("time1");
        String time2 = intent.getExtras().getString("time2");
        String time3 = intent.getExtras().getString("time3");

        btnAddTime1.setText(splitTime(time1));
        btnAddTime2.setText(splitTime(time2));
        btnAddTime3.setText(splitTime(time3));


        exy = Integer.parseInt(expire.substring(0,4));
        exm = Integer.parseInt(expire.substring(6,8));
        exd = Integer.parseInt(expire.substring(10,12));

        Log.i("expire", String.valueOf(exy + " - " + exm + " - " + exd));

        // 초기화
        slotNum.setText(slot+"번");
        if (slot.equals("7")) {
            takeType.setVisibility(View.GONE);
            layoutType.setVisibility(View.GONE);
            layoutDay.setVisibility(View.GONE);
            layoutCycle.setVisibility(View.GONE);
            layoutTake.setVisibility(View.GONE);
        }
        mediName.setText(name);
        btnExpiredate.setText(exy+" - "+exm+" - "+exd);

        if (type.equals("요일별")) {
            typedayVal = 1;
            typecycleVal = 0;
            btnDay.setBackgroundResource(R.drawable.button_select);
            btnDay.setTextColor(Color.WHITE);
            btnCycle.setBackgroundResource(R.drawable.button);
            btnCycle.setTextColor(Color.BLACK);
            layoutDay.setVisibility(View.VISIBLE);
            layoutCycle.setVisibility(View.GONE);
        } else if (type.equals("주기별")) {
            typedayVal = 0;
            typecycleVal = 1;
            btnCycle.setBackgroundResource(R.drawable.button_select);
            btnCycle.setTextColor(Color.WHITE);
            btnDay.setBackgroundResource(R.drawable.button);
            btnDay.setTextColor(Color.BLACK);
            layoutCycle.setVisibility(View.VISIBLE);
            layoutDay.setVisibility(View.GONE);

            start = start.replace("년"," -");
            start = start.replace("월", " -");
            start = start.replace("일", "");
            btnStartdate.setText(start);

            if (cycle.equals("1일")) {
                spinCycle.setSelection(0);
            } else if (cycle.equals("2일")) {
                spinCycle.setSelection(1);
            } else if (cycle.equals("3일")) {
                spinCycle.setSelection(2);
            } else if (cycle.equals("4일")) {
                spinCycle.setSelection(3);
            } else if (cycle.equals("5일")) {
                spinCycle.setSelection(4);
            } else if (cycle.equals("6일")) {
                spinCycle.setSelection(5);
            } else if (cycle.equals("7일")) {
                spinCycle.setSelection(6);
            } else if (cycle.equals("8일")) {
                spinCycle.setSelection(7);
            } else if (cycle.equals("9일")) {
                spinCycle.setSelection(8);
            } else if (cycle.equals("10일")) {
                spinCycle.setSelection(9);
            }

        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    takeday(id, num);
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
                //Log.d("IN ASYNC", String.valueOf(flag));
            }
        });

        if (fre.equals("1번")) {
            spinPerDay.setSelection(0);
        } else if (fre.equals("2번")) {
            spinPerDay.setSelection(1);
        } else if (fre.equals("3번")) {
            spinPerDay.setSelection(2);
        }


        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        //        요일 눌렀을때 설정
        btnDaySun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(daySunVal == 0) {
                    daySunVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDaySun.setTextColor(Color.WHITE);
                } else if(daySunVal == 1) {
                    daySunVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDaySun.setTextColor(Color.BLACK);
                }
            }
        });
        btnDayMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dayMonVal == 0) {
                    dayMonVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDayMon.setTextColor(Color.WHITE);
                } else if(dayMonVal == 1) {
                    dayMonVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDayMon.setTextColor(Color.BLACK);
                }
            }
        });
        btnDayTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dayTueVal == 0) {
                    dayTueVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDayTue.setTextColor(Color.WHITE);
                } else if(dayTueVal == 1) {
                    dayTueVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDayTue.setTextColor(Color.BLACK);
                }
            }
        });
        btnDayWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dayWedVal == 0) {
                    dayWedVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDayWed.setTextColor(Color.WHITE);
                } else if(dayWedVal == 1) {
                    dayWedVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDayWed.setTextColor(Color.BLACK);
                }
            }
        });
        btnDayThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dayThuVal == 0) {
                    dayThuVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDayThu.setTextColor(Color.WHITE);
                } else if(dayThuVal == 1) {
                    dayThuVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDayThu.setTextColor(Color.BLACK);
                }
            }
        });
        btnDayFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dayFriVal == 0) {
                    dayFriVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDayFri.setTextColor(Color.WHITE);
                } else if(dayFriVal == 1) {
                    dayFriVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDayFri.setTextColor(Color.BLACK);
                }
            }
        });
        btnDaySat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(daySatVal == 0) {
                    daySatVal = 1;
                    v.setBackgroundResource(R.drawable.button_select);
                    btnDaySat.setTextColor(Color.WHITE);
                } else if(daySatVal == 1) {
                    daySatVal = 0;
                    v.setBackgroundResource(R.drawable.button);
                    btnDaySat.setTextColor(Color.BLACK);
                }
            }
        });

        //오늘 날짜
        Date today = new Date();
        SimpleDateFormat yformat = new SimpleDateFormat("yyyy");
        SimpleDateFormat mformat = new SimpleDateFormat("MM");
        SimpleDateFormat dformat = new SimpleDateFormat("dd");
        TimeZone time = TimeZone.getTimeZone("Asia/Seoul");
        yformat.setTimeZone(time);
        mformat.setTimeZone(time);
        dformat.setTimeZone(time);
        todayy = Integer.parseInt(yformat.format(today));
        todaym = Integer.parseInt(mformat.format(today));
        todayd = Integer.parseInt(dformat.format(today));

        //        주기별일 때 복용 시작 날짜 설정
        btnStartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditMediActivity.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        todayy = year;
                        todaym = month+1;
                        todayd = dayOfMonth;
                        btnStartdate.setText(todayy+" - "+todaym+" - "+todayd);
                    }
                }, todayy, todaym-1, todayd).show();
            }
        });

        //        하루 복용 횟수 spinner 값에 따른 변경
        spinPerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int intPerDay = spinPerDay.getSelectedItemPosition();
                switch (intPerDay) {
                    case 0 :
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
                DatePickerDialog datedialog = new DatePickerDialog(EditMediActivity.this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        exy = year;
                        exm = month+1;
                        exd = dayOfMonth;
                        btnExpiredate.setText(exy+" - "+exm+" - "+exd);
                    }
                }, exy, exm-1, exd);

                datedialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                datedialog.show();
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    takeloadArray = takenumload(id, num);
                    Log.i("takeloadArray", String.valueOf(takeloadArray));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        //저장
        btnMediAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용기한 수정
                editexpire = (String) btnExpiredate.getText();
                editexpire = editexpire.replaceAll(" ", "");

                // 복용시간 수정
                final ArrayList<String> timeArray = new ArrayList<>();
                editfre = spinPerDay.getSelectedItemPosition()+1;
                if (editfre == 1) {
                    edittime1 = editTime((String) btnAddTime1.getText());
                    timeArray.add(edittime1);
                } else if (editfre == 2) {
                    edittime1 = editTime((String) btnAddTime1.getText());
                    edittime2 = editTime((String) btnAddTime2.getText());
                    timeArray.add(edittime1);
                    timeArray.add(edittime2);
                } else if (editfre == 3) {
                    edittime1 = editTime((String) btnAddTime1.getText());
                    edittime2 = editTime((String) btnAddTime2.getText());
                    edittime3 = editTime((String) btnAddTime3.getText());
                    timeArray.add(edittime1);
                    timeArray.add(edittime2);
                    timeArray.add(edittime3);
                }

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        //사용기한 수정
                        storageedit(id, slot, editexpire);

                        //복용정보 삭제
                        for (i = 0; i<takeloadArray.size(); i++) {
                            takeeditdelete(takeloadArray.get(i));
                        }

                        Log.i("timeArray", String.valueOf(timeArray));

                        if (typedayVal == 1) {
                            edittype = "요일별";
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
                                    takedayadd(id, num, storageNum, edittype, dayArray.get(j), editfre, timeArray.get(i));
                                }
                            }

                        } else if (typecycleVal == 1) {

                            edittype = "주기별";
                            editstart = (String) btnStartdate.getText();
                            editstart = editstart.replaceAll(" ","");
                            editcycle = spinCycle.getSelectedItemPosition()+1;

                            for (i = 0; i < timeArray.size(); i++) {
                                takecycleadd(id, num, storageNum, edittype, editstart, editcycle, editfre, timeArray.get(i));
                            }
                        }
                    }
                });
                Toast.makeText(getApplicationContext(),"수정되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    //    복용 시간 설정할때 사용하는 TimePicker
    void selectTime(final Button btn) {
        int hour, min;

        String val = btn.getText().toString();
        String[] splitText = val.split(" ");
        min = Integer.parseInt(splitText[3]);
        String ampm = splitText[0];
        if (ampm.equals("AM")) {
            hour = Integer.parseInt(splitText[1]);
        } else {
            hour = Integer.parseInt(splitText[1]) + 12;
        }


        TimePickerDialog dialog = new TimePickerDialog(EditMediActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(hourOfDay < 12) {
                    btn.setText("AM "+ hourOfDay + " : ");
                } else {
                    btn.setText("PM " + (hourOfDay-12) + " : ");
                }
                if(minute < 10) {
                    btn.setText(btn.getText().toString() + "0" + minute);
                } else {
                    btn.setText(btn.getText().toString() + minute);
                }
            }
        }, hour , min, false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    // 시간 초기화 시
    String splitTime(String time) {
        Log.i("splitTime",time);

        String t = time;

        if (t == null | t.equals("")) {
            t = "AM 08 : 00";
        } else {
            String[] splitText = time.split(":");
            String hour = splitText[0];
            String min = splitText[1];

            int h = Integer.parseInt(hour);

            if (h > 12 && h < 22) {
                t = "PM " + "0"+ (h-12) + " : " + min;
            } else if (h > 21 && h < 25) {
                t = "PM " + (h-12) + " : " + min;
            } else {
                t = "AM " + hour + " : " + min;
            }
        }

        return t;
    }


    // 요일 초기화
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

            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String take_day = jsonObject.getString("take_day");

                if (take_day.equals("일")) {
                    daySunVal = 1;
                    btnDaySun.setBackgroundResource(R.drawable.button_select);
                    btnDaySun.setTextColor(Color.WHITE);
                } else if (take_day.equals("월")) {
                    dayMonVal = 1;
                    btnDayMon.setBackgroundResource(R.drawable.button_select);
                    btnDayMon.setTextColor(Color.WHITE);
                } else if (take_day.equals("화")) {
                    dayTueVal = 1;
                    btnDayTue.setBackgroundResource(R.drawable.button_select);
                    btnDayTue.setTextColor(Color.WHITE);
                } else if (take_day.equals("수")) {
                    dayWedVal = 1;
                    btnDayWed.setBackgroundResource(R.drawable.button_select);
                    btnDayWed.setTextColor(Color.WHITE);
                } else if (take_day.equals("목")) {
                    dayThuVal = 1;
                    btnDayThu.setBackgroundResource(R.drawable.button_select);
                    btnDayThu.setTextColor(Color.WHITE);
                } else if (take_day.equals("금")) {
                    dayFriVal = 1;
                    btnDayFri.setBackgroundResource(R.drawable.button_select);
                    btnDayFri.setTextColor(Color.WHITE);
                } else if (take_day.equals("토")) {
                    daySatVal = 1;
                    btnDaySat.setBackgroundResource(R.drawable.button_select);
                    btnDaySat.setTextColor(Color.WHITE);
                }

            }
            return true;
        }

        return false;
    }



    private ArrayList<String> takenumload(String id, String num) throws JSONException {
        REST_API takenumload = new REST_API("takenumload");

        String json = "{\"id\" : \"" + id + "\", \"num\" : \"" + num + "\"}";

        String result = takenumload.post(json);

        ArrayList<String> takeArray = new ArrayList<>();

        Log.d("takenumload", "result : " + result);
        JSONArray jsonArray = new JSONArray(result);

        for(int i = 0 ; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String take_num = jsonObject.getString("take_num");
            takeArray.add(take_num);
        }

        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("takenumload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("takenumload", "FAIL!!!!!");
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else if(result != null) {
            Log.d("takenumload", "SUCCESS!!!!!");
            return takeArray;
        }

        return null;
    }

    // 복용 정보 지우기 (수정하기 어려워서 지웠다 추가하는 걸로)
    private boolean takeeditdelete(String num) {

        REST_API takeeditdelete = new REST_API("takeeditdelete");

        String json = "{\"num\" : \"" + num + "\"}";

        String result = takeeditdelete.delete(json);
//        Log.d("LOGIN", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("takeeditdelete", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("takeeditdelete", "FAIL!!!!!");
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("takeeditdelete", "SUCCESS!!!!!");
            return true;
        } else if(result.equals("false\n")) {
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return false;
    }

    // 복용시간 수정하기 위해서
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



    private boolean storageedit(String id, String slot, String expire) {

        REST_API storageedit = new REST_API("storageedit");

        String json = "{\"id\" : \"" + id + "\", \"slot\" : \"" + slot + "\" , \"expire\" : \"" + expire + "\"}";
        // json에서 변수명도 큰따옴표로 감싸야함.

        String result = storageedit.put(json);
        Log.d("storageedit", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("storageedit", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("storageedit", "FAIL!!!!!");
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("storageedit", "SUCCESS!!!!!");
            return true;
        }

        return false;
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
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("takedayadd", "FAIL!!!!!");
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "", Toast.LENGTH_SHORT).show();
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
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("takecycleadd", "FAIL!!!!!");
            EditMediActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(EditMediActivity.this, "", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("takecycleadd", "SUCCESS!!!!!");
            return true;
        }

        return false;
    }
}
