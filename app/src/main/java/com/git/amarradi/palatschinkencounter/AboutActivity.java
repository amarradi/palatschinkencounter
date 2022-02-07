package com.git.amarradi.palatschinkencounter;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView title = findViewById(R.id.tvAppName);
        TextView whatsdo = findViewById(R.id.tvwhatsdo);
        TextView havefun = findViewById(R.id.tvhavefun);
        TextView oss = findViewById(R.id.itsos);
        TextView appVersion = findViewById(R.id.tvVersion);
        Typeface typeface = getResources().getFont(R.font.opensans_regular);
        appVersion.setTypeface(typeface);
        title.setTypeface(typeface);
        whatsdo.setTypeface(typeface);
        havefun.setTypeface(typeface);
        oss.setTypeface(typeface);
        //Resources resources = getResources();
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String version = String.format(getResources().getString(R.string.version), BuildConfig.VERSION_NAME);
        appVersion.setText(version);
    }

}
