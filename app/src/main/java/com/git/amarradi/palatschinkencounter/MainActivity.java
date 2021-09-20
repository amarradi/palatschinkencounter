package com.git.amarradi.palatschinkencounter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements ExampleDialog.ExampleDialogListener {

    private int counter = 0;
    private TextView textView;
    private Button counterTextButton;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String COUNTER = "text";

    private int savedCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterTextButton = (Button) findViewById(R.id.counter_text_button);
        textView = (TextView) findViewById(R.id.textview);

        counterTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                textView.setText(Integer.toString(counter));
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
        MenuItem clean = menu.findItem(R.id.item_clean);
        clean.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                openDialog();
                return false;
            }
        });
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        switch (item.getItemId()) {
//            case R.id.item_clean:
//                openDialog();
//
//                Toast.makeText(this, "reset counter", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    return true
//    }

    private void openDialog() {
        ExampleDialog dialog = new ExampleDialog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void save_data() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COUNTER,counter);
        editor.apply();
    }

    public void load_data() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        savedCounter = sharedPreferences.getInt(COUNTER,0);
        counter = savedCounter;
    }

    public void updateViews() {
        textView.setText(Integer.toString(savedCounter));
    }

    public void reset_counter() {
        counter = 0;
        save_data();
        updateViews();
    }

    @Override
    public void onYesClicked() {
        reset_counter();
    }
}