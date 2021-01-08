package com.example.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.main_fragment.*
import com.google.android.gms.ads.initialization.InitializationStatus




class MainFragment : Fragment() {


    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        startQuizButton.setOnClickListener {
            viewModel.startQuiz()
        }
        settingsButton.setOnClickListener {
            viewModel.settings()
        }
        viewModel.state.observe(viewLifecycleOwner, {

        })
        viewModel.state.observe(viewLifecycleOwner, {
            when (it) {
                is MainState.StartGameState -> {
                    findNavController(this).navigate(R.id.action_mainFragment_to_quizFragment)
                }

                is MainState.ErrorState -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is MainState.SettingState -> {
                    findNavController(this).navigate(R.id.action_mainFragment_to_settingsFragment)
                }

                is MainState.DefaultState -> {
                    MobileAds.initialize(requireContext()) {}
                    val adRequest = AdRequest.Builder().build()
                    adView.loadAd(adRequest)
                }
            }
        })

    }

}