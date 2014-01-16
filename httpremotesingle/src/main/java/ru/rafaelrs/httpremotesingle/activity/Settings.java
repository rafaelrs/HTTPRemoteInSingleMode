package ru.rafaelrs.httpremotesingle.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ru.rafaelrs.httpremotesingle.R;
import ru.rafaelrs.httpremotesingle.system.SystemMenubar;

/**
 * Created by 1111 on 18.12.13.
 */
public class Settings extends PreferenceActivity implements Preference.OnPreferenceClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        Preference enableMenu = findPreference("enableMenu");
        enableMenu.setOnPreferenceClickListener(this);
        //EditTextPreference deviceId = (EditTextPreference) findPreference("deviceId");
        //deviceId.setOnPreferenceChangeListener(this);
        //EditTextPreference clientId = (EditTextPreference) findPreference("clientId");
        //clientId.setOnPreferenceChangeListener(this);


        shared = PreferenceManager.getDefaultSharedPreferences(this);

        ActionBar currBar = getActionBar();
        currBar.setDisplayHomeAsUpEnabled(true);

        updatePrefFields();
    }

    @Override

    protected void onResume(){
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updatePrefFields();
    }

    private void updatePrefFields() {
        EditTextPreference deviceId = (EditTextPreference) findPreference("deviceId");
        deviceId.setSummary(shared.getString("deviceId", ""));
        EditTextPreference clientId = (EditTextPreference) findPreference("clientId");
        clientId.setSummary(shared.getString("clientId", ""));
        EditTextPreference resendFreq = (EditTextPreference) findPreference("resendFreq");
        resendFreq.setSummary(getResources().getString(R.string.settings_resendFreq_desc) + " " + shared.getString("resendFreq", ""));
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        CheckBoxPreference enableMenu = (CheckBoxPreference) findPreference("enableMenu");

        if (enableMenu.isChecked()) {
            SystemMenubar.show();
        } else {
            SystemMenubar.hide();
        }
        return true;
    }

    public static boolean isMenuEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("enableMenu", false);
    }

    public static String getDeviceId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("deviceId", "");
    }

    public static String getClientId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("clientId", "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}

