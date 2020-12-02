package com.example.quiz

import android.content.Intent.getIntent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.end_game_fragment.*

class EndGameFragment : Fragment() {

    private lateinit var viewModel: EndGameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.end_game_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EndGameViewModel::class.java)
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is EndGameState.DefaultState -> {
                    viewModel.setCorrectAnswer(arguments?.getInt("correctAnswers"))
                    score.text = viewModel.correctAnswers.toString()
                }
                is EndGameState.ErrorState -> {
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

}