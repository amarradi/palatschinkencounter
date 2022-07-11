package com.git.amarradi.palatschinkencounter;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, WipeDataDialog.WipeDialogListener {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String COUNTER = "text";
    public static final String NIGHT_MODE = "night_mode";


    private int counter = 0;
    private TextView textView;
    private boolean nightMode;
    private boolean safedNightMode;

    @SuppressLint("DefaultLocale")

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setLogo(R.mipmap.logo_psc_round);
        getSupportActionBar().setDisplayUseLogoEnabled(false);

        Typeface typeface = getResources().getFont(R.font.opensans_regular);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button counterTextButton = findViewById(R.id.counter_text_button);
        counterTextButton.setTypeface(getResources().getFont(R.font.opensans_bold));

        textView = findViewById(R.id.textview);
        TextView textView_start = findViewById(R.id.tv_startpage);
        textView.setTypeface(typeface);
        textView_start.setTypeface(typeface);

        setupSharedPreferences();

        textView.setOnLongClickListener(v -> {
            deleteDialog();
            return false;
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String themes = sharedPreferences.getString(NIGHT_MODE, "");

        changeTheme(themes);

        load_data();
        updateViews();

        counterTextButton.setOnClickListener(v -> {
            counter++;
            textView.setText(format("%d", counter));
            save_data();
        });
        load_data();
        updateViews();
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_setting) {
            Intent intentSetting = new Intent(this, SettingActivity.class);
            startActivity(intentSetting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if (key.equals("theme")) {
            loadThemeFromPreference(sharedPreferences);
        }
    }

    private void loadThemeFromPreference(SharedPreferences sharedPreferences) {
        changeTheme(sharedPreferences.getString(getString(R.string.theme_key),getString(R.string.lightmode_preference_option_value)));
    }

    private void changeTheme(String theme_value) {

        switch (theme_value) {
            case "lightmode": {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(NIGHT_MODE, theme_value);
                editor.apply();
                break;
            }
            case "darkmode": {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(NIGHT_MODE, theme_value);
                editor.apply();
                break;
            }
            case "system default": {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(NIGHT_MODE, theme_value);
                editor.apply();
                break;
            }
        }
    }

    public void deleteDialog() {
        WipeDataDialog dialog = new WipeDataDialog();
        dialog.show(getSupportFragmentManager(), "delete dialog");
    }

    public void save_data() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COUNTER, counter);
        editor.apply();
    }

    public void load_data() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        counter = sharedPreferences.getInt(COUNTER, 0);
    }

    @SuppressLint("DefaultLocale")
    public void updateViews() {
        textView.setText(format("%d", counter));
    }

    public void reset_counter() {
        counter = 0;
        updateViews();
        save_data();
        Toast.makeText(this, this.getString(R.string.reset), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onYesClicked() {
        reset_counter();
    }

}