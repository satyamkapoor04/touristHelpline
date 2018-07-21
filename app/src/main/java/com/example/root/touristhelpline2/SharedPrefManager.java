package com.example.root.touristhelpline2;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "App4tourist";
    private static final String key_username = "keyusername";
    private static final String key_gender = "keygender";
    private static final String key_email = "keyemail";

    private static SharedPrefManager mInstance;
    private static Context mContext;

    private SharedPrefManager (Context context) {
        mContext = context;
    }

    public static synchronized SharedPrefManager getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean isLoggedIn () {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(key_username, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User (sharedPreferences.getString(key_username,null), sharedPreferences.getString(key_gender, null), sharedPreferences.getString(key_email, null));
    }

    public void userLogin (User user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key_username,user.getUsername());
        editor.putString(key_gender,user.getGender());
        editor.putString(key_email,user.getEmail());
        editor.apply();
    }

    public void logout () {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
