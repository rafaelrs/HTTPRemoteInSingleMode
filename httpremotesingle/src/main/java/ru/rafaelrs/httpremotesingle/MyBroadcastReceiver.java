package ru.rafaelrs.httpremotesingle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.rafaelrs.httpremoteservice.MessagingService;
import ru.rafaelrs.httpremotesingle.activity.MainActivity;

// Класс используемый для старта приложения при загрузке системы и старта сервиса
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, MainActivity.class);
        context.startService(startServiceIntent);
        Intent serviceLauncher = new Intent(context, MessagingService.class);
        context.startService(serviceLauncher);
    }
}