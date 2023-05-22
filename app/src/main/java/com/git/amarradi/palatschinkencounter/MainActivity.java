package com.git.amarradi.palatschinkencounter;


import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.core.splashscreen.SplashScreen;
import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, WipeDataDialog.WipeDialogListener {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String COUNTER = "text";
    public static final String NIGHT_MODE = "night_mode";
    public static final String SCREENSHOT_PNG = "screenshot.png";
    @SuppressLint("DefaultLocale")

    private int counter = 0;
    private TextView textView;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Palatschinkencounter);
        // Handle the splash screen transition.
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setLogo(R.mipmap.logo_psc_round);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        Typeface typeface = getResources().getFont(R.font.opensans_regular);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton counterTextButton = findViewById(R.id.counter_text_button);


        textView = findViewById(R.id.tv_counterstate);
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
            textView.setText(String.format("%d", counter));
            save_data();
        });
        load_data();
        updateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_setting: {
                Intent intentSetting = new Intent(this, SettingActivity.class);
                startActivity(intentSetting);
                return true;
            }
            case R.id.item_share: {
                shareImage();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint({"StringFormatMatches", "ResourceType"})
    private void shareImage() {
        Resources resources = getResources();
        Toast.makeText(this, resources.getString(R.string.ready_to_share), Toast.LENGTH_SHORT).show();
        ImageButton imageButton = findViewById(R.id.counter_text_button);
        imageButton.setVisibility(View.INVISIBLE);
        View view1 = getWindow().getDecorView().getRootView();
        view1.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
        view1.setDrawingCacheEnabled(false);
        imageButton.setVisibility(View.VISIBLE);

        try {
            File cacheFile = new File(getApplicationContext().getCacheDir(), SCREENSHOT_PNG);
            OutputStream outputStream = new FileOutputStream(cacheFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception ignored) {
        }

        File imagePath = new File(getCacheDir().toURI());
        File newFile = new File(imagePath, SCREENSHOT_PNG);
        Uri contentUri = FileProvider.getUriForFile(MainActivity.this, getApplicationContext().getPackageName(), newFile);


        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(resources.getString(R.string.share_counter), counter));
        startActivity(Intent.createChooser(shareIntent, String.format(resources.getString(R.string.share_with))));
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("theme")) {
            loadThemeFromPreference(sharedPreferences);
        }
    }

    private void loadThemeFromPreference(SharedPreferences sharedPreferences) {
        changeTheme(sharedPreferences.getString(getString(R.string.theme_key), getString(R.string.lightmode_preference_option_value)));
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