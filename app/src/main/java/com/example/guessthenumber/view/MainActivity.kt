package com.example.guessthenumber.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.guessthenumber.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tv = findViewById<TextView>(R.id.testTextView)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener{
            val intent = Intent(this, GameActivity::class.java)
            // Запускаємо SecondActivity
            startActivity(intent)

        }
    }
}