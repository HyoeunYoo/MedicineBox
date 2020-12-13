package com.example.medicinebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    ImageView btnBack, btnHome, btnSearch;
    TextView editSearch;
    ListView searchList;

    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);
        btnSearch = findViewById(R.id.btnSearch);

        editSearch = findViewById(R.id.editSearch);
        searchList = findViewById(R.id.searchList);

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

        //검색
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtra("search",editSearch.getText().toString()); //검색어 검색 결과화면으로 넘겨주기
                startActivity(intent);
                finish();
            }
        });

        // 메인에서 검색한 값 가져오기
        Intent intent = getIntent();
        final String word = intent.getExtras().getString("search");
        editSearch.setText(word);

        AsyncTask.execute(new Runnable() {        // 비동기 방식으로 해야된됨. 안그럼 잘 안됨.
            @Override
            public void run() {
                ArrayList<String> resultArray = new ArrayList<>();

                try {
                    resultArray = search(word);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("resultArray", String.valueOf(resultArray));
                int i;
                for (i=0; i < resultArray.size(); i++) {
                    list.add(resultArray.get(i));
                }

                getXmlData(word);

                SearchActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                    @Override
                    public void run() {
                        ArrayAdapter adapter = new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, list);
                        searchList.setAdapter(adapter);
                    }
                });

                /*Log.d("IN ASYNC", String.valueOf(flag));
                if(flag) {
                    //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(intent);
                }*/
            }
        });

        //결과 리스트에서 선택
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),list.get(position),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SearchSelect.class);
                intent.putExtra("name",list.get(position)); //검색어 검색 결과화면으로 넘겨주기
                startActivity(intent);
            }
        });
    }



    private ArrayList<String> search(String word) throws JSONException {

        REST_API search = new REST_API("search");

        String json = "{\"search\" : \"" + word + "\"}";               // json에서 변수명도 큰따옴표로 감싸야함.

        String result = search.post(json);

        ArrayList<String> searchArray = new ArrayList<>();

        Log.d("ACCOUNTload", "result : " + result); //쿼리 결과값
        JSONArray jsonArray = new JSONArray(result);
        for(int i = 0 ; i<jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String mediName = jsonObject.getString("medi_name");
            searchArray.add(mediName);
        }
        Log.i("searchArray", String.valueOf(searchArray));

//        Log.d("LOGIN", "result : " + result);
        if(result.equals("timeout")) {                                                          // 서버 연결 시간(5초) 초과시
            Log.d("ACCOUNTload", "TIMEOUT!!!!!");
//            토스트를 띄우고 싶은데 메인쓰레드에 접근할수 없다고 함. 그래서 이런식으로 쓰레드에 접근.
            SearchActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchActivity.this, "서버 연결 시간 초과", Toast.LENGTH_SHORT).show();
                }
            });
        } else if(result == null || result.equals("")){
            Log.d("ACCOUNTload", "FAIL!!!!!");
            SearchActivity.this.runOnUiThread(new Runnable() {                                       // UI 쓰레드에서 실행
                @Override
                public void run() {
                    Toast.makeText(SearchActivity.this, "검색결과 없음", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        } else {
            Log.d("ACCOUNTload", "SUCCESS!!!!!");
            return searchArray;
        }

        return null;
    }

    void getXmlData(String word){

        StringBuffer buffer=new StringBuffer();

        Log.i("word",word);
        //String name = URLEncoder.encode(str);//한글의 경우 인식이 안되기에 utf-8 방식으로 encoding..
        String key= "NNlPSgKdYOzfOQTW0r5g1EGKaq43lm%2FmEwfx%2Fyi09i6FtGJCPyG1fB2yIEvE4lCLfxs7X6O%2FXtZ89jslBzW%2BWw%3D%3D";
        String queryUrl="http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList?"+"ServiceKey="+key+"&numOfRows=100"+"&item_name="+word;
        Log.i("url",queryUrl);
        try {
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            Log.i("eventType", String.valueOf(eventType));

            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("ITEM_NAME")){
                            buffer.append("품목명 : ");
                            xpp.next();
                            list.add(xpp.getText());
                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
            e.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        //return buffer.toString();//StringBuffer 문자열 객체 반환

    }//getXmlData method....
}
