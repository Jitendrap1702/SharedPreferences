package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.example.sharedpreferences.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    // Current count
    private int mCount = 0;
    // Current background color
    private int mColor;
    // Key for current count
    private final String COUNT_KEY = "count";
    // Key for current color
    private final String COLOR_KEY = "color";
    // Member variable for sharedPreferences
    private SharedPreferences mSharedPref;
    // Name of shared Preferences file
    private String sharedPrefFile = "com.example.sharedpreferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        // Initialize views, color
        mColor = ContextCompat.getColor(this, R.color.default_background);

        // Shared Preferences
        mSharedPref = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        mCount = mSharedPref.getInt(COUNT_KEY,0);   // Getting the count value stored in sharedPreferences
        activityMainBinding.countTextview.setText(String.format("%s",mCount));  // Update the value on main textView
        mColor = mSharedPref.getInt(COLOR_KEY,mColor); // Getting the color value
        activityMainBinding.countTextview.setBackgroundColor(mColor);  // Update the background color of main textView
    }

    /**
     * Handles the onClick for the background color buttons. Gets background
     * color of the button that was clicked, and sets the TextView background
     * to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    public void changeBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        activityMainBinding.countTextview.setBackgroundColor(color);
        mColor = color;
    }

    /**
     * Handles the onClick for the Count button. Increments the value of the
     * mCount global and updates the TextView.
     *
     * @param view The view (Button) that was clicked.
     */
    public void countUp(View view) {
        mCount++;
        activityMainBinding.countTextview.setText(String.format("%s", mCount));
    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor prefEditor = mSharedPref.edit();
        prefEditor.putInt(COUNT_KEY,mCount);
        prefEditor.putInt(COLOR_KEY,mColor);
        prefEditor.apply();
    }

    /**
     * Handles the onClick for the Reset button. Resets the global count and
     * background variables to the defaults and resets the views to those
     * default values.
     *
     * @param view The view (Button) that was clicked.
     */
    public void reset(View view) {
        // Reset count
        mCount = 0;
        activityMainBinding.countTextview.setText(String.format("%s", mCount));

        // Reset color
        mColor = ContextCompat.getColor(this, R.color.default_background);
        activityMainBinding.countTextview.setBackgroundColor(mColor);

        // Clearing Preferences
        SharedPreferences.Editor prefEditorInReset = mSharedPref.edit();
        prefEditorInReset.clear();
        prefEditorInReset.apply();
    }

}