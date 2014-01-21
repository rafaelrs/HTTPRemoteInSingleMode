package ru.rafaelrs.httpremotesingle.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import ru.rafaelrs.httpremotesingle.R;

// Класс высплывающего сообщения
public class PopupActivity extends Activity {

    public static final String KEY_TEXT = "ru.rafaelrs.httpremotesingle.popup_text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        // Получаем текст сообщения и выводим
        TextView infoText = (TextView)findViewById(R.id.text_infomessage);
        infoText.setText(getIntent().getStringExtra(KEY_TEXT));
    }
}
