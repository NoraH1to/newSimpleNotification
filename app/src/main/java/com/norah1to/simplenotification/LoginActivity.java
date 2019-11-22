package com.norah1to.simplenotification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.norah1to.simplenotification.Http.HttpHelper;

public class LoginActivity extends AppCompatActivity {

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

        inputAccount = (TextInputEditText) findViewById(R.id.input_login_account);

        inputPassword = (TextInputEditText) findViewById(R.id.input_login_password);

        btnLogin = (MaterialButton) findViewById(R.id.btn_login_login);
        btnLogin.setOnClickListener(v -> {
            // Todo: 登入请求
            try {
                if (httpRequestThread != null) {
                    httpRequestThread.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            httpRequestThread = new Thread(() -> {
                HttpHelper.Login(new HttpHelper.UserBean(inputAccount.getText().toString(),
                        inputPassword.getText().toString()));
            });
            httpRequestThread.start();
        });

        btnRegister = (MaterialButton) findViewById(R.id.btn_login_register);

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
    protected void onStart() {
        super.onStart();

        inputAccount.requestFocus();
    }


}
