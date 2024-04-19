package com.example.medwheels_pat1;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.medwheels_pat1.HelperClass;

public class PowerButtonService extends Service {
    private static final String TAG = "PowerButtonService";
    private static final int TRIPLE_CLICK_THRESHOLD = 3;
    private static final long RESET_DELAY_MILLIS = 2000; // 2 seconds

    private static int clickCount = 0;
    private static long lastClickTime = 0;

    private BroadcastReceiver powerButtonReceiver;
    @Override
    public void onCreate() {
        super.onCreate();

        powerButtonReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastClickTime > RESET_DELAY_MILLIS) {
                        clickCount = 1;
                    } else {
                        clickCount++;
                    }
                    lastClickTime = currentTime;

                    if (clickCount == TRIPLE_CLICK_THRESHOLD) {

//                        HelperClass helperClass = new HelperClass(email,pass, name);
                        Log.d(TAG, "Triple click detected!");
                        clickCount = 0;
                    }
                }
            }

        };

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(powerButtonReceiver, filter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}