package com.example.guessthenumber.view

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
import com.example.guessthenumber.databinding.FragmentLostBinding
import com.example.guessthenumber.viewmodel.GameActivityViewModel

class LostFragment : Fragment() {
    companion object {
        private const val TITLE = "Результати гри"
        private const val RESULT = "Ви програли. Спробуйте ще раз"
        private const val ATTEMPTS = "Ви програли. Спробуйте ще раз"
    }

    private lateinit var gameActivityViewModel: GameActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = LostFragment.TITLE

        val binding = DataBindingUtil.inflate<FragmentLostBinding>(
            inflater, R.layout.fragment_lost, container, false
        )

        val args = arguments
        var attempts = args?.getInt("attempts", 0)

        if (attempts != null) {
            attempts = 5 - attempts
            binding.attemptsTextView.text = "Кількість використаних спроб: $attempts"
        }

        binding.letsPlayButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(R.id.action_lostFragment_to_startGameFragment)
        }

        //binding.helloTextView.text = RESULT + "\n" +
        return binding.root
    }
}
