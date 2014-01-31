package ru.rafaelrs.httpremoteservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ru.rafaelrs.httpremotesingle.HTTPMessaging;

/**
 * Created by rafaelrs on 30.01.14.
 */
public class OnAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.v(this.getClass().getName(), "Service catch alarm message at: " + new java.util.Date().toString());

        new Thread() {
            // Важно! Отправка должна происходить обязательно в отдельном потоке, т.к. нельзя
            // вешать ресивер
            public void run() {
                HTTPMessaging.sendSheduledPackets(context);
            }
        }.start();
    }
}
