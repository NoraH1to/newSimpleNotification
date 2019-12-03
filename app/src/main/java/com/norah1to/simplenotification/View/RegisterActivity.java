package com.norah1to.simplenotification.View;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.norah1to.simplenotification.Http.HttpHelper;
import com.norah1to.simplenotification.R;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout inputAccountLayout;
    private TextInputEditText inputAccount;

    private TextInputLayout inputPasswordLayout;
    private TextInputEditText inputPassword;

    private TextInputLayout inputConfirmPasswordLayout;
    private TextInputEditText inputConfirmPassword;

    private MaterialButton btnRegister;

    private Thread HttpRequestThread;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 账号
        inputAccountLayout = (TextInputLayout)
                findViewById(R.id.input_layout_register_account);
        inputAccount = (TextInputEditText) findViewById(R.id.input_register_account);
        inputAccountLayout.setErrorIconDrawable(null);

        // 密码
        inputPasswordLayout = (TextInputLayout)
                findViewById(R.id.input_layout_register_password);
        inputPassword = (TextInputEditText) findViewById(R.id.input_register_password);
        inputPasswordLayout.setErrorIconDrawable(null);

        // 确认密码
        inputConfirmPasswordLayout = (TextInputLayout)
                findViewById(R.id.input_layout_register_confirm_password);
        inputConfirmPassword = (TextInputEditText) findViewById(R.id.input_register_confirm_password);
        inputConfirmPasswordLayout.setErrorIconDrawable(null);

        // 注册按钮
        btnRegister = (MaterialButton) findViewById(R.id.btn_register_register);
        btnRegister.setOnClickListener(v -> {
            // 先判断输入是否合法
            if (inputConfirmPasswordLayout.isErrorEnabled()
            || inputPasswordLayout.isErrorEnabled()
            || inputAccountLayout.isErrorEnabled()) {
                Toast.makeText(this,
                        getResources().getString(R.string.toast_register_input_err),
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            // 进行网络请求
            HttpRequestThread = new Thread(() -> {
                HttpHelper.ResultBean result = HttpHelper.Register(
                        new HttpHelper.UserBean(inputAccount.getText().toString(), inputPassword.getText().toString()),
                        handler);
                handler.post(() -> {
                    Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    if (result.isSuccess()) {
                        finish();
                    }
                });
            });
            HttpRequestThread.start();
        });

        // 设置确认密码框的 done 事件
        inputConfirmPassword.setOnEditorActionListener(((v, actionId, event) -> {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    btnRegister.performClick();
                    return true;
                default:
                    return false;
            }
        }));

        // 监听账号输入
        inputAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkAccountInput(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 监密码输入
        inputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPasswordInput(s);
                checkConfirmPasswordInput(
                        Objects.requireNonNull(inputConfirmPassword.getText()).toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 监听确认密码输入
        inputConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkConfirmPasswordInput(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // 检查账号合法性
    private void checkAccountInput(CharSequence s) {
        if (s.length() < 8 || s.length() > 32) {
            inputAccountLayout.setError(
                    getResources().getString(R.string.register_input_err_length));
            inputAccountLayout.setErrorEnabled(true);
        } else {
            inputAccountLayout.setErrorEnabled(false);
        }
    }

    // 检查密码合法性
    private void checkPasswordInput(CharSequence s) {
        if (s.length() < 8 || s.length() > 32) {
            inputPasswordLayout.setError(
                    getResources().getString(R.string.register_input_err_length));
            inputPasswordLayout.setErrorEnabled(true);
        } else {
            inputPasswordLayout.setErrorEnabled(false);
        }
    }

    // 检查确认密码合法性
    private void checkConfirmPasswordInput(CharSequence s) {
        if (!s.toString().equals(Objects.requireNonNull(inputPassword.getText()).toString())) {
            inputConfirmPasswordLayout.setError(
                    getResources().getString(R.string.register_input_err_confirm_fail));
            inputConfirmPasswordLayout.setErrorEnabled(true);
        } else if (s.length() < 8 || s.length() > 32) {
            inputConfirmPasswordLayout.setError(
                    getResources().getString(R.string.register_input_err_length));
            inputConfirmPasswordLayout.setErrorEnabled(true);
        } else {
            inputConfirmPasswordLayout.setErrorEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputAccount.requestFocus();
    }
}
