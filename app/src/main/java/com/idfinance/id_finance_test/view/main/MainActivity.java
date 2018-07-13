package com.idfinance.id_finance_test.view.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.idfinance.id_finance_test.R;
import com.idfinance.id_finance_test.utils.Preferences;
import com.idfinance.id_finance_test.view.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.imgShowPassword) ImageView imgShowPassword;
    @BindView(R.id.imgVk) ImageView imgVk;

    @BindView(R.id.tvClearAll) TextView tvClearAll;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etLogin) EditText etLogin;

    @BindView(R.id.btnLogin) Button btnLogin;

    @BindView(R.id.cbSaveAll) CheckBox cbSaveAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvClearAll.setOnClickListener(v -> {
            clearAll();
        });

        permissionSavePassword();

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

    private void clearAll() {
        etPassword.setText("");
        etLogin.setText("");
    }

    private void permissionSavePassword() {
        cbSaveAll.setChecked(Preferences.isPermissionSavePassword());
        cbSaveAll.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Preferences.setPermissionSavePassword(isChecked);
            cbSaveAll.setChecked(Preferences.isPermissionSavePassword());
        });

        if(Preferences.isPermissionSavePassword()) {
            etPassword.setText(Preferences.getPassword());
            etLogin.setText(Preferences.getLogin());
        } else {
            clearAll();
        }
    }
}
