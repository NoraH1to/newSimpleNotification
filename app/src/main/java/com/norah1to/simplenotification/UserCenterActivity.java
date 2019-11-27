package com.norah1to.simplenotification;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
            showConfirmLogoutDialog();
        });
    }

    // 登出
    private void logout() {
        HttpRequestThread = new Thread(() -> {
            HttpHelper.ResultBean result =  HttpHelper.Logout(handler);
            handler.post(() -> {
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if (result.isSuccess()) {
                    finish();
                    new Thread(() -> {
                        MainActivity.mtodoViewModel.deleteAllTag();
                        MainActivity.mtodoViewModel.deleteAllTodo();
                    }).start();
                }
            });
        });
        HttpRequestThread.start();
    }

    // 确认是否登出
    private void showConfirmLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle(R.string.dialog_confirm_logout_title)
                .setMessage(R.string.dialog_confirm_logout_message)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}
