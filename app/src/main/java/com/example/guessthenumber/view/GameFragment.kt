package com.example.guessthenumber.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.guessthenumber.R
import com.example.guessthenumber.databinding.FragmentGameBinding
import com.example.guessthenumber.databinding.FragmentStartGameBinding
import com.example.guessthenumber.model.GameStatus
import com.example.guessthenumber.viewmodel.GameActivityViewModel
import com.google.android.material.textfield.TextInputLayout

class GameFragment : Fragment() {
    lateinit var gameActivityViewModel: GameActivityViewModel
    lateinit var textInputNumber: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false
        )
        binding.gameFragment = this
        gameActivityViewModel = ViewModelProvider(this).get(GameActivityViewModel::class.java)

        val tv = binding.testTextView
        val attempt = binding.attemptsTextView
        val button = binding.button
        textInputNumber = binding.textInputNumber

        gameActivityViewModel.attemptsLeft.observe(viewLifecycleOwner, { attemptsLeft ->
            attempt.text = "Спроби " + attemptsLeft.toString()
        })
        attempt.text = gameActivityViewModel.attemptsLeft.toString()

        gameActivityViewModel.generateRandomNumber()
        gameActivityViewModel.number.observe(viewLifecycleOwner, Observer { number ->
            tv.text = number.toString()
        })
        tv.text = gameActivityViewModel.number.toString()


        binding.button.setOnClickListener { view: View ->
            var inputNumber = 0
            if (validateEmail()) {
                inputNumber = textInputNumber.editText?.getText().toString().toInt()
                gameActivityViewModel.makeGuess(inputNumber)
            }
            if (gameActivityViewModel.gameStatus.value == GameStatus.WON) {

                Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_wonFragment)
                //val intent = Intent(this, WonActivity::class.java)
                //startActivity(intent)
            }
            if (gameActivityViewModel.gameStatus.value == GameStatus.LOST) {
                Navigation.findNavController(view).navigate(R.id.action_gameFragment_to_lostFragment)
                //val intent = Intent(this, LostActivity::class.java)
                //startActivity(intent)
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
        return binding.root
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