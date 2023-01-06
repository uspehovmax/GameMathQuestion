package ru.uspehovmax.composition.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import ru.uspehovmax.composition.domain.entity.Level

/**
 * Создание Фабрики GameViewModelFactory для передачи параметров во ViewModel
 */
class GameViewModelFactory(
    private val application: Application,
    private val level: Level
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, level) as T
        }
        throw RuntimeException("Unknown view model ${modelClass}")
    }
}