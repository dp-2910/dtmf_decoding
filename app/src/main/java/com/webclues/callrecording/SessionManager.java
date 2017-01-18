package com.webclues.callrecording;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by android on 5/1/17.
 */

public class SessionManager {

    public static final String KEY_NAME = "serviceStatus";
    // Sharedpref file name
    private static final String PREF_NAME = "ServicePref";
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean getServiceStatus() {
        boolean serviceFlag = pref.getBoolean(KEY_NAME, false);
        return serviceFlag;
    }

    public void setServiceStatus(boolean flag) {
        editor.putBoolean(KEY_NAME, flag);
        editor.commit();
    }

}
