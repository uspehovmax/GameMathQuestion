package ru.uspehovmax.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// enum классы неявно реализуют инт. Serializable - преобразуют данные в набор байт
@Parcelize
enum class Level: Parcelable {
    TEST , EASY, NORMAL, HARD
}