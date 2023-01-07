package ru.uspehovmax.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Serializable в Андроиде редко используют из-за малой скорости, обычно Parcelable
 * Serializable - интерфейс-маркер, т.е. ничего не нужно переопределять
 * Parcelable - интерфейс, в кот.нужно переопределить А.методы и указать
 * какие поля и порядок следования, чтобы упростить библиотека Parcelize
 * в build.gradle применить плагин  id 'kotlin-parcelize'
 */
@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestions: Int,
    val gameSettings: GameSettings
) : Parcelable

//    : Serializable
