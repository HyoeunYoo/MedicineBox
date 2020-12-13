package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class SearchSelect extends AppCompatActivity {

    TextView mediName, mediEffect, mediUse;
    ImageView btnBack, btnHome, mediPhoto;

    int count = 0;

    boolean flag;

    String noneSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_select);

        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);

        mediName = findViewById(R.id.mediName);
        mediEffect = findViewById(R.id.mediEffect);
        mediUse = findViewById(R.id.mediUse);
        mediPhoto = findViewById(R.id.mediPhoto);

        //뒤로가기
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //홈
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 검색 결과에서 선택한 값 넘겨받음
        Intent intent = getIntent();
        final String name = intent.getExtras().getString("name");
        mediName.setText(name);

        AsyncTask.execute(new Runnable() {          // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
            @Override
            public void run() {
                try {
                    flag = mediname(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("IN ASYNC", String.valueOf(flag));
                if(flag) {
                    try {
                        count = searchload(name);
                        searchadd(count+1, name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL imgurl = new URL("http://www.medicinebox.site/project/medicine/img/"+name+".png");
                                Log.i("imgurl", String.valueOf(imgurl));
                                InputStream is = imgurl.openStream();
                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
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
                } else {
                    try {
                        // none 테이블에 같은 이름으로 의약품 있는 지 검색
                        noneSearch = noneload(name);
                        if (noneSearch.equals("-1")) { // 없으면
                            // 새로 추가
                            nonesearchadd(name);
                        } else { // 있으면
                            // search 수 증가
                            nonesearchup(Integer.parseInt(noneSearch) + 1, name);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Thread thread2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                URL imgurl = new URL(getXmlData(name));
                                Log.i("imgurl", String.valueOf(imgurl));
                                //Log.i("count", String.valueOf(count));
                    /*if (imgurl.equals(0)) {
                        SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                            @Override
                            public void run() {
                                mediPhoto.setImageResource(R.drawable.search_logo);
                            }
                        });
                    } else {*/
                                InputStream is = imgurl.openStream();
                                final Bitmap bm = BitmapFactory.decodeStream(is);
                                SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
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

                    thread2.start();
                }
            }
        });
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
            SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchSelect.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
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

    private int searchload(String name) throws JSONException {

        REST_API mediname = new REST_API("mediname");

        String json = "{\"name\" : \"" + name + "\"}";

        String result = mediname.post(json);
        Log.d("MEDIname", "result : " + result); //쿼리 결과값

        int medi_search = 0;

        JSONArray jsonArray = new JSONArray(result);

        Log.i("resultjson", String.valueOf(jsonArray.length()));
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("MEDIname", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchSelect.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (jsonArray.length() == 0) {
            return 0;
        } else {
            for(int i = 0 ; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                medi_search = Integer.parseInt(jsonObject.getString("medi_search"));
            }
            return medi_search;
        }

        return 0;
    }

    private boolean searchadd(int num, String name) {

        REST_API searchadd = new REST_API("searchadd");

        String json = "{\"num\" : \"" + num + "\", \"name\" : \"" + name + "\"}";               // json에서 변수명도 큰따옴표로 감싸야함.

        String result = searchadd.post(json);
//        Log.d("LOGIN", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("searchadd", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchSelect.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("searchadd", "FAIL!!!!!");
            SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchSelect.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        } else if(result.equals("true\n")) {
            Log.d("searchadd", "SUCCESS!!!!!");
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
        String none_search = null;

        Log.i("resultjson", String.valueOf(jsonArray.length()));
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("noneload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchSelect.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
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
                none_search = jsonObject.getString("none_search");
            }
            return none_search;
        }
    }

    private boolean nonesearchadd(String name) throws JSONException {

        REST_API nonesearchadd = new REST_API("nonesearchadd");

        String json = "{\"name\" : \"" + name + "\"}";


        String result = nonesearchadd.post(json);
        Log.d("nonestoreadd", "result : " + result); //쿼리 결과값

        if(result.equals("timeout")) {                                                         // 서버 연결 시간(5초) 초과시
            Log.d("nonestoreadd", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchSelect.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });

            return false;
        } else if (result.equals("false")) {
            return false;
        } else {
            return true;
        }
    }

    private boolean nonesearchup(int num, String name) throws JSONException {

        REST_API nonesearchup = new REST_API("nonesearchup");

        String json = "{\"num\" : \"" + num + "\", \"name\" : \"" + name + "\"}";

        String result = nonesearchup.post(json);
        Log.d("nonesearchup", "result : " + result); //쿼리 결과값

        if(result.equals("timeout")) {                                                         // 서버 연결 시간(5초) 초과시
            Log.d("nonesearchup", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.

            SearchSelect.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchSelect.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });

            return false;
        } else if (result.equals("false")) {
            return false;
        } else {
            return true;
        }
    }

    String getXmlData(String word){

        StringBuffer buffer = new StringBuffer();

        Log.i("word",word);
        //String name = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String key= "NNlPSgKdYOzfOQTW0r5g1EGKaq43lm%2FmEwfx%2Fyi09i6FtGJCPyG1fB2yIEvE4lCLfxs7X6O%2FXtZ89jslBzW%2BWw%3D%3D";
        String queryUrl="http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList?"+"ServiceKey="+key+"&item_name="+word;
        Log.i("url",queryUrl);
        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;
            String count;

            xpp.next();
            int eventType= xpp.getEventType();
            Log.i("eventType", String.valueOf(eventType));

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        //buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        /*if(tag.equals("totalCount")) {
                            xpp.next();
                            count = xpp.getText();

                        }*/
                        if(tag.equals("item"));// 첫번째 검색결과
                        else if(tag.equals("ITEM_IMAGE")){
                            //buffer.append("큰제품이미지 : ");
                            xpp.next();
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            //buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("CLASS_NAME")){
                            //buffer.append("분류명 : ");
                            xpp.next();
                            mediEffect.setText(xpp.getText());
                            //buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            //buffer.append("\n");//줄바꿈 문자 추가
                        }


                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item"))
                            //buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
            e.printStackTrace();
        }

        //buffer.append("파싱 끝\n");
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....

}
