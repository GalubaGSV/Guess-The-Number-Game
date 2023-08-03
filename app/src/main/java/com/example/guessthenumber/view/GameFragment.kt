package com.example.guessthenumber.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.guessthenumber.R
import com.example.guessthenumber.databinding.FragmentGameBinding
import com.example.guessthenumber.model.GameStatus
import com.example.guessthenumber.viewmodel.GameActivityViewModel
import com.google.android.material.textfield.TextInputLayout

class GameFragment : Fragment() {
    companion object {
        private const val ATTEMPTS = "Спроби "
        private const val NO_INTERNET_MESSAGE = "Немає інтернет з'єднання!!! Спробуйте пізніше"
        private const val TOO_BIG_NUMBER_MESSAGE = "Занадто велике число"
        private const val TOO_SMALL_NUMBER_MESSAGE = "Занадто мале число"
        private const val INPUT_NUMBER_MESSAGE = "Будь ласка введіть число"
        private const val INPUT_CORRECT_NUMBER_MESSAGE = "Число має бути від 1 до 100"
        private const val TITLE = "Вгадай число"
    }

    lateinit var gameActivityViewModel: GameActivityViewModel
    lateinit var textInputNumber: TextInputLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false
        )
        binding.gameFragment = this
        gameActivityViewModel = ViewModelProvider(this).get(GameActivityViewModel::class.java)

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = GameFragment.TITLE

        val tv = binding.testTextView
        val attempt = binding.attemptsTextView
        textInputNumber = binding.textInputNumber
        var attempts = 0

        gameActivityViewModel.attemptsLeft.observe(viewLifecycleOwner) { attemptsLeft ->
            attempt.text = ATTEMPTS + attemptsLeft.toString()
            attempts = attemptsLeft
        }

        gameActivityViewModel.generateRandomNumber(requireContext())
        // Show random number, it need only for testing
        gameActivityViewModel.number.observe(viewLifecycleOwner, Observer { number ->
            tv.text = number.toString()
        })

        binding.finishGameButton.setOnClickListener { view: View ->
            val action = GameFragmentDirections.actionGameFragmentToLostFragment(attempts)
            Navigation.findNavController(view).navigate(action)
        }

        binding.button.setOnClickListener { view: View ->
            binding.finishGameButton.visibility = View.VISIBLE
            var inputNumber = 0
            if (validateEmail()) {
                inputNumber = textInputNumber.editText?.getText().toString().toInt()
                gameActivityViewModel.makeGuess(inputNumber)
            }
            if (gameActivityViewModel.gameStatus.value == GameStatus.WON) {
                val action = GameFragmentDirections.actionGameFragmentToWonFragment(attempts)
                Navigation.findNavController(view).navigate(action)
            }
            if (gameActivityViewModel.gameStatus.value == GameStatus.LOST) {
                val action = GameFragmentDirections.actionGameFragmentToLostFragment(attempts)
                Navigation.findNavController(view).navigate(action)
            }

            val currentNumber = gameActivityViewModel.number.value
            if (currentNumber == -1) {
                attempt.text = NO_INTERNET_MESSAGE
            } else {
                if (currentNumber != null) {
                    if (inputNumber < currentNumber && inputNumber > 0) {
                        attempt.text = attempt.text.toString() + "\n" + TOO_SMALL_NUMBER_MESSAGE
                        textInputNumber.editText?.setText("")
                    } else if (inputNumber > currentNumber) {
                        attempt.text = attempt.text.toString() + "\n" + TOO_BIG_NUMBER_MESSAGE
                        textInputNumber.editText?.setText("")
                    }
                }
            }
        }
        return binding.root
    }

    private fun validateEmail(): Boolean {
        val stringNumber = textInputNumber.getEditText()?.getText().toString()
        if (stringNumber == "") {
            textInputNumber.setError(INPUT_NUMBER_MESSAGE)
            return false
        }
        val numberInput: Int = stringNumber.toInt()
        if (numberInput <= 1 || numberInput >= 100) {
            textInputNumber.setError(INPUT_CORRECT_NUMBER_MESSAGE)
            return false
        }
        textInputNumber.setError("")
        return true
    }


}
