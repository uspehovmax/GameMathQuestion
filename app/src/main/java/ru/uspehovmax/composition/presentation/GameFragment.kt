package ru.uspehovmax.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.uspehovmax.composition.R
import ru.uspehovmax.composition.data.GameRepositoryImpl
import ru.uspehovmax.composition.databinding.FragmentGameBinding
import ru.uspehovmax.composition.domain.entity.GameResult
import ru.uspehovmax.composition.domain.entity.Level
import ru.uspehovmax.composition.domain.usecase.GetGamesSettingsUseCase

class GameFragment : Fragment() {

    private lateinit var level: Level
    private val viewModelFactory by lazy {
        GameViewModelFactory(requireActivity().application, level)
    }
    //    private lateinit var viewModel: GameViewModel
    // или ленивая иницциализация. Не пишем в onViewCreated
    // ! чтобы передать параметры в конструктор viewModel - нужно создать viewModelFactory
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            with(binding) {
                add(tvOption1)
                add(tvOption2)
                add(tvOption3)
                add(tvOption4)
                add(tvOption5)
                add(tvOption6)
            }
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    companion object {
        const val NAME = "GameFragment"
        private const val KEY_LEVEL = "level"
        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    // сериализация - перевод объекта в байты для передачи
//                    putSerializable(KEY_LEVEL, level)
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }

    private fun parseArgs() {
//        level = requireArguments().getSerializable(KEY_LEVEL) as Level
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
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
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // запуск обсервера ViewModel и слушателей нажатия варианта ответа
        observeViewModel()
        setClickListenersToOptions()
        // запуск startGame из viewModel
//        viewModel.startGame(level)    // убрали
    }

    private fun setClickListenersToOptions() {
        // слушание списка кнопок - вариантов ответа
        for (tvOption in tvOptions) {
            tvOption.setOnClickListener {
                // запуск выбор ответа из viewModel
                viewModel.chooseAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    private fun observeViewModel() {
        with(binding) {
            // LiveData question
            viewModel.question.observe(viewLifecycleOwner) {
                tvSum.text = it.sum.toString()
                tvLeftNumber.text = it.visibleNumber.toString()
                for (i in 0 until tvOptions.size) {
                    tvOptions[i].text = it.options[i].toString()
                }
            }
            // LiveData percentOfRightAnswers
            viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
                progressBar.setProgress(it)
            }
            // LiveData enoughCountOfRightAnswers
            viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
                tvAnswersProgress.setTextColor(getColorByState(it))
            }
            // LiveData enoughCountOfRightAnswers
            viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
                val color = getColorByState(it)
                // установвка цвета у progressBar
                progressBar.progressTintList = ColorStateList.valueOf(color)
            }
            // LiveData formattedTime
            viewModel.formattedTime.observe(viewLifecycleOwner) {
                tvTimer.text = it
            }
            // LiveData minPercent
            viewModel.minPercent.observe(viewLifecycleOwner) {
                progressBar.secondaryProgress = it
            }
            // LiveData gameResult
            viewModel.gameResult.observe(viewLifecycleOwner) {
                launchGameFinishedFragment(it)
            }
            // LiveData gameResult
            viewModel.progressAnswers.observe(viewLifecycleOwner) {
                tvAnswersProgress.text = it
            }

        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if(goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_dark
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

}