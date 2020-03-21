package com.mtem.asset_management_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Utility extends BaseActivity{
    public static void setTheme(Context context, int theme) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            prefs.edit().putInt(context.getString(R.string.prefs_theme_key), theme).apply();
    }
    public static int getTheme(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(context.getString(R.string.prefs_theme_key), 1);
    }
    public static void setLanguage(Context context,String language) {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(context.getString(R.string.prefs_language_key), language).apply();
    }
    public static String getLanguage(Context context) {
             SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
             String def=prefs.getString(context.getString(R.string.prefs_language_key), english);
             if(def==null){
                 return english;
             }else
                 return def;
    }
    public static void setlogin(Context context,String id,String pass) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(context.getString(R.string.pre_user_id), id).apply();
        prefs.edit().putString(context.getString(R.string.pre_user_pass), pass).apply();
    }
    public static String getUserName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String userInfo=prefs.getString(context.getString(R.string.pre_user_id), "");
            return userInfo;
    }
    public static String getPassword(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String userInfo=prefs.getString(context.getString(R.string.pre_user_pass), "");
            return userInfo;
    }
    public static void clearlogin(Context context) {
         SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(context.getString(R.string.pre_user_id), "").apply();
        prefs.edit().putString(context.getString(R.string.pre_user_pass), "").apply();
    }
}
