package com.idfinance.id_finance_test.router;

import android.app.Activity;
import android.content.Intent;

import com.idfinance.id_finance_test.relations.MainRelations;
import com.idfinance.id_finance_test.view.home.HomeActivity;

public class MainRouter implements MainRelations.IRouter {

    private Activity activity;

    public MainRouter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void showHomeActivity() {
        activity.startActivity(new Intent(activity, HomeActivity.class));
    }
}
