package com.git.amarradi.palatschinkencounter;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChangelogActivity extends AppCompatActivity {
    TextView tv_changelog, et_changelog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelog);

        tv_changelog = findViewById(R.id.tvChangeloghead);
        et_changelog = findViewById(R.id.etChangelog);

        readChangelog();

    }

    private void readChangelog() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.changelog);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            String s;

            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s).append("\n");
            }
            et_changelog.setText(stringBuffer.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}