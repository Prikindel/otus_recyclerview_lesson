package ru.prike.otus_recyclerview_lesson

import java.util.Collections
import kotlin.collections.iterator
import kotlin.io.iterator
import kotlin.text.clear
import kotlin.text.get
import kotlin.text.iterator

object Data {
    private val personItems = mutableListOf<Item>()
    val items get() = personItems.toList().filter { (it as? PersonItem)?.isHide != true }

    fun generateTestData() {
        personItems.clear()
        for (i in 0..100) {
            if (i % 4 == 0) {
                val dayNumber = i / 4
                val day = DayItem(
                    id = i,
                    date = "Day $dayNumber"
                )
                personItems.add(day)
            } else {
                personItems.add(
                    PersonItem(
                        id = i,
                        name = "Пользователь $i",
                        date = "${i % 24}:${String.format("%02d", i % 60)}",
                        message = "Это сообщение номер $i для демонстрации работы ListView"
                    )
                )
            }
        }
    }

    fun changeData(id: Int) {
        val personItem = personItems.find { it.id == id } ?: return
        val index = personItems.indexOfFirst { it.id == id }

        when (personItem) {
            is PersonItem -> {
                personItems.removeAt(index)
                personItems.add(index, personItem.copy(message = "Новое сообщение"))
            }

            is DayItem -> {
                for (i in index + 1 until personItems.size) {
                    if (personItems[i] is DayItem) break
                    else {
                        val person = personItems[i] as PersonItem
                        personItems.removeAt(i)
                        personItems.add(i, person.changeVisible())
                    }
                }
            }
        }
    }

    fun exchange(fromPosition: Int, toPosition: Int) {
        val fromItem = personItems[fromPosition]
        personItems.removeAt(fromPosition)
        personItems.add(toPosition, fromItem)

//        if (fromPosition < toPosition) {
//            for (index in fromPosition until toPosition) {
//                Collections.swap(personItems, index, index + 1)
//            }
//        } else {
//            for (index in toPosition until fromPosition) {
//                Collections.swap(personItems, index, index - 1)
//            }
//        }
    }

    fun deleteData(id: Int) {
        val index = personItems.indexOfFirst { it.id == id }
        personItems.removeAt(index)
    }

    fun removeAtPosition(position: Int) {
        personItems.removeAt(position)
    }
}