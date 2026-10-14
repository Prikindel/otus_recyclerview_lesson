package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity(), ChatListener {

    private val data = Data
    private val chatAdapter by lazy { ChatAdapter(this) }
    private val chatDiffAdapter by lazy { ChatDiffAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        data.generateTestData()

        val rv = findViewById<RecyclerView>(R.id.listView)
//        rv.addItemDecoration(
//            DividerItemDecoration(
//                this,
//                DividerItemDecoration.VERTICAL
//            )
//        )
//========================================================================================
//        rv.addItemDecoration(
//            DividerItemDecoration(
//                this,
//                DividerItemDecoration.VERTICAL
//            ).apply {
//                ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
//                    ?.let(::setDrawable)
//            }
//        )
//========================================================================================
        rv.addItemDecoration(CustomDecorator())

        val itemTouchHelperCallback = ItemTouchHelperCallback(
            object : ItemTouchHelperCallback.Callback {

                override fun onRemoveItem(position: Int) {
                    data.removeAtPosition(position)
                    updateChat()
                }

                override fun exchange(fromPosition: Int, toPosition: Int) {
                    data.exchange(fromPosition, toPosition)
                    updateChat()
                }
            }
        )
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv)

        rv.adapter = chatDiffAdapter

//        chatAdapter.setList(personItems.toList())
        updateChat()
    }

    override fun onItemClick(id: Int) {
//        val personItem = personItems.find { it.id == id } ?: return
//        Toast.makeText(this, "Клик по ${personItem.name}", Toast.LENGTH_SHORT).show()

//        val index = personItems.indexOfFirst { it.id == id }
//        if (personItem is PersonItem) {
////            personItems.add(index + 1, personItem.copy(id = personItems.maxOf { it.id } + 1))
//            personItems.removeAt(index)
//            personItems.add(index, personItem.copy(message = "Новое сообщение"))
//        }
//        updateChat()
//        chatAdapter.addItem(personItems.toList(), index + 1)

//        personItems.removeAt(index)
//        personItems.add(index + 6, personItem)
//        chatAdapter.replace(personItems, index, index + 6)
//        when (personItem) {
//            is PersonItem -> {
//                personItems.removeAt(index)
//                personItems.add(index, personItem.copy(message = "Новое сообщение"))
//            }
//
//            is DayItem -> {
//                for (i in index + 1 until personItems.size) {
//                    if (personItems[i] is DayItem) break
//                    else {
//                        val person = personItems[i] as PersonItem
//                        personItems.removeAt(i)
//                        personItems.add(i, person.changeVisible())
//                    }
//                }
//            }
//        }

        data.changeData(id)
        updateChat()
    }

    override fun onItemDelete(id: Int) {
        data.deleteData(id)
        updateChat()
//        chatAdapter.removeItem(personItems.toList(), index)
    }

    private fun updateChat() {
//        chatDiffAdapter.submitList(personItems.toList().filter { (it as? PersonItem)?.isHide != true })
        chatDiffAdapter.submitList(data.items)
    }
}