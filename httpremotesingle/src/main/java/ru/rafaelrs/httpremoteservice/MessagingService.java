package ru.rafaelrs.httpremoteservice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.util.Log;

import ru.rafaelrs.httpremotesingle.activity.Settings;

/**
 * Created by rafaelrs on 30.01.14.
 */
public class MessagingService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static final int FIRST_RUN = 5 * 1000;
    public static int INTERVAL;
    //public static final int INTERVAL = 10 * 1000;
    AlarmManager alarmManager;
    public static boolean isStarted = false;

    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        Log.v(this.getClass().getName(), "Service onCreate(). Started at " + new java.util.Date().toString());

        Intent intent = new Intent(this, OnAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        INTERVAL = Settings.getResendFreq(this) * 60 * 1000;
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + FIRST_RUN,
                INTERVAL, pendingIntent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isStarted = false;
        Log.v(this.getClass().getName(), "Service onDestroy(). Stop at "  + new java.util.Date().toString());

        if (alarmManager != null) {
            Intent intent = new Intent(this, OnAlarmReceiver.class);
            alarmManager.cancel(PendingIntent.getBroadcast(this, 0, intent, 0));
            Log.v(this.getClass().getName(), "AlarmManger unbinding at " + new java.util.Date().toString());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
