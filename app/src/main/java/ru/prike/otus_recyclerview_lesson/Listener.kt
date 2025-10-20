package ru.prike.otus_recyclerview_lesson

interface Listener {

    fun onItemClick(id: Int)

    fun onItemPupkinDelete(id: Int)

    fun exchange(from: Int, to: Int)
}