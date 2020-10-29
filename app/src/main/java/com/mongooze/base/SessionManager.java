package com.mongooze.base;

import android.content.Context;

import com.jachdev.commonlibs.utils.TinyDb;
public class SessionManager {
    private static final String TAG = SessionManager.class.getSimpleName();
    private TinyDb mTinyDb;

    public SessionManager(Context context) {
        mTinyDb = new TinyDb(context);
    }

    public boolean isUserAvailable() {
        return !getUsername().isEmpty() && !getEmail().isEmpty();
    }

    public String getUsername(){
        return mTinyDb.getString(AppConstant.Pref.KEY_USER);
    }

    public void setUsername(String username){
        mTinyDb.putString(AppConstant.Pref.KEY_USER, username);
    }

    public String getEmail(){
        return mTinyDb.getString(AppConstant.Pref.KEY_EMAIL);
    }

    public void setEmail(String putString){
        mTinyDb.putString(AppConstant.Pref.KEY_EMAIL, putString);
    }
}
