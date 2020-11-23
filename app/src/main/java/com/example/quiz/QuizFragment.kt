package com.example.quiz

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            Toast.makeText(requireContext(), "OK", Toast.LENGTH_SHORT).show()
            viewModel.nextQuestion()
        }
        button2.setOnClickListener {
            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            viewModel.nextQuestion()
        }
        button3.setOnClickListener {
            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            viewModel.nextQuestion()
        }
        button4.setOnClickListener {
            Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
            viewModel.nextQuestion()
        }
        viewModel.questionNumber.observe(viewLifecycleOwner, {
            questionNum = it
        })
        viewModel.quiz.observe(viewLifecycleOwner, {
            quiz = it
        })
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is QuizState.LoddingState -> {
                    progressBar.visibility = View.VISIBLE
                    button1.visibility = View.INVISIBLE
                    button2.visibility = View.INVISIBLE
                    button3.visibility = View.INVISIBLE
                    button4.visibility = View.INVISIBLE
                    category.visibility = View.INVISIBLE
                    categoryText.visibility = View.INVISIBLE
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
                    category.visibility = View.VISIBLE
                    categoryText.visibility = View.VISIBLE
                    questionText.visibility = View.VISIBLE

                }
                is QuizState.ButtonClickState -> {
                    updateView()
                }
                is QuizState.ErrorState -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun updateView() {
        categoryText.text = quiz!!.results[questionNum!!].category
        questionText.text = quiz!!.results[questionNum!!].question
        button1.text = quiz!!.results[questionNum!!].correct_answer
        button2.text = quiz!!.results[questionNum!!].incorrect_answers[0]
        button3.text = quiz!!.results[questionNum!!].incorrect_answers[1]
        button4.text = quiz!!.results[questionNum!!].incorrect_answers[2]
    }

}