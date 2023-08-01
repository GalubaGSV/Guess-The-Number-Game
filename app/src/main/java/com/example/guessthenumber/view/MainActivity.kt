package com.example.guessthenumber.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.guessthenumber.R
import com.example.guessthenumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        // Don't call the parent implementation to prevent orientation change
        // super.onConfigurationChanged(newConfig)
    }
}