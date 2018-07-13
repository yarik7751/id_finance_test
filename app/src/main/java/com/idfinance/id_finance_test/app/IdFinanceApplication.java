package com.idfinance.id_finance_test.app;

import android.app.Application;
import android.content.ContextWrapper;

import com.idfinance.id_finance_test.utils.Preferences;
import com.pixplicity.easyprefs.library.Prefs;
import com.vk.sdk.VKSdk;

public class IdFinanceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(Preferences.SP_NAME)
                .setUseDefaultSharedPreference(true)
                .build();
        VKSdk.initialize(this);
    }
}
