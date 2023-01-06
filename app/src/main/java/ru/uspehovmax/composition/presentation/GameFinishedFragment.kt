package ru.uspehovmax.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.uspehovmax.composition.R
import ru.uspehovmax.composition.databinding.FragmentGameFinishedBinding
import ru.uspehovmax.composition.domain.entity.GameResult

class GameFinishedFragment: Fragment() {

    private val viewModel: ViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }

    private lateinit var gameResult: GameResult
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    companion object {
        const val KEY_GAME_RESULT = "game_result"

        // вместо newInstance используются safeargs для передачи данных
        fun newInstance(gameResult: GameResult): GameFinishedFragment{
            return GameFinishedFragment().apply{
                arguments = Bundle().apply {
                    // сериализация - перевод объекта в байты для передачи
//                    putSerializable(KEY_GAME_RESULT, gameResult)
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }

    private fun parseArgs() {
//        gameResult = requireArguments().getSerializable(GameFinishedFragment.KEY_GAME_RESULT) as GameResult
        // из аргументов через getParcelable получаем объект GameResult
         requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
             gameResult = it
         }
    }

    // запуск выбора фрагмента GameFragment
    private fun retryGame() {
//        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        // замена на navigate
        findNavController().popBackStack()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        bindViews()
    }

    private fun bindViews() {
        with(binding) {
            emojiResult.setImageResource(getSmileResId())
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                gameResult.gameSettings.minCountOofRightAnswers
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                gameResult.countOfRightAnswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                gameResult.gameSettings.minPercentOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightAnswer()
            )
        }
    }

    private fun getPercentOfRightAnswer() = with (gameResult) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun getSmileResId(): Int {
        return if (gameResult.winner) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    private fun setupClickListeners() {
        // на экране GameFinishedFragment слушатель нажатия кнопки "назад"
        // замена navigate
//        val callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                retryGame()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        // слушатель нажатия "Попробовать снова"
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/*
    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOofRightAnswers
        )
        _enoughCountOfRightAnswers.value =
            countOfRightAnswers >= gameSettings.minCountOofRightAnswers
        _enoughPercentOfRightAnswers.value = percent >= gameSettings.minPercentOfRightAnswers

    }

 */