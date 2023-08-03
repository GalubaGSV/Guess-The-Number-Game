package com.example.guessthenumber.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.guessthenumber.R
import com.example.guessthenumber.databinding.FragmentGameBinding
import com.example.guessthenumber.databinding.FragmentWonBinding

class WonFragment : Fragment() {
    companion object {
        private const val TITLE = "Результати гри"
        private const val RESULT = "Ви програли. Спробуйте ще раз"
        private const val ATTEMPTS = "Ви програли. Спробуйте ще раз"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = WonFragment.TITLE
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentWonBinding>(
            inflater, R.layout.fragment_won, container, false
        )

        val args = arguments
        var attempts = args?.getInt("attempts", 0)

        if (attempts != null) {
            attempts = 5 - attempts
            binding.attemptsTextView.text = "Кількість використаних спроб: $attempts"
        }

        binding.letsPlayButton.setOnClickListener { view: View ->
            Navigation.findNavController(view).navigate(R.id.action_wonFragment_to_startGameFragment)
        }


        return binding.root
    }


}