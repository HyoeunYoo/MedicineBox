package com.example.medicinebox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
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
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



public class AddMedicine extends AppCompatActivity {

    int daySunVal = 0, dayMonVal = 0, dayTueVal = 0, dayWedVal = 0, dayThuVal = 0, dayFriVal = 0, daySatVal = 0;
    int y, m, d;

    ImageView btnBack;

    LinearLayout layoutDay, layoutCycle, lAddTime1, lAddTime2, lAddTime3, lAddTime4, lAddTime5;
    Button btnDay, btnCycle, btnStartdate, btnDaySun, btnDayMon, btnDayTue, btnDayWed, btnDayThu, btnDayFri, btnDaySat
            , btnAddTime1, btnAddTime2, btnAddTime3, btnAddTime4, btnAddTime5, btnSubmit, btnCancel;
    Toolbar toolbar;
    Spinner spinCycle, spinPerDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicine);

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
        lAddTime4 = findViewById(R.id.layoutAddTime4);
        lAddTime5 = findViewById(R.id.layoutAddTime5);
        btnAddTime1 = findViewById(R.id.btnAddTime1);
        btnAddTime2 = findViewById(R.id.btnAddTime2);
        btnAddTime3 = findViewById(R.id.btnAddTime3);
        btnAddTime4 = findViewById(R.id.btnAddTime4);
        btnAddTime5 = findViewById(R.id.btnAddTime5);

        toolbar = findViewById(R.id.toolbar_addMedicine);
        layoutDay = findViewById(R.id.layoutAddDay);
        layoutCycle = findViewById(R.id.layoutAddCycle);
        btnDay = findViewById(R.id.btnType_Day);
        btnCycle = findViewById(R.id.btnType_Cycle);
        spinCycle = findViewById(R.id.spinnerCycle);
        spinPerDay = findViewById(R.id.spinnerPerDay);
        btnStartdate = findViewById(R.id.btnStartdate);
        btnSubmit = findViewById(R.id.btnAddMedSubmit);
        btnCancel =  findViewById(R.id.btnAddMedCancel);


//        액션바 설정
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("약 추가");

//        복용 타입 요일별 선택했을 때
        btnDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                v.setBackgroundResource(R.drawable.button_select);
                btnCycle.setTextColor(Color.WHITE);
                btnDay.setBackgroundResource(R.drawable.button);
                btnDay.setTextColor(Color.BLACK);
                layoutCycle.setVisibility(View.VISIBLE);
                layoutDay.setVisibility(View.GONE);
            }
        });

//        spinner item 설정
        ArrayAdapter<CharSequence> cycleAdapter = ArrayAdapter.createFromResource(this, R.array.array_cycle, R.layout.spinner_item1);
        spinCycle.setAdapter(cycleAdapter);
        ArrayAdapter<CharSequence> perdayAdapter = ArrayAdapter.createFromResource(this, R.array.array_perday, R.layout.spinner_item1);
        spinPerDay.setAdapter(perdayAdapter);

//        주기별일 때 복용 날짜 시작 설정
        btnStartdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMedicine.this, new DatePickerDialog.OnDateSetListener(){

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        y = year;
                        m = month+1;
                        d = dayOfMonth;
                        btnStartdate.setText(y+" - "+m+" - "+d);
                    }
                }, 2020, 1, 1).show();
                Log.d("TAG", "onClick: asd");
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
                        lAddTime4.setVisibility(View.GONE);
                        lAddTime5.setVisibility(View.GONE);
                        break;
                    case 1:
                        lAddTime1.setVisibility(View.VISIBLE);
                        lAddTime2.setVisibility(View.VISIBLE);
                        lAddTime3.setVisibility(View.GONE);
                        lAddTime4.setVisibility(View.GONE);
                        lAddTime5.setVisibility(View.GONE);
                        break;
                    case 2:
                        lAddTime1.setVisibility(View.VISIBLE);
                        lAddTime2.setVisibility(View.VISIBLE);
                        lAddTime3.setVisibility(View.VISIBLE);
                        lAddTime4.setVisibility(View.GONE);
                        lAddTime5.setVisibility(View.GONE);
                        break;
                    case 3:
                        lAddTime1.setVisibility(View.VISIBLE);
                        lAddTime2.setVisibility(View.VISIBLE);
                        lAddTime3.setVisibility(View.VISIBLE);
                        lAddTime4.setVisibility(View.VISIBLE);
                        lAddTime5.setVisibility(View.GONE);
                        break;
                    case 4:
                        lAddTime1.setVisibility(View.VISIBLE);
                        lAddTime2.setVisibility(View.VISIBLE);
                        lAddTime3.setVisibility(View.VISIBLE);
                        lAddTime4.setVisibility(View.VISIBLE);
                        lAddTime5.setVisibility(View.VISIBLE);
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
        btnAddTime4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(btnAddTime4);
            }
        });
        btnAddTime5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(btnAddTime5);
            }
        });


//        확인 버튼 클릭시 이벤트
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        취소 버튼 클릿기 이벤트
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    //    복용 시간 설정할때 시용하는 TimePicker
    void selectTime(final Button btn) {
        String val = btn.getText().toString();
        int hour = 12;
        int min = 00;
        if(!val.split(" ")[0].equals("--")) {
            hour = Integer.parseInt(val.split(" ")[1]);
            min = Integer.parseInt(val.split(" ")[3]);
        }
        if(val.split(" ")[0].equals("PM")) {
            hour += 12;
        }
        TimePickerDialog dialog = new TimePickerDialog(AddMedicine.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
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
}
