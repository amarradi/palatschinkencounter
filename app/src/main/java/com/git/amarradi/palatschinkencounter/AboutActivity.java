package com.git.amarradi.palatschinkencounter;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView title = findViewById(R.id.tvAppName);
        TextView whatsdo = findViewById(R.id.tvwhatsdo);
        TextView havefun = findViewById(R.id.tvhavefun);
        TextView oss = findViewById(R.id.itsos);
        TextView appVersion = findViewById(R.id.tvVersion);
        Typeface typeface_regular = getResources().getFont(R.font.opensans_regular);
        Typeface typeface_bold = getResources().getFont(R.font.opensans_extrabold);
        appVersion.setTypeface(typeface_regular);
        title.setTypeface(typeface_regular);
        whatsdo.setTypeface(typeface_regular);
        havefun.setTypeface(typeface_bold);
        oss.setTypeface(typeface_regular);
        TextView resethow = findViewById(R.id.resethowto);
        resethow.setTypeface(typeface_regular);
        //Resources resources = getResources();
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String version = String.format(getResources().getString(R.string.version), BuildConfig.VERSION_NAME);
        appVersion.setText(version);
    }

}
