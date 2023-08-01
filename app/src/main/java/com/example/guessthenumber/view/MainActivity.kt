package com.example.guessthenumber.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.guessthenumber.R
import com.example.guessthenumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
//        val tv = findViewById<TextView>(R.id.testTextView)
//        val button = findViewById<Button>(R.id.button)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )

//        button.setOnClickListener{
//            val intent = Intent(this, GameActivity::class.java)
//            // Запускаємо SecondActivity
//            startActivity(intent)
//
//        }
    }
}