package ru.rafaelrs.httpremotesingle;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.rafaelrs.httpremotesingle.system.SystemMenubar;

public class MainActivity extends Activity implements View.OnClickListener {

    private int clickCounter = 0;
    private boolean toolsPanelIsHidden = true;

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
        TextView buttonSettings = (TextView) findViewById(R.id.button_settings);
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
                new Thread() {
                    public void run() {
                        builder = new StringBuilder();
                        try {
                            URL url = new URL(input_url.getText().toString());
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                            urlConnection.setRequestMethod("GET");
                            urlConnection.setDoOutput(false);
                            urlConnection.connect();

                            InputStream content = urlConnection.getInputStream();

                            byte[] buffer = new byte[1024];
                            while (content.read(buffer) > 0) {
                                builder.append(new String(buffer));
                            }
                            content.close();
                        }
                        catch (ClientProtocolException e) {
                            e.printStackTrace();
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }

                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                content_response.setText(builder.toString());
                            }
                        });

                    }
                }.start();
                break;
            case R.id.button_2:
                break;
        }
    }
}
