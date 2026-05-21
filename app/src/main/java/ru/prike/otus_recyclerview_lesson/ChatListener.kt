package ru.prike.otus_recyclerview_lesson

interface ChatListener {

    fun onItemClick(id: Int)

    fun onItemDelete(id: Int)
}