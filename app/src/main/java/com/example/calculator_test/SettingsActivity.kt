package com.example.calculator_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //Adds a back button to the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    //Aforementioned actionbar functionality
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}