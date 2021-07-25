package com.git.amarradi.palatschinkencounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

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

}