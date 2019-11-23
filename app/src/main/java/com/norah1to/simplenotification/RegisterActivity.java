package com.norah1to.simplenotification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.norah1to.simplenotification.Http.HttpHelper;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText inputAccount;

    private TextInputEditText inputPassword;

    private TextInputEditText inputConfirmPassword;

    private MaterialButton btnRegister;

    private Thread HttpRequestThread;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 账号
        inputAccount = (TextInputEditText) findViewById(R.id.input_register_account);

        // 密码
        inputPassword = (TextInputEditText) findViewById(R.id.input_register_password);

        // 确认密码
        inputConfirmPassword = (TextInputEditText) findViewById(R.id.input_register_confirm_password);

        // 注册按钮
        btnRegister = (MaterialButton) findViewById(R.id.btn_register_register);
        btnRegister.setOnClickListener(v -> {
            HttpRequestThread = new Thread(() -> {
                HttpHelper.ResultBean result = HttpHelper.Register(
                        new HttpHelper.UserBean(inputAccount.getText().toString(), inputPassword.getText().toString()),
                        handler);
                handler.post(() -> {
                    Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
            HttpRequestThread.start();
        });
    }
}
