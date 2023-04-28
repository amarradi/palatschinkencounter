package com.git.amarradi.palatschinkencounter;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Objects.requireNonNull;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.splashscreen.SplashScreen;
import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, WipeDataDialog.WipeDialogListener {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String COUNTER = "text";
    public static final String NIGHT_MODE = "night_mode";
    private static final String TAG = "PERMISSION_TAG";
    private static final int REQUEST_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private int counter = 0;
    private TextView textView;
    private boolean nightMode;
    private boolean safedNightMode;

    @SuppressLint("DefaultLocale")

    ImageView imageViewShare;

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


        final View content = findViewById(android.R.id.content);


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

    private void requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            try {
                Log.d(TAG, "requestPermission: try");

                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                storageActivityResultLauncher.launch(intent);
            }
            catch (Exception e){
                Log.e(TAG, "requestPermission: catch", e);
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                storageActivityResultLauncher.launch(intent);
            }
        }
        else {
            //Android is below 11(R)
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE
            );
        }
    }

    private final ActivityResultLauncher<Intent> storageActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d(TAG, "onActivityResult: ");
                    //here we will handle the result of our intent
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        //Android is 11(R) or above
                        if (Environment.isExternalStorageManager()){
                            //Manage External Storage Permission is granted
                            Log.d(TAG, "onActivityResult: Manage External Storage Permission is granted");
                            //createFolder();
                        }
                        else{
                            //Manage External Storage Permission is denied
                            Log.d(TAG, "onActivityResult: Manage External Storage Permission is denied");
                            Toast.makeText(MainActivity.this, "Manage External Storage Permission is denied", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
    );

    public boolean checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            //Android is 11(R) or above
            return Environment.isExternalStorageManager();
        }
        else{
            //Android is below 11(R)
            int write = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

            return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED;
        }
    }

    /*Handle permission request results*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0){
                //check each permission if granted or not
                boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean read = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (write && read){
                    //External Storage permissions granted
                    Log.d(TAG, "onRequestPermissionsResult: External Storage permissions granted");
                    //createFolder();
                }
                else{
                    //External Storage permission denied
                    Log.d(TAG, "onRequestPermissionsResult: External Storage permission denied");
                    Toast.makeText(this, "External Storage permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
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
        View btnView = findViewById(R.id.counter_text_button);
        btnView.setVisibility(View.GONE);
        if (checkPermission()) {
            Log.d(TAG, "onClick: Permissions already granted...");
            File file = new File(Environment.getExternalStorageDirectory() + "/palatschinkencounter/saved_images/");
            boolean folderCreated = file.mkdirs();
            View view1 = getWindow().getDecorView().getRootView();
            view1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
            view1.setDrawingCacheEnabled(false);
            btnView.setVisibility(View.VISIBLE);
            String filePath = Environment.getExternalStorageDirectory()+"/palatschinkencounter/"+"/"+System.currentTimeMillis()+".png";
            File fileScreenshot = new File(filePath);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(fileScreenshot);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Uri contentUri = FileProvider.getUriForFile(this,"com.git.amarradi.palatschinkencounter",fileScreenshot);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/png");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, String.format(resources.getString(R.string.share_with)));
                shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));
                shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                shareIntent.putExtra(Intent.EXTRA_TEXT, String.format(resources.getString(R.string.share_counter), counter));
                shareIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, String.format(resources.getString(R.string.share_with))));
            //    boolean deleted = fileScreenshot.delete();

            } catch (Exception e) {
                Log.d("TAG", "shareImage: "+e.getLocalizedMessage());
            }
        } else {
            Log.d(TAG, "onClick: Permissions was not granted, request...");
            requestPermission();
        }
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