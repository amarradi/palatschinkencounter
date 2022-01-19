package com.git.amarradi.palatschinkencounter;

import static java.util.Objects.requireNonNull;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String COUNTER = "text";
    private int counter = 0;
    private TextView textView;
    private int savedCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setLogo(R.mipmap.logo_psc_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button counterTextButton = findViewById(R.id.counter_text_button);
        textView = findViewById(R.id.textview);

        load_data();
        updateViews();

        counterTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                textView.setText(String.format("%d", counter));
                save_data();
            }
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
            case R.id.item_clean:
                openDialog();
                return true;
            case R.id.item_recipe:
                Intent intentRecipe = new Intent(this, RecipeActivity.class);
                startActivity(intentRecipe);
                return true;
            case R.id.item_about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openDialog() {
        ExampleDialog dialog = new ExampleDialog();
        dialog.show(getSupportFragmentManager(), "open dialog");
    }

    public void save_data() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COUNTER, counter);
        editor.apply();
    }

    public void load_data() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        savedCounter = sharedPreferences.getInt(COUNTER, 0);
        counter = savedCounter;
    }

    public void updateViews() {
        textView.setText(String.format("%d", counter));
    }

    public void reset_counter() {
        counter = 0; //set counter to 0
        updateViews();
        save_data();
        Toast.makeText(this, this.getString(R.string.reset), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onYesClicked() {
        reset_counter();
    }
}