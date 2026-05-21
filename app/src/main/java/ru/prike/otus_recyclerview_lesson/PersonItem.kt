package ru.prike.otus_recyclerview_lesson

import androidx.annotation.ColorRes

data class PersonItem(
    val id: Int,
    val name: String,
    val date: String,
    val message: String,
)

@ColorRes
fun PersonItem.getColor(): Int {
    return if (id % 2 == 0) R.color.red else R.color.blue
}