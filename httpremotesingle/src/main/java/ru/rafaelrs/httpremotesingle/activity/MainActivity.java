package ru.rafaelrs.httpremotesingle.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;

import ru.rafaelrs.httpremotesingle.HTTPMessaging;
import ru.rafaelrs.httpremotesingle.R;
import ru.rafaelrs.httpremotesingle.system.SystemMenubar;

// Это начальная активити, с нее начинаем работу
public class MainActivity extends Activity implements View.OnClickListener {

    // Объявим хэндлер для вывода информации в пользовательский интерфейс из параллельных потоков
    final Handler uiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Убираем заголовок окна и растягиваем на весь экран
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // Привязываем к кнопка обработчик их нажатия
        TextView button1 = (TextView) findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        TextView button2 = (TextView) findViewById(R.id.button_2);
        button2.setOnClickListener(this);
        ImageView buttonSettings = (ImageView) findViewById(R.id.button_settings);
        buttonSettings.setOnClickListener(this);

        // Если в настройках указано, что системную панель надо убирать - убираем
        if (Settings.isMenuEnabled(this)) {
            SystemMenubar.show();
        } else {
            SystemMenubar.hide();
        }
    }

    @Override
    public void onClick(View v) {

        // Обработка нажатий на кнопки
        switch (v.getId()) {
            // Кнопка открытия настроек
            case R.id.button_settings:
                openSettings();
                break;
            // Кнопка отправки сообщения 1
            case R.id.button_1:
                HTTPMessaging.send(this, "1", uiHandler);

                // Откроем уведомление о том, что сообщение отправлено
                //Intent popupIntent = new Intent(this, PopupActivity.class);
                //popupIntent.putExtra(PopupActivity.KEY_TEXT, getResources().getString(R.string.text_thanks));
                //startActivity(popupIntent);
                Toast.makeText(this, getResources().getString(R.string.text_thanks), Toast.LENGTH_LONG).show();
                break;
            // Кнопка открытия набора кнопок 21-25
            case R.id.button_2:
                startActivity(new Intent(this, ButtonsActivity.class));
                break;
        }
    }

    private void openSettings() {

        final String currPass = Settings.getPassword(this);
        final Intent settingsIntent = new Intent(this, Settings.class);
        if (Settings.getPassword(this) != "") {
            // Используем AlertDialog для того чтобы запросить пароль (если в настройках он указан)
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle(getResources().getString(R.string.text_passheader));
            alert.setMessage(getResources().getString(R.string.text_passtext));

            // Добавим поле ввода
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton(getResources().getString(R.string.action_ok), new DialogInterface.OnClickListener() {
                // Обработчик ввода пароля
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    // Совпало? откроем настройки
                    if (currPass.equals(value)) {
                        startActivity(settingsIntent);
                    } else {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.text_passincorrect), Toast.LENGTH_LONG).show();
                    }
                }
            });

            alert.setNegativeButton(getResources().getString(R.string.action_cancel), new DialogInterface.OnClickListener() {
                // Обработчик отмены
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });

            alert.show();
        } else {
            // Если пароль пустой, значит настройки можно открыть без него
            startActivity(settingsIntent);
        }

    }
}
