package ru.uspehovmax.composition.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Serializable в Андроиде редко используют из-за малой скорости, обычно Parcelable
 * Serializable - интерфейс-маркер, т.е. ничего не нужно переопределять
 * Parcelable - интерфейс, в кот.нужно переопределить А.методы и указать какие поля и порядок следования
 * в build.gradle применить плагин  id 'kotlin-parcelize'
*/
@Parcelize
data class GameSettings (
    val maxSumValue: Int,
    val minCountOofRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
    ): Parcelable

/*
    // считываение всех полей как и в конструкторе
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    // заись в parcel как и в конструкторе
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(maxSumValue)
        parcel.writeInt(minCountOofRightAnswers)
        parcel.writeInt(minPercentOfRightAnswers)
        parcel.writeInt(gameTimeInSeconds)
    }

    // возвращает 0
    override fun describeContents(): Int {
        return 0
    }

    //
    companion object CREATOR : Parcelable.Creator<GameSettings> {
        // вызывает конструктор с параметром parcel: Parcel
        override fun createFromParcel(parcel: Parcel): GameSettings {
            return GameSettings(parcel)
        }

        // массив объекотв GameSettings?
        override fun newArray(size: Int): Array<GameSettings?> {
            return arrayOfNulls(size)
        }
    }

*/