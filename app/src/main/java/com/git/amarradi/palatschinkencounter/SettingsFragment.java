package com.git.amarradi.palatschinkencounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import java.util.Objects;

/**
 * Created by Parzival on 18-03-2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();

        Preference feedback_preference = findPreference("feedback_preference");
        assert feedback_preference != null;
        feedback_preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"info@palatschinkencounter.de"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                startActivity(intent);
                return false;
            }
        });

        Preference version_preference = getPreferenceManager().findPreference("version_preference");

        assert version_preference != null;
        version_preference.setTitle("Palatschinkencounter " + BuildConfig.VERSION_NAME);
        int count = prefScreen.getPreferenceCount();

        // Go through all of the preferences, and set up their preference summary.
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);

            // You don't need to set up preference summaries for checkbox preferences because
            // they are already set up in xml using summaryOff and summary On
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences != null ? sharedPreferences.getString(p.getKey(), "") : null;

                setPreferenceSummary(p, value);
            }
        }

        Preference recipe_preference = getPreferenceManager().findPreference("recipe");
        Objects.requireNonNull(recipe_preference).setOnPreferenceClickListener(preference -> {
            Intent recipe_intent = new Intent(getActivity(), RecipeActivity.class);
            startActivity(recipe_intent);
            return false;
        });
        Preference about_preference = getPreferenceManager().findPreference("about");
        Objects.requireNonNull(about_preference).setOnPreferenceClickListener(preference -> {
            Intent about_intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(about_intent);
            return false;
        });

        Preference changelog = getPreferenceManager().findPreference("version_preference");
        Objects.requireNonNull(changelog).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(@NonNull Preference preference) {
                Intent change_intent = new Intent(getActivity(), ChangelogActivity.class);
                startActivity(change_intent);
                return false;
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Figure out which preference was changed
        Preference preference = findPreference(key);
        if (null != preference) {
            // Updates the summary for the preference
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
    }

    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            // For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else if (preference instanceof EditTextPreference) {
            // For EditTextPreferences, set the summary to the value's simple string representation.
            preference.setSummary(value);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getPreferenceScreen().getSharedPreferences())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Objects.requireNonNull(getPreferenceScreen().getSharedPreferences())
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}