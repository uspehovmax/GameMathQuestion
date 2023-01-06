package ru.uspehovmax.composition.domain.repository

import ru.uspehovmax.composition.domain.entity.GameSettings
import ru.uspehovmax.composition.domain.entity.Level
import ru.uspehovmax.composition.domain.entity.Question

interface GameRepository {

    fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int
    ): Question

    fun getGameSettings(level: Level): GameSettings
}