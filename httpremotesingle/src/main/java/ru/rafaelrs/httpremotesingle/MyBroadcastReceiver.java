package ru.rafaelrs.httpremotesingle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.rafaelrs.httpremotesingle.activity.MainActivity;

// Класс используемый для старта приложения при загрузке системы
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, MainActivity.class);
        context.startService(startServiceIntent);
    }
}