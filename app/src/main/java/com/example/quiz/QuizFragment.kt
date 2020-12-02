package com.example.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import kotlinx.android.synthetic.main.quiz_fragment.*

class QuizFragment : Fragment() {

    private lateinit var viewModel: QuizViewModel
    private var questionNum: Int? = null
    private var quiz: Quiz? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quiz_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        button1.setOnClickListener {
            viewModel.correctAnswer()
            Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show()
            viewModel.checkQuestion()
        }

        button2.setOnClickListener {
            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            viewModel.checkQuestion()
        }

        button3.setOnClickListener {
            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            viewModel.checkQuestion()
        }

        button4.setOnClickListener {
            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            viewModel.checkQuestion()
        }

        viewModel.questionNumber.observe(viewLifecycleOwner, {
            Log.d("MYTAG", it.toString())
            questionNum = it
        })
        viewModel.quiz.observe(viewLifecycleOwner, {
            quiz = it
        })
        viewModel.correctAnswers.observe(viewLifecycleOwner, {
            scoreText.text = it.toString()
        })
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is QuizState.LoddingState -> {
                    progressBar.visibility = View.VISIBLE
                    button1.visibility = View.INVISIBLE
                    button2.visibility = View.INVISIBLE
                    button3.visibility = View.INVISIBLE
                    button4.visibility = View.INVISIBLE
                    score.visibility = View.INVISIBLE
                    scoreText.visibility = View.INVISIBLE
                    questionText.visibility = View.INVISIBLE
                    viewModel.loadQuiz()
                }
                is QuizState.StartQuizState -> {
                    updateView()
                    progressBar.visibility = View.INVISIBLE
                    button1.visibility = View.VISIBLE
                    button2.visibility = View.VISIBLE
                    button3.visibility = View.VISIBLE
                    button4.visibility = View.VISIBLE
                    score.visibility = View.VISIBLE
                    scoreText.visibility = View.VISIBLE
                    questionText.visibility = View.VISIBLE

                }
                is QuizState.ButtonClickState -> {
                    updateView()
                }
                is QuizState.EndQuizState -> {
                    val bundle = bundleOf("correctAnswers" to viewModel.correctAnswers.value!!)
                    findNavController(this).navigate(
                        R.id.action_quizFragment_to_endGameFragment,
                        bundle
                    )
                }
                is QuizState.ErrorState -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun updateView() {
        questionText.text = quiz!!.results[questionNum!!].question
        button1.text = quiz!!.results[questionNum!!].correct_answer
        button2.text = quiz!!.results[questionNum!!].incorrect_answers[0]
        button3.text = quiz!!.results[questionNum!!].incorrect_answers[1]
        button4.text = quiz!!.results[questionNum!!].incorrect_answers[2]
    }

}