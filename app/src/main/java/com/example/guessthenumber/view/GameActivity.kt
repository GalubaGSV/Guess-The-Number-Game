package com.example.guessthenumber.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.guessthenumber.R
import com.example.guessthenumber.model.GameStatus
import com.example.guessthenumber.viewmodel.GameActivityViewModel
import com.google.android.material.textfield.TextInputLayout

class GameActivity : AppCompatActivity() {
    lateinit var gameActivityViewModel: GameActivityViewModel
    lateinit var textInputNumber: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameActivityViewModel = ViewModelProvider(this).get(GameActivityViewModel::class.java)
        val tv = findViewById<TextView>(R.id.testTextView)
        val attempt = findViewById<TextView>(R.id.attemptsTextView)
        val button = findViewById<Button>(R.id.button)
        textInputNumber = findViewById<TextInputLayout>(R.id.textInputNumber)

        gameActivityViewModel.attemptsLeft.observe(this, { attemptsLeft ->
            attempt.text = "Спроби " + attemptsLeft.toString()
        })
        attempt.text = gameActivityViewModel.attemptsLeft.toString()

        gameActivityViewModel.generateRandomNumber()
        gameActivityViewModel.number.observe(this, Observer { number ->
            tv.text = number.toString()
        })
        tv.text = gameActivityViewModel.number.toString()

        button.setOnClickListener{
            var inputNumber = 0
            if (validateEmail()) {
                inputNumber = textInputNumber.editText?.getText().toString().toInt()
                gameActivityViewModel.makeGuess(inputNumber)
            }
            if (gameActivityViewModel.gameStatus.value == GameStatus.WON) {
                val intent = Intent(this, WonActivity::class.java)
                startActivity(intent)
            }
            if (gameActivityViewModel.gameStatus.value == GameStatus.LOST) {
                val intent = Intent(this, LostActivity::class.java)
                startActivity(intent)
            }
            val currentNumber = gameActivityViewModel.number.value
            if (currentNumber != null) {
                if (inputNumber < currentNumber && inputNumber > 0) {
                    attempt.text = attempt.text.toString() + " Занадто мале число"
                    textInputNumber.editText?.setText("")
                } else if (inputNumber > currentNumber) {
                    attempt.text = attempt.text.toString() + " Занадто велике число"
                    textInputNumber.editText?.setText("")
                }
            }
        }
    }

    private fun validateEmail(): Boolean {
        val stringNumber = textInputNumber.getEditText()?.getText().toString()
        if (stringNumber == "") {
            textInputNumber.setError("Please input number")
            return false
        }
        val numberInput: Int = stringNumber.toInt()
        if (numberInput < 1 || numberInput > 100) {
            textInputNumber.setError("Please input correct number")
            return false
        }
        textInputNumber.setError("")
        return true
    }
}
