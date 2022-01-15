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

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_clean:
                MainActivity main = new MainActivity();
                main.openDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

     */
}
