package ru.prike.otus_recyclerview_lesson

import androidx.annotation.ColorRes

data class PersonItem(
    override val id: Int,
    val name: String,
    val date: String,
    val message: String,
    val isHide: Boolean = false
) : Item

data class DayItem(
    override val id: Int,
    val date: String
) : Item

interface Item {
    val id: Int
}

@ColorRes
fun PersonItem.getColor(): Int {
    return if (id % 2 == 0) R.color.red else R.color.blue
}