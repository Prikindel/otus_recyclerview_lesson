package ru.prike.otus_recyclerview_lesson

data class PersonItem(
    override val id: Int,
    val name: String,
    val date: String,
    val message: String,
    val isHide: Boolean = false
) : Item {

    fun changeVisible() = copy(isHide = !isHide)
}

data class DayItem(
    override val id: Int,
    val date: String
) : Item

interface Item {
    val id: Int
}