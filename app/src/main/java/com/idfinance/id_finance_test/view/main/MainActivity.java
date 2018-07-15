package com.idfinance.id_finance_test.view.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.idfinance.id_finance_test.R;
import com.idfinance.id_finance_test.presenter.MainPresenter;
import com.idfinance.id_finance_test.relations.MainRelations;
import com.idfinance.id_finance_test.utils.Preferences;
import com.idfinance.id_finance_test.view.base.BaseActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainRelations.IView {

    private final String TAG = "MainActivity_log";

    @BindView(R.id.imgShowPassword) ImageView imgShowPassword;
    @BindView(R.id.imgVk) ImageView imgVk;

    @BindView(R.id.tvClearAll) TextView tvClearAll;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etLogin) EditText etLogin;

    @BindView(R.id.btnLogin) Button btnLogin;

    @BindView(R.id.cbSaveAll) CheckBox cbSaveAll;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        tvClearAll.setOnClickListener(v -> clearAll() );
        permissionSavePassword();
        imgShowPassword.setOnClickListener(v -> showPassword(etPassword.getInputType()) );
        btnLogin.setOnClickListener(v -> onLoginButtonClick() );
        imgVk.setOnClickListener(v -> VKSdk.login(this, VKScope.EMAIL) );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.d(TAG, "accessToken: " + res.accessToken);
                Log.d(TAG, "email: " + res.email);
                Log.d(TAG, "userId: " + res.userId);
                presenter.vkAuthorization();
            }

            @Override
            public void onError(VKError error) {
                showToast(R.string.vk_aut_error);
            }
        });
    }

    private void showPassword(int type) {
        //etPassword.setSelection(etPassword.length());
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
        });

        if(Preferences.isPermissionSavePassword()) {
            etPassword.setText(Preferences.getPassword());
            etLogin.setText(Preferences.getLogin());
        }
    }

    private void onLoginButtonClick() {
        presenter.onLoginClickButton(
            etLogin.getText().toString(),
            etPassword.getText().toString());
    }

    @Override
    public void showError() {
        showToast(R.string.login_error);
    }

    @Override
    public void showEmptyDataError() {
        showToast(R.string.empty_data);
    }
}
