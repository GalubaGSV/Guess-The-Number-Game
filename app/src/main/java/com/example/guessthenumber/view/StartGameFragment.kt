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
import com.example.guessthenumber.databinding.FragmentStartGameBinding

class StartGameFragment : Fragment() {
    companion object {
        private const val TITLE = "Меню"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentStartGameBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_start_game, container, false
        )

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = TITLE

        binding.letsPlayButton.setOnClickListener {  view: View ->
            Navigation.findNavController(view).navigate(R.id.action_startGameFragment_to_gameFragment)
        }

        binding.finishButton.setOnClickListener {  view: View ->
            requireActivity().finish()
        }

        return binding.root
    }
}