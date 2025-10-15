package ru.prike.otus_recyclerview_lesson

import androidx.annotation.ColorRes

data class PersonItem(
    val id: Int,
    val name: String,
    val date: String,
    val message: String,
    @ColorRes val backgroundColor: Int = 0
)