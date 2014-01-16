package ru.rafaelrs.httpremotesingle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ru.rafaelrs.httpremotesingle.activity.MainActivity;

/**
 * Created by rafaelrs on 07.12.13.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, MainActivity.class);
        context.startService(startServiceIntent);
    }
}