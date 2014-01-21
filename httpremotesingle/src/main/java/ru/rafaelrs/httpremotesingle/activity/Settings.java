package ru.rafaelrs.httpremotesingle.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import ru.rafaelrs.httpremotesingle.R;
import ru.rafaelrs.httpremotesingle.system.SystemMenubar;

// Активити ввода и хранения настроек
public class Settings extends PreferenceActivity implements Preference.OnPreferenceClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        Preference enableMenu = findPreference("enableMenu");
        // Обработчик нажатия на опцию
        enableMenu.setOnPreferenceClickListener(this);

        shared = PreferenceManager.getDefaultSharedPreferences(this);

        // В заголовке активити добавляем стрелочку позволяющей вернутся назад (кнопки то мы заблокировали :))
        ActionBar currBar = getActionBar();
        currBar.setDisplayHomeAsUpEnabled(true);

        // Обновим подписи опций
        updatePrefFields();
    }

    @Override

    protected void onResume(){
        super.onResume();

        // Обработчик изменения опций
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Обновим подписи опций
        updatePrefFields();
    }

    private void updatePrefFields() {
        // Обновим подписи опций
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

        // В зависимости от выбранной опции - скрываем, показываем системную панель
        if (enableMenu.isChecked()) {
            SystemMenubar.show();
        } else {
            SystemMenubar.hide();
        }
        return true;
    }

    // Подгрузка меню для ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Обработчик выбора пункта меню в ActionBar
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

    // Блок статических методов для получения тех или иных опций
    public static boolean isMenuEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("enableMenu", false);
    }

    public static String getDeviceId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("deviceId", "");
    }

    public static String getClientId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("clientId", "");
    }

    public static String getPassword(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString("password", "");
    }

}

