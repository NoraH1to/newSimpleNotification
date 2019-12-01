package com.norah1to.simplenotification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.norah1to.simplenotification.Http.HttpHelper;

import java.util.Objects;

public class ChangePasswordActivity extends AppCompatActivity {

    public static final String TAG = "ChangePasswordActivity";

    private Handler handler = new Handler(Looper.getMainLooper());

    private Thread HttpThread;

    private TextInputLayout inputOldPasswordLayout;
    private TextInputEditText inputOldPassword;

    private TextInputLayout inputNewPasswordLayout;
    private TextInputEditText inputNewPassword;

    private TextInputLayout inputConfirmNewPasswordLayout;
    private TextInputEditText inputConfirmNewPassword;

    private MaterialButton btnChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        // 旧密码
        inputOldPasswordLayout = (TextInputLayout)
                findViewById(R.id.input_layout_change_password_old_password);
        inputOldPassword = (TextInputEditText)
                findViewById(R.id.input_change_password_old_password);
        inputOldPasswordLayout.setErrorIconDrawable(null);


        // 新密码
        inputNewPasswordLayout = (TextInputLayout)
                findViewById(R.id.input_layout_change_password_new_password);
        inputNewPassword = (TextInputEditText)
                findViewById(R.id.input_change_password_new_password);
        inputNewPasswordLayout.setErrorIconDrawable(null);


        // 确认新密码
        inputConfirmNewPasswordLayout = (TextInputLayout)
                findViewById(R.id.input_layout_change_password_confirm_new_password);
        inputConfirmNewPassword = (TextInputEditText)
                findViewById(R.id.input_change_password_confirm_new_password);
        inputConfirmNewPasswordLayout.setErrorIconDrawable(null);


        // 修改按钮
        btnChangePassword = (MaterialButton)
                findViewById(R.id.btn_change_password_change_password);
        btnChangePassword.setOnClickListener(v -> {
            // 先判断输入是否合法
            if (inputOldPasswordLayout.isErrorEnabled()
                    || inputNewPasswordLayout.isErrorEnabled()
                    || inputConfirmNewPasswordLayout.isErrorEnabled()) {
                Toast.makeText(this,
                        getResources().getString(R.string.toast_register_input_err),
                        Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            // 进行网络请求
            HttpThread = new Thread(() -> {
                HttpHelper.ResultBean resultBean = HttpHelper.changePssword(
                        handler,
                        new HttpHelper.ChangePasswordBean(
                                inputOldPassword.getText().toString(),
                                inputNewPassword.getText().toString())
                );
                handler.post(() -> {
                    Toast.makeText(this, resultBean.getMessage(), Toast.LENGTH_SHORT).show();
                    if (resultBean.isSuccess()) {
                        finish();
                    }
                });
            });
            HttpThread.start();
        });

        // 设置确认新密码框的 done 事件
        inputConfirmNewPassword.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    btnChangePassword.performClick();
                    return true;
                default:
                    return false;
            }
        });


        // 监听旧密码输入
        inputOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkOldPasswordInput(s);
                checkNewPasswordInput(
                        Objects.requireNonNull(inputNewPassword.getText()).toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // 监听新密码输入
        inputNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkNewPasswordInput(s);
                checkConfirmNewPasswordInput(
                        Objects.requireNonNull(inputConfirmNewPassword.getText()).toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        // 监听确认新密码输入
        inputConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkConfirmNewPasswordInput(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    // 检查旧密码合法性
    private void checkOldPasswordInput(CharSequence s) {
        if (s.length() < 8 || s.length() > 32) {
            inputOldPasswordLayout.setError(
                    getResources().getString(R.string.register_input_err_length));
            inputOldPasswordLayout.setErrorEnabled(true);
        } else {
            inputOldPasswordLayout.setErrorEnabled(false);
        }
    }

    // 检查新密码合法性
    private void checkNewPasswordInput(CharSequence s) {
        if (s.toString().equals(Objects.requireNonNull(inputOldPassword.getText()).toString())) {
            inputNewPasswordLayout.setError(
                    getResources().getString(R.string.change_password_input_err_new_password_fail));
            inputNewPasswordLayout.setErrorEnabled(true);
        } else if (s.length() < 8 || s.length() > 32) {
            inputNewPasswordLayout.setError(
                    getResources().getString(R.string.register_input_err_length));
            inputNewPasswordLayout.setErrorEnabled(true);
        } else {
            inputNewPasswordLayout.setErrorEnabled(false);
        }
    }

    // 检查确认新密码合法性
    private void checkConfirmNewPasswordInput(CharSequence s) {
        if (!s.toString().equals(Objects.requireNonNull(inputNewPassword.getText()).toString())) {
            inputConfirmNewPasswordLayout.setError(
                    getResources().getString(R.string.register_input_err_confirm_fail));
            inputConfirmNewPasswordLayout.setErrorEnabled(true);
        } else if (s.length() < 8 || s.length() > 32) {
            inputConfirmNewPasswordLayout.setError(
                    getResources().getString(R.string.register_input_err_length));
            inputConfirmNewPasswordLayout.setErrorEnabled(true);
        } else {
            inputConfirmNewPasswordLayout.setErrorEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        inputOldPassword.requestFocus();
    }
}
