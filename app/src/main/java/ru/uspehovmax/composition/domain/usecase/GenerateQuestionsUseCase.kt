package ru.uspehovmax.composition.domain.usecase

import ru.uspehovmax.composition.domain.entity.GameSettings
import ru.uspehovmax.composition.domain.entity.Question
import ru.uspehovmax.composition.domain.repository.GameRepository

class GenerateQuestionsUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(maxSumValue: Int): Question {
        return repository.generateQuestion(maxSumValue, COUNT_OF_OPTIONS)
    }

    companion object{
        private const val COUNT_OF_OPTIONS = 6
    }
}