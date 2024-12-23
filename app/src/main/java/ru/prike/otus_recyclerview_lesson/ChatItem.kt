package ru.prike.otus_recyclerview_lesson

import androidx.annotation.ColorRes

data class ChatItem(
    val id: Int,
    val name: String,
    val date: String,
    val message: String,
    @ColorRes val background: Int?
) : Item

data class DayItem(
    val id: Int,
    val title: String
) : Item

interface Item