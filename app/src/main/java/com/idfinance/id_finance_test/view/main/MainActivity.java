package com.idfinance.id_finance_test.view.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.idfinance.id_finance_test.R;
import com.idfinance.id_finance_test.view.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.imgShowPassword) ImageView imgShowPassword;
    @BindView(R.id.etPassword) EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgShowPassword.setOnClickListener(v -> {
            showPassword(etPassword.getInputType());
        });
    }

    private void showPassword(int type) {
        etPassword.setSelection(etPassword.length());
        if(type == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            imgShowPassword.setImageResource(R.drawable.baseline_visibility_off_24);
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            imgShowPassword.setImageResource(R.drawable.baseline_remove_red_eye_24);
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }
}
