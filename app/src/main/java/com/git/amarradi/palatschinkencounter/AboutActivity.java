package com.git.amarradi.palatschinkencounter;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView appVersion = findViewById(R.id.tvVersion);
        Resources resources = getResources();
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String version = String.format(getResources().getString(R.string.version), BuildConfig.VERSION_NAME);
        appVersion.setText(version);
    }

}
