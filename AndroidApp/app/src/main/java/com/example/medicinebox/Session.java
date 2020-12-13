package com.example.medicinebox;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    static final private String PREF_USER_ID = "id";
    static final private String PREF_DEVICE_IP = "ip";

    static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUserData(Context context, String id, String ip) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(PREF_USER_ID, id);
        editor.putString(PREF_DEVICE_IP, ip);
        editor.commit();
    }

    public static void setUserID(Context context, String id) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(PREF_USER_ID, id);
        editor.commit();
    }

    public static void setDeviceIP(Context context, String ip) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(PREF_DEVICE_IP, ip);
        editor.commit();
    }

    public static String getUserID(Context context) {
        return getSharedPreference(context).getString(PREF_USER_ID, "");
    }

    public static String getDeviceIP(Context context) {
        return getSharedPreference(context).getString(PREF_DEVICE_IP, "");
    }

    public static void clearUserData(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.clear();
        editor.commit();
    }
}
