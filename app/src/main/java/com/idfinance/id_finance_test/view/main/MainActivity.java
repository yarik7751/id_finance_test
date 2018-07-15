package com.idfinance.id_finance_test.view.main;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.idfinance.id_finance_test.R;
import com.idfinance.id_finance_test.presenter.MainPresenter;
import com.idfinance.id_finance_test.relations.MainRelations;
import com.idfinance.id_finance_test.utils.Preferences;
import com.idfinance.id_finance_test.view.base.BaseActivity;
import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainRelations.IView, FingerPrintAuthCallback {

    private final String TAG = "MainActivity_log";

    @BindView(R.id.imgShowPassword) ImageView imgShowPassword;
    @BindView(R.id.imgVk) ImageView imgVk;
    @BindView(R.id.tvClearAll) TextView tvClearAll;
    @BindView(R.id.etPassword) EditText etPassword;
    @BindView(R.id.etLogin) EditText etLogin;
    @BindView(R.id.btnLogin) Button btnLogin;
    @BindView(R.id.cbSaveAll) CheckBox cbSaveAll;

    private MainPresenter mPresenter;
    //https://github.com/multidots/android-fingerprint-authentication
    private FingerPrintAuthHelper mFingerPrintAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);

        tvClearAll.setOnClickListener(v -> clearAll() );
        permissionSavePassword();
        imgShowPassword.setOnClickListener(v -> showPassword(etPassword.getInputType()) );
        btnLogin.setOnClickListener(v -> onLoginButtonClick() );
        imgVk.setOnClickListener(v -> VKSdk.login(this, VKScope.EMAIL) );
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFingerPrintAuthHelper.stopAuth();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                Log.d(TAG, "accessToken: " + res.accessToken);
                Log.d(TAG, "email: " + res.email);
                Log.d(TAG, "userId: " + res.userId);
                mPresenter.vkAuthorization();
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
        }/* else {
            clearAll();
        }*/
    }

    private void onLoginButtonClick() {
        mPresenter.onLoginClickButton(
            etLogin.getText().toString(),
            etPassword.getText().toString());
    }

    @Override
    public void showError() { showToast(R.string.login_error); }

    @Override
    public void showEmptyDataError() { showToast(R.string.empty_data); }

    //FingerPrintAuthCallback
    @Override
    public void onNoFingerPrintHardwareFound() {}

    @Override
    public void onBelowMarshmallow() {}

    @Override
    public void onNoFingerPrintRegistered() { showToast(R.string.no_fingerprint_registred); }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        etLogin.setText("kolya");
        etPassword.setText("098765");
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        if(errorCode == AuthErrorCodes.CANNOT_RECOGNIZE_ERROR) {
            showToast("Cannot recognize the fingerprint scanned!");
        } else {
            showToast("Error of fingerprint function!");
        }
    }
}
