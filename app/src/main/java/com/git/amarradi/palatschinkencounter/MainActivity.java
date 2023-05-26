package com.git.amarradi.palatschinkencounter;


import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.FileProvider;
import androidx.core.splashscreen.SplashScreen;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

import de.cketti.library.changelog.ChangeLog;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, WipeDataDialog.WipeDialogListener {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String COUNTER = "text";
    public static final String NIGHT_MODE = "night_mode";
    public static final String SCREENSHOT_PNG = "screenshot.png";
    private static final int SCREEN_ORIENTATION_UNSPECIFIED = SCREEN_ORIENTATION_PORTRAIT;
    private CoordinatorLayout coordinatorLayout;
    FloatingActionButton floatingActionButton;
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
        setRequestedOrientation(SCREEN_ORIENTATION_UNSPECIFIED);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(v -> {
            shareImage();
        });



        ChangeLog cl = new ChangeLog(this);
        if (cl.isFirstRun()) {
            cl.getLogDialog().show();
        }


        ImageButton counterTextButton = findViewById(R.id.counter_text_button);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_setting) {
            Intent intentSetting = new Intent(this, SettingActivity.class);
            startActivity(intentSetting);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint({"StringFormatMatches", "ResourceType"})
    private void shareImage() {
        Resources resources = getResources();

        Snackbar snackbar = Snackbar.make(coordinatorLayout,
                resources.getString(R.string.ready_to_share),Snackbar.LENGTH_LONG);
        snackbar.show();

        ImageButton imageButton = findViewById(R.id.counter_text_button);
        imageButton.setVisibility(View.INVISIBLE);

        floatingActionButton.setVisibility(View.INVISIBLE);
        View view1 = getWindow().getDecorView().getRootView();
        Bitmap bitmap = getBitMapFromView(view1);
        imageButton.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
        try {
            File cacheFile = new File(getApplicationContext().getCacheDir(), SCREENSHOT_PNG);
            OutputStream outputStream = Files.newOutputStream(cacheFile.toPath());
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

    private Bitmap getBitMapFromView(View view) {
        Bitmap bitmap;
        bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable background = view.getBackground();
        background.draw(canvas);
        view.draw(canvas);
        return bitmap;
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
        Resources resources = getResources();
        int undo = counter;
        counter = 0;
        updateViews();
        save_data();
        Snackbar snackbar = Snackbar.make(coordinatorLayout,resources.getString(R.string.reset),Snackbar.LENGTH_LONG);
        snackbar.setAction(resources.getString(R.string.undo), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter = undo;
                updateViews();
            }
        });
        snackbar.show();
    }

    @Override
    public void onYesClicked() {
        reset_counter();
    }
}