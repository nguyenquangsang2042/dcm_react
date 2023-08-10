package com.vuthao.VNADCM.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.splashscreen.SplashScreen;

import com.vuthao.VNADCM.BuildConfig;
import com.vuthao.VNADCM.R;
import com.vuthao.VNADCM.activity.TabActivity;
import com.vuthao.VNADCM.base.Constants;
import com.vuthao.VNADCM.base.DCMApplication;
import com.vuthao.VNADCM.base.Functions;
import com.vuthao.VNADCM.base.LocaleHelper;
import com.vuthao.VNADCM.base.Utility;
import com.vuthao.VNADCM.base.activity.BaseActivity;
import com.vuthao.VNADCM.base.api.ApiDCMController;
import com.vuthao.VNADCM.base.keyboard.KeyboardManager;
import com.vuthao.VNADCM.base.model.app.CurrentUser;
import com.vuthao.VNADCM.base.model.app.User;
import com.vuthao.VNADCM.databinding.ActivityLoginBinding;
import com.vuthao.VNADCM.login.presenter.LoginPresenter;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginPresenter.LoginListener, BaseActivity.PermissionListener {
    private ActivityLoginBinding binding;
    private LoginPresenter presenter;
    private boolean isAutoLogin;
    private boolean isShowPassword = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        checkUserPermission(this, new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE});

        checkNotification();
        init();
        enableLogin();
        checkLogin();

        binding.btnLogin.setOnClickListener(this);
        binding.edtUsername.setOnClickListener(this);
        binding.edtPassword.setOnClickListener(this);
        binding.lnUsername.setOnClickListener(this);
        binding.lnPassword.setOnClickListener(this);
        binding.imgShowPassword.setOnClickListener(this);
        binding.imgDeleteUsername.setOnClickListener(this);
    }

    private void init() {
        presenter = new LoginPresenter(this, this);

        if (BuildConfig.DEBUG) {
            if (Constants.BASE_URL.contains("uat")) {
                binding.edtUsername.setText("vnadmsuat.adminsite");
                binding.edtPassword.setText("vnadmsuat@123$%");
            } else {
                //binding.edtUsername.setText("vnadms.adminsite");
                //binding.edtPassword.setText("vnadms@123$%");
                binding.edtUsername.setText("kbvna.admin1@vietnamairlines.com");
                binding.edtPassword.setText("vna@05042023");
            }
        }

        binding.tvNote.setText(getString(R.string.updating_data));
    }

    private void checkLogin() {
        getPreferencesController().getUserLogin(userId -> {
            if (userId.isEmpty()) {
                isAutoLogin = false;

                binding.lnLogin.setVisibility(View.VISIBLE);
                binding.tvNote.setVisibility(View.INVISIBLE);
            } else {
                isAutoLogin = true;

                binding.lnLogin.setVisibility(View.INVISIBLE);
                binding.tvNote.setVisibility(View.VISIBLE);

                String u = getPreferencesController().getUser();
                User user = Functions.jsonToObj(u, User.class);
                CurrentUser.getInstance().setUser(user);

                Intent i = new Intent(this, TabActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void enableLogin() {
        binding.edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.imgDeleteUsername.setVisibility(editable.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                binding.btnLogin.setEnabled(editable.length() > 0 && binding.edtPassword.length() > 0);
            }
        });

        binding.edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.btnLogin.setEnabled(editable.length() > 0 && binding.edtUsername.length() > 0);
            }
        });
    }

    private void login() {
        binding.lnLogin.setVisibility(View.INVISIBLE);
        binding.tvNote.setVisibility(View.VISIBLE);
        presenter.auth(binding.edtUsername.getText().toString(), binding.edtPassword.getText().toString());
    }

    private void enablePassword() {
        if (isShowPassword) {
            binding.edtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            binding.edtPassword.setTypeface(ResourcesCompat.getFont(this, R.font.fonts), Typeface.ITALIC);
            binding.imgShowPassword.setImageResource(R.drawable.icon_hide_password);
            isShowPassword = false;
        } else {
            binding.edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            binding.edtPassword.setTypeface(ResourcesCompat.getFont(this, R.font.fonts), Typeface.ITALIC);
            binding.imgShowPassword.setImageResource(R.drawable.icon_show_password);
            isShowPassword = true;
        }
    }

    private void checkNotification() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String url = bundle.getString("Url", "");
            if (!url.isEmpty()) {
                Constants.mUrl = getIntent().getExtras().getString("Url");
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnLogin) {
            login();
        } else if (view == binding.edtUsername) {
            selectEditText(binding.edtUsername);
        } else if (view == binding.edtPassword) {
            selectEditText(binding.edtPassword);
        } else if (view == binding.lnUsername) {
            binding.edtUsername.requestFocus();
            KeyboardManager.showKeyBoard(binding.edtUsername, this);
        } else if (view == binding.lnPassword) {
            binding.edtPassword.requestFocus();
            KeyboardManager.showKeyBoard(binding.edtPassword, this);
        } else if (view == binding.imgShowPassword) {
            enablePassword();
        } else if (view == binding.imgDeleteUsername) {
            binding.edtUsername.setText("");
        }
    }

    @Override
    public void onAuthSuccess() {
        presenter.getCurrentUserInfo();
    }

    @Override
    public void onAuthError(String error) {
        error = getString(R.string.error_login);
        Utility.share.showAlertWithOnlyOK(error, this, () -> {
            binding.lnLogin.setVisibility(View.VISIBLE);
            binding.tvNote.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public void onLoginSuccess() {
        presenter.getAllMasterData(!isAutoLogin);
    }

    @Override
    public void onLoginError(String error) {
        if (isAutoLogin) {
            isAutoLogin = false;
            String username = getPreferencesController().getUsername();
            String password = getPreferencesController().getPassword();

            presenter.auth(username, password);
        } else {
            error = getString(R.string.error_login);
            Utility.share.showAlertWithOnlyOK(error, this, () -> {
                if (isAutoLogin) {
                    isAutoLogin = false;
                    Utility.share.resetData(this);
                }

                binding.lnLogin.setVisibility(View.VISIBLE);
                binding.tvNote.setVisibility(View.INVISIBLE);
            });
        }
    }

    @Override
    public void onGetAllMasterDataSuccess() {
        binding.tvNote.setText(getString(R.string.update_finish));
        Intent i = new Intent(this, TabActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onGetAllMasterDataError() {
        Log.d("ERROR", "onGetAllMasterDataError error");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.wrap(newBase, "en"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        DCMApplication.getInstance().trackScreenView("Login screen");
    }

    @Override
    public void OnAcceptedAllPermission() {

    }

    @Override
    public void OnCancelPermission() {
        Utility.share.showAlertWithOnlyOK(getString(R.string.error_permission), LoginActivity.this, () -> finish());
    }

    @Override
    public void OnNeverRequestPermission() {

    }
}