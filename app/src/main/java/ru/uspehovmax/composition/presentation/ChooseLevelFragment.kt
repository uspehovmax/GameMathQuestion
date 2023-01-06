package ru.uspehovmax.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.uspehovmax.composition.R
import ru.uspehovmax.composition.databinding.FragmentChooseLevelBinding
import ru.uspehovmax.composition.domain.entity.Level

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    companion object {
        const val NAME = "ChooseLevelFragment"

        // вместо newInstance используются safeargs для передачи данных
        fun newInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            buttonLevelTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }

            buttonLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }

            buttonLevelNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }

            buttonLevelHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }

    private fun launchGameFragment(level: Level) {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, GameFragment.newInstance(level))
//            .addToBackStack(GameFragment.NAME)
//            .commit()
        // поменяли. на navigate
        // Для передачи level используем args = Bundle()
//        val args = Bundle().apply {
//            putParcelable(GameFragment.KEY_LEVEL, level)
//        }
//          передача арргументов через safeargs
//        findNavController().navigate(R.id.action_chooseLevelFragment_to_gameFragment, args)
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(
                level
            )
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}