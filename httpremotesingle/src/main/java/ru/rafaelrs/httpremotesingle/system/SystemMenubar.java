package ru.rafaelrs.httpremotesingle.system;

import android.os.Build;
import android.util.Log;

import java.io.IOException;

// Класс используемый для включения выключения системной панели. Требует прав ROOT!
public class SystemMenubar {

    private static final String TAG = SystemMenubar.class.toString();

    // Выключаем панель
    public static void hide() {
        try{
            Build.VERSION_CODES vc = new Build.VERSION_CODES();
            Build.VERSION vr = new Build.VERSION();
            String ProcID = "79"; //HONEYCOMB и позднее

            //v.RELEASE  //4.0.3
            if(vr.SDK_INT >= vc.ICE_CREAM_SANDWICH){
                ProcID = "42"; //ICS и позднее
            }

            //REQUIRES ROOT
            Process proc = Runtime.getRuntime().exec(
                    new String[]{"su","-c",
                            "service call activity "+ ProcID +" s16 com.android.systemui"});
            proc.waitFor();
        } catch(Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }

    // Включаем панель
    public static void show() {
        try {
            Runtime.getRuntime().exec(
                    new String[]{"am","startservice","-n",
                            "com.android.systemui/.SystemUIService"});
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage(), ex);
        }
    }
}
