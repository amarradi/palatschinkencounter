package com.git.amarradi.palatschinkencounter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ChangelogActivity extends AppCompatActivity {
    TextView tv_changelog, et_changelog;
    ImageView birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelog);

        tv_changelog = findViewById(R.id.tvChangeloghead);
        et_changelog = findViewById(R.id.etChangelog);

        birthday = findViewById(R.id.birthday);

        readChangelog();

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