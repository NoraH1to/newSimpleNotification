package com.norah1to.simplenotification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.norah1to.simplenotification.Http.HttpHelper;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";

    private Handler handler = new Handler(Looper.getMainLooper());

    // 账号
    private TextInputEditText inputAccount;

    // 密码
    private TextInputEditText inputPassword;

    // 登入按钮
    private MaterialButton btnLogin;

    // 注册按钮
    private MaterialButton btnRegister;

    // 网络请求线程
    private static Thread httpRequestThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 账号
        inputAccount = (TextInputEditText) findViewById(R.id.input_login_account);

        // 密码
        inputPassword = (TextInputEditText) findViewById(R.id.input_login_password);

        // 登入按钮
        btnLogin = (MaterialButton) findViewById(R.id.btn_login_login);
        btnLogin.setOnClickListener(v -> {
            // 子线程 http 请求
            httpRequestThread = new Thread(() -> {
                HttpHelper.ResultBean result = HttpHelper.Login(
                        new HttpHelper.UserBean(inputAccount.getText().toString(),
                        inputPassword.getText().toString()),
                        handler);
                // 执行完后请求主线程返回结果提示
                handler.post(() -> {
                    Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    if (result.isSuccess()) {
                        finish();
                    }
                });
            });
            httpRequestThread.start();
        });

        // 注册按钮
        btnRegister = (MaterialButton) findViewById(R.id.btn_login_register);
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        inputPassword.setOnEditorActionListener(((v, actionId, event) -> {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    btnLogin.performClick();
                    break;
            }
            return true;
        }));
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        inputAccount.requestFocus();
    }
}
