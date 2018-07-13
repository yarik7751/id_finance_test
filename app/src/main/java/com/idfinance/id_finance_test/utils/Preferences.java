package com.idfinance.id_finance_test.utils;

import android.content.Context;
import android.content.ContextWrapper;

import com.pixplicity.easyprefs.library.Prefs;

public class Preferences {

    public static final String SP_NAME = "com.idfinance.id_finance_test";
    private static final String SP_PERM_SAVE = "SP_PERM_SAVE";
    private static final String SP_LOGIN = "SP_LOGIN";
    private static final String SP_PASSWORD = "SP_PASSWORD";

    public static void setPermissionSavePassword(boolean save) {
        Prefs.putBoolean(SP_PERM_SAVE, save);
    }

    public static boolean isPermissionSavePassword() {
        return Prefs.getBoolean(SP_PERM_SAVE, false);
    }

    public static void setLogin(String login) {
        Prefs.putString(SP_LOGIN, login);
    }

    public static String getLogin() {
        return Prefs.getString(SP_LOGIN, "");
    }

    public static void setPassword(String password) {
        Prefs.putString(SP_PASSWORD, password);
    }

    public static String getPassword() {
        return Prefs.getString(SP_PASSWORD, "");
    }
}
