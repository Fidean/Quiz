package com.example.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
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

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            NavHostFragment.findNavController(this@EndGameFragment)
                .navigate(R.id.action_endGameFragment_to_mainFragment)
        }

        restartButton.setOnClickListener {
            viewModel.restart()
        }

        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is EndGameState.DefaultState -> {
                    viewModel.setCorrectAnswer(arguments?.getInt("correctAnswers"))
                    score.text = viewModel.getCorrectAnswer()
                }
                is EndGameState.RestartState -> {
                    NavHostFragment.findNavController(this)
                        .navigate(R.id.action_endGameFragment_to_quizFragment)
                }
                is EndGameState.ErrorState -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

}