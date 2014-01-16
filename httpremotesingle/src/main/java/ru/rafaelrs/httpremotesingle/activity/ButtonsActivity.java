package ru.rafaelrs.httpremotesingle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import ru.rafaelrs.httpremotesingle.HTTPMessaging;
import ru.rafaelrs.httpremotesingle.R;
import ru.rafaelrs.httpremotesingle.system.SystemMenubar;

public class ButtonsActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_buttons);

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
        switch (v.getId()) {
            case R.id.button_close:
                finish();
                break;
            case R.id.button_21:
                HTTPMessaging.send(this, "21");
                break;
            case R.id.button_22:
                HTTPMessaging.send(this, "22");
                break;
            case R.id.button_23:
                HTTPMessaging.send(this, "23");
                break;
            case R.id.button_24:
                HTTPMessaging.send(this, "24");
                break;
            case R.id.button_25:
                HTTPMessaging.send(this, "25");
                break;
        }
    }


}
