package com.example.medicinebox;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class ConnDevice {
    private final static String TAG = ConnDevice.class.getSimpleName();

//    final static String base_url = "http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:65004/";
    String device_ip = null;
    String base_url = null;
    String string_url;
    URL url = null;
    HttpURLConnection conn = null;
    InputStream in = null;
    JSONObject json = null;
    BufferedReader br = null;


    public ConnDevice(Context context) {
        device_ip = Session.getDeviceIP(context);
        base_url = "http://" + device_ip + ":60002";
    }


    public ConnDevice(Context context, String param) {
        device_ip = Session.getDeviceIP(context);
        base_url = "http://" + device_ip + ":60002/";
        string_url = base_url + param;


    }

    public boolean connect() {
        try {
            url = new URL(string_url);
            conn = (HttpURLConnection) url.openConnection();
//            in = new BufferedInputStream(conn.getInputStream());
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            conn.connect();

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {                       // 200
//                success
                return true;
            } else {
                return false;
            }

        } catch(Exception e) {
            e.toString();
        }
        return false;
    }
    public void disconnect() {
        try {
            if(conn != null) {
                if (br != null) {
                    br.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String post(String jsonMsg) {
        try {

            url = new URL(string_url);
            Log.i("post url",string_url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);

            conn.connect();


            Log.d("CONNECTION", jsonMsg);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jsonMsg);
            wr.flush();

            StringBuilder stringBuilder = new StringBuilder();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {                                   // 연결 성공
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    Log.d("line",line);
                    stringBuilder.append(line).append("\n");
                    //stringBuilder.append(line);
                }
                br.close();
                Log.i("CONNECTION", stringBuilder.toString());
                return stringBuilder.toString();
            } else {
                Log.i("CONNECTION", conn.getResponseMessage());
                return null;
            }
        }catch (SocketTimeoutException t) {
            t.printStackTrace();
            return "timeout";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }


    private String put(String jsonMsg) {
        try {
            url = new URL(string_url);
            Log.i("put url",string_url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("PUT");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);

            conn.connect();


            Log.d("CONNECTION", jsonMsg);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jsonMsg);
            wr.flush();

            StringBuilder stringBuilder = new StringBuilder();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {                                   // 연결 성공
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                br.close();
                return stringBuilder.toString();
                //JSONObject json = new JSONObject;/
            } else {
                Log.i("CONNECTION", conn.getResponseMessage());
                return null;
            }
        }catch (SocketTimeoutException t) {
            t.printStackTrace();
            return "timeout";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

    private String get(String param) {
        try {

            String _url = string_url + "?" + param;
            url = new URL(_url);
            Log.i("get url",_url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);

            Log.d("GET", "get: " + conn.getRequestMethod());


            Log.d("CONNECTION", param);

            StringBuilder stringBuilder = new StringBuilder();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {                                   // 연결 성공
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                br.close();
                Log.e("GET", "RECEIVED MESSAGE : " + stringBuilder.toString());
                return stringBuilder.toString();
                //JSONObject json = new JSONObject;/
            } else {
                Log.i("CONNECTION", conn.getResponseMessage());
                return null;
            }
        }catch (SocketTimeoutException t) {
            t.printStackTrace();
            return "timeout";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

    private String get() {
        try {

            String _url = string_url;
            url = new URL(_url);
            Log.i("get url",_url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);

            Log.d("GET", "get: " + conn.getRequestMethod());


//            Log.d("CONNECTION", param);

            StringBuilder stringBuilder = new StringBuilder();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {                                   // 연결 성공
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                br.close();
                Log.e("GET", "RECEIVED MESSAGE : " + stringBuilder.toString());
                return stringBuilder.toString();
                //JSONObject json = new JSONObject;/
            } else {
                Log.i("CONNECTION", conn.getResponseMessage());
                return null;
            }
        }catch (SocketTimeoutException t) {
            t.printStackTrace();
            return "timeout";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }


    private String delete(String jsonMsg) {
        try {
            url = new URL(string_url);
            Log.i("delete url",string_url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("DELETE");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setDefaultUseCaches(false);

            conn.connect();


            Log.d("CONNECTION", jsonMsg);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(jsonMsg);
            wr.flush();

            StringBuilder stringBuilder = new StringBuilder();
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {                                   // 연결 성공
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                br.close();
                return stringBuilder.toString();
                //JSONObject json = new JSONObject;/
            } else {
                Log.i("CONNECTION", conn.getResponseMessage());
                return null;
            }
        }catch (SocketTimeoutException t) {
            t.printStackTrace();
            return "timeout";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

//  복용
    public boolean dosing(int slot) {
        String json = "{ \"slot\" : \"" + slot + "\" }";
        string_url = base_url + "/dosing";
//        string_url = "http://ec2-3-34-54-94.ap-northeast-2.compute.amazonaws.com:65002/dosing";         // 테스트용. aws 서버로 테스트
        Log.d(TAG, "dosing slot " + slot + " dosing start");
        String result = post(json);
        Log.d(TAG, "dosing: result : " + result);
        if(result.equals("") || result == null || result.equals("null"))
            return false;
        else
            result = result.trim();
        if(result.equals("true")) {
            Log.e(TAG, "dosing " + slot + " SUCCESS");
            return true;
        } else {
            Log.e(TAG, "dosing " + slot + " FAIL");
            Log.e(TAG, "dosing" + slot + " : " + result);
            return false;
        }

    }

//    잔여량 가져오기
    public boolean quantity(int slot) {
//        String json = "{ \"slot\" : \"" + slot + "\" }";
        string_url = base_url + "/getQuantity";

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                String result = get();
//                result = result.trim();
//
//                if(result.equals("true")) {
//                    Log.e(TAG, "dosing " + slot + " SUCCESS");
//                    return true;
//                } else {
//                    Log.e(TAG, "dosing " + slot + " FAIL");
//                    Log.e(TAG, "dosing" + slot + " : " + result);
//                    return false;
//                }
//            }
//        });
        String result = get();
        result = result.trim();

        if(result.equals("true")) {
            Log.e(TAG, "dosing " + slot + " SUCCESS");
            return true;
        } else {
            Log.e(TAG, "dosing " + slot + " FAIL");
            Log.e(TAG, "dosing" + slot + " : " + result);
            return false;
        }

    }


//    투입구 열기
    public boolean openSlot(int slot) {
        string_url = base_url + "/open/" + slot;


        String result = get();
        result = result.trim();

        if(result.equals("true")) {
            Log.e(TAG, "Open Slot "+ slot + "SUCCESS");
            return true;
        } else {
            Log.e(TAG, "Open Slot " + slot + " FAIL");
            Log.e(TAG, "Open Slot " + slot + " Fail caused by : " + result);
            return false;
        }
    }

}
