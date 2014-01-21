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

// Класс для отправки HTTP запросов
public class HTTPMessaging {

    private static final String TAG = HTTPMessaging.class.toString();

    // Функция, которая формирует строку параметров для будущего запроса
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

    // Статический метод непосредственно самой отправки сообщения
    public static void send(final Context context, final String message, final Handler uiHandler) {

        // Важно! Отправка должна происходить обязательно в отдельном потоке, т.к. из UI потока
        // нельзя работать с HTTP запросами
        new Thread() {
            public void run() {
                StringBuilder builder = new StringBuilder();
                try {
                    // Определяем адрес для отправки и добавляем туда параметры
                    URL url = new URL("http://qualitybutton.ru/click.php" + getRequestParameters(context, message));
                    final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    // Выполняем запрос
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(false);
                    urlConnection.connect();
                    Log.e(TAG, "HTTP request result: " + urlConnection.getResponseCode());

                    // Делаем оповещение о результате запроса. Используем uiHandler для выполнения кода
                    // работающего с UI в потоке UI
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

                    // Необязательный для задачи кусок - считываем ответ от сервера
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

    // Вариант функции без указания хендлера UI потока. В это случае тосты не выводятся
    public static void send(final Context context, final String message) {
        send(context, message, null);
    }
}
