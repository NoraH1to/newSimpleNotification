package com.norah1to.simplenotification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.norah1to.simplenotification.Http.HttpHelper;

public class UserCenterActivity extends AppCompatActivity {

    private Handler handler = new Handler(Looper.getMainLooper());

    private Thread HttpRequestThread;

    private MaterialButton btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        // 登出
        btnLogout = (MaterialButton) findViewById(R.id.btn_user_center_logout);
        btnLogout.setOnClickListener(v -> {
            HttpRequestThread = new Thread(() -> {
                HttpHelper.ResultBean result =  HttpHelper.Logout(handler);
                handler.post(() -> {
                    Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
            HttpRequestThread.start();
        });
    }
}
