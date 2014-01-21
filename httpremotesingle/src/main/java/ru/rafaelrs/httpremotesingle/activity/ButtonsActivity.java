package ru.rafaelrs.httpremotesingle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import ru.rafaelrs.httpremotesingle.HTTPMessaging;
import ru.rafaelrs.httpremotesingle.R;

// Активити с кнопка 21-25
public class ButtonsActivity extends Activity implements View.OnClickListener {

    // Объявим хэндлер для вывода информации в пользовательский интерфейс из параллельных потоков
    final Handler uiHandler = new Handler();

    // Объявим переменные необходимые для организации закрытия формы после 30 секунд
    private final static long SHOW_PERIOD = 30000;

    // В принципе отедльный хендлер можно было не заводить, сделано для более легкой переносимости кода
    private final Handler delayhandler = new Handler();
    private final Runnable splashTask = new Runnable() {
        @Override
        public void run() {
            // Закрываем активити
            finish();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        // Убираем все ожидающие сообщения и публикуем новые сообщения на отложенное
        // закрытие активити (без этого она может закрыться раньше времени)
        delayhandler.removeCallbacks(splashTask);
        delayhandler.postDelayed(splashTask, SHOW_PERIOD);
    }

    @Override
    protected void onPause() {
        // Убираем все ожидающие сообщения на отложенное закрытие активити при выходе из нее
        delayhandler.removeCallbacks(splashTask);
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Убираем заголовок окна и растягиваем на весь экран
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_buttons);

        // Привязываем к кнопка обработчик их нажатия
        TextView button21 = (TextView) findViewById(R.id.button_21);
        button21.setOnClickListener(this);
        TextView button22 = (TextView) findViewById(R.id.button_22);
        button22.setOnClickListener(this);
        TextView button23 = (TextView) findViewById(R.id.button_23);
        button23.setOnClickListener(this);
        TextView button24 = (TextView) findViewById(R.id.button_24);
        button24.setOnClickListener(this);
        TextView button25 = (TextView) findViewById(R.id.button_25);
        button25.setOnClickListener(this);
        TextView buttonClose = (TextView) findViewById(R.id.button_close);
        buttonClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // Отправляем сообщение по HTTP в соответствии с нажатой кнопкой
        switch (v.getId()) {
            case R.id.button_close:
                finish();
                break;
            case R.id.button_21:
                HTTPMessaging.send(this, "21", uiHandler);
                break;
            case R.id.button_22:
                HTTPMessaging.send(this, "22", uiHandler);
                break;
            case R.id.button_23:
                HTTPMessaging.send(this, "23", uiHandler);
                break;
            case R.id.button_24:
                HTTPMessaging.send(this, "24", uiHandler);
                break;
            case R.id.button_25:
                HTTPMessaging.send(this, "25", uiHandler);
                break;
        }
    }


}
