package com.norah1to.simplenotification.Service;

import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

import androidx.annotation.RequiresApi;

import com.norah1to.simplenotification.View.MakeTodoActivity;

import java.lang.reflect.Method;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MakeTodoTileService extends TileService {
    public MakeTodoTileService() {
        super();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTileAdded() {
        getQsTile().setState(Tile.STATE_ACTIVE);
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        getQsTile().setState(Tile.STATE_ACTIVE);
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();
        Intent intent = new Intent(this, MakeTodoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (!isLocked()) {
           startActivity(intent);
           collapseStatusBar();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    //收起状态栏
    public  void collapseStatusBar() {
        Object service = getSystemService("statusbar");
        if (null == service)
            return;
        try {
            Class<?> clazz = Class.forName("android.app.StatusBarManager");
            int sdkVersion = android.os.Build.VERSION.SDK_INT;
            Method collapse = null;
            if (sdkVersion <= 16) {
                collapse = clazz.getMethod("collapse");
            } else {
                collapse = clazz.getMethod("collapsePanels");
            }
            collapse.setAccessible(true);
            collapse.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
