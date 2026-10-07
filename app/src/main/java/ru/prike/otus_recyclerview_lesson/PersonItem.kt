package ru.prike.otus_recyclerview_lesson

data class PersonItem(
    override val id: Int,
    val name: String,
    val date: String,
    val message: String,
) : Item

data class DayItem(
    override val id: Int,
    val date: String
) : Item

interface Item {
    val id: Int
}