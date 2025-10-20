package ru.prike.otus_recyclerview_lesson

import androidx.annotation.ColorRes

data class PersonItem(
    override val id: Int,
    val name: String,
    val date: String,
    val message: String,
    @ColorRes val backgroundColor: Int = 0,
    val isHide: Boolean = false
) : Item

data class DayItem(
    override val id: Int,
    val title: String,
) : Item

interface Item {
    val id: Int
}