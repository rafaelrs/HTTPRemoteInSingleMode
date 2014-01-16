package ru.rafaelrs.httpremotesingle;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import ru.rafaelrs.httpremotesingle.activity.Settings;
import ru.rafaelrs.utils.StringProc;

/**
 * Created by rafaelrs on 16.01.14.
 */
public class HTTPMessaging {

    private static final String TAG = HTTPMessaging.class.toString();

    private static String getRequestParameters(Context context, String message) {

        StringBuilder builder = new StringBuilder();

        try {
            builder.append("?device_id=" + URLEncoder.encode(Settings.getDeviceId(context), "UTF-8"));
            builder.append("&client_id=" + URLEncoder.encode(Settings.getClientId(context), "UTF-8"));
            builder.append("&item=" + URLEncoder.encode(message, "UTF-8"));
            builder.append("&date=" + URLEncoder.encode(String.valueOf(System.currentTimeMillis()), "UTF-8"));
            String md5sum = StringProc.md5(builder.toString());
            builder.append("&sum=" + URLEncoder.encode(md5sum, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Building query error: " + e.getMessage(), e);
        }
        return builder.toString();
    }

    public static void send(final Context context, final String message, final Handler uiHandler) {

        new Thread() {
            public void run() {
                StringBuilder builder = new StringBuilder();
                try {
                    URL url = new URL("http://qualitybutton.ru/click.php" + getRequestParameters(context, message));
                    final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(false);
                    urlConnection.connect();
                    Log.e(TAG, "HTTP request result: " + urlConnection.getResponseCode());
                    if (uiHandler != null) {
                        uiHandler.post(new Runnable() {
                            public void run() {
                                try {
                                    if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                                        Toast.makeText(context, "HTTP request sending error", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(context, "HTTP request sended", Toast.LENGTH_LONG).show();
                                    }
                                } catch (IOException e) {
                                    Log.e(TAG, "Message showing error: " + e.getMessage(), e);
                                }
                            }
                        });
                    }

                    InputStream content = urlConnection.getInputStream();

                    byte[] buffer = new byte[1024];
                    while (content.read(buffer) > 0) {
                        builder.append(new String(buffer));
                    }
                    content.close();
                }
                catch (ClientProtocolException e) {
                    Log.e(TAG, "HTTP request error: " + e.getMessage(), e);
                }
                catch (IOException e) {
                    Log.e(TAG, "HTTP request error: " + e.getMessage(), e);
                }

            }
        }.start();
    }

    public static void send(final Context context, final String message) {
        send(context, message, null);
    }
}
