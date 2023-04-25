package com.git.amarradi.palatschinkencounter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_Palatschinkencounter);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView title = findViewById(R.id.tvAppName);

        TextView havefun = findViewById(R.id.tvhavefun);
        TextView intention = findViewById(R.id.intention);
        TextView decision= findViewById(R.id.tv_decision);
        TextView oss = findViewById(R.id.itsos);
        oss.setMovementMethod(LinkMovementMethod.getInstance());
        TextView appVersion = findViewById(R.id.tvVersion);
        Typeface typeface_regular = getResources().getFont(R.font.opensans_regular);
        Typeface typeface_bold = getResources().getFont(R.font.opensans_extrabold);
        appVersion.setTypeface(typeface_regular);
        title.setTypeface(typeface_regular);

        havefun.setTypeface(typeface_bold);
        oss.setTypeface(typeface_regular);
        intention.setTypeface(typeface_regular);
        decision.setTypeface(typeface_regular);
        TextView resethow = findViewById(R.id.resethowto);
        resethow.setTypeface(typeface_regular);
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String version = String.format(getResources().getString(R.string.version), BuildConfig.VERSION_NAME);
        appVersion.setText(version);
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

}
