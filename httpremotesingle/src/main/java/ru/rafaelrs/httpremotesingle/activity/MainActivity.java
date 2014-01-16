package ru.rafaelrs.httpremotesingle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import ru.rafaelrs.httpremotesingle.HTTPMessaging;
import ru.rafaelrs.httpremotesingle.R;
import ru.rafaelrs.httpremotesingle.system.SystemMenubar;

public class MainActivity extends Activity implements View.OnClickListener {

    final Handler uiHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        TextView button1 = (TextView) findViewById(R.id.button_1);
        button1.setOnClickListener(this);
        TextView button2 = (TextView) findViewById(R.id.button_2);
        button2.setOnClickListener(this);
        ImageView buttonSettings = (ImageView) findViewById(R.id.button_settings);
        buttonSettings.setOnClickListener(this);

        //CheckBoxPreference enableMenu = (CheckBoxPreference) findPreference("enableMenu");
        if (Settings.isMenuEnabled(this)) {
            SystemMenubar.show();
        } else {
            SystemMenubar.hide();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_settings:
                startActivity(new Intent(this, Settings.class));
                break;
            case R.id.button_1:
                HTTPMessaging.send(this, "1", uiHandler);
                break;
            case R.id.button_2:
                startActivity(new Intent(this, ButtonsActivity.class));
                break;
        }
    }


}
