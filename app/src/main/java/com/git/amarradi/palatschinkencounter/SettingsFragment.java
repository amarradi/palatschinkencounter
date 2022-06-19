package com.git.amarradi.palatschinkencounter;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {

        addPreferencesFromResource(R.xml.preferences);
        Preference feedback_preference = (Preference) findPreference("feedback_preference");

        feedback_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, "info@palatschinkencounter.de");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                startActivity(intent);
                return true;
            }
        });

        Preference checkbox = (Preference) findPreference("check_theme_preference");
        checkbox.setOnPreferenceChangeListener((preference, newValue) -> {
            Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();
            return true;
        });

    }

}