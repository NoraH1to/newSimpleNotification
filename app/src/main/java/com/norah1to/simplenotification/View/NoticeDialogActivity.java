package com.norah1to.simplenotification.View;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NoticeDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(android.R.style.Theme_Material_Light_Dialog_NoActionBar);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Title");
        dialog.setMessage("Message");
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();

//        setContentView(R.layout.activity_notice_dialog);
    }
}
