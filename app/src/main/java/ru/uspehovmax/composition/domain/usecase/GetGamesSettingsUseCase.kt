package ru.uspehovmax.composition.domain.usecase

import ru.uspehovmax.composition.domain.entity.GameSettings
import ru.uspehovmax.composition.domain.entity.Level
import ru.uspehovmax.composition.domain.entity.Question
import ru.uspehovmax.composition.domain.repository.GameRepository

class GetGamesSettingsUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }

}