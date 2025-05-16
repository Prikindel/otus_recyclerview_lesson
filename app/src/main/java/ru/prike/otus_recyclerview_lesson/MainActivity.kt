package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Listener {

//    private val adapter: ChatAdapter by lazy { ChatAdapter(this) }
    private val adapter: ChatDiffAdapter by lazy { ChatDiffAdapter(this) }

    private var list: List<Item> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.list)

//        recyclerView.recycledViewPool.setMaxRecycledViews(ChatAdapter.ViewTypes.CHAT.id, 5)
//        recyclerView.setItemViewCacheSize(3)

//        recyclerView.addItemDecoration(
//            DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
//                ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
//                    ?.let { setDrawable(it) }
//            }
//        )

//        recyclerView.addItemDecoration(CustomDecorator())
        ItemTouchHelper(ItemTouchHelperCallback()).attachToRecyclerView(recyclerView)

        recyclerView.adapter = adapter
        list = generateList()
//        adapter.setData(list)
        adapter.submitList(list)

        recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val lstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

//                println(
//                    "Visible item count: $visibleItemCount\n" +
//                            "Total item count: $totalItemCount\n" +
//                            "First visible item position: $firstVisibleItemPosition\n" +
//                            "Last visible item position: $lstVisibleItemPosition"
//                )

                if (lstVisibleItemPosition > totalItemCount - 4) {
                    println("Added new items")
                    list = list + generateList(totalItemCount)
                    adapter.submitList(list)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()

//        lifecycleScope.launch {
//            delay(1000)
//            moved()
//            delay(1000)
//            moved()
//            delay(1000)
//            moved()
//            delay(1000)
//            moved()
//        }
    }

    override fun onItemClicked(id: Int) {
        Toast.makeText(this, "Clicked $id item", Toast.LENGTH_SHORT).show()
//        val item = list.find { (it as? DayItem)?.id == id } ?: return
//        val position = list.indexOfFirst { (it as? DayItem)?.id == id }
//        val list = mutableListOf<Item>()
//        repeat(10) {
//            val index = it + id
//            val personItem = ChatItem(
//                id = index,
//                name = "Name $index",
//                message = "This is message",
//                date = "12:05",
//                background = if (index % 2 == 0) R.color.blue else R.color.green
//            )
//            list.add(personItem)
//        }
//        this.list = this.list.toMutableList().apply{ addAll(position + 1, list.toList()) }
//
//        adapter.submitList(this.list)
    }

    override fun onItemActionClicked(id: Int) {
//        list = list.filter { it.id != id }
//        adapter.removeItem(id)
        val chatItem = list.find { (it as? ChatItem)?.id == id }
        list = list.filter { chatItem != it }
        adapter.submitList(list)
    }

    private fun moved() {
        val item = list.get(1)
        list = list.toMutableList().also {
            it.remove(item)
            it.add(5, item)
        }.toList()
        adapter.submitList(list)
    }

    fun generateList(startId: Int = 0) = run {
        val list = mutableListOf<Item>()
        repeat(startId + 20) {
            if (it % 10 == 0) {
                list.add(
                    DayItem(it, "Day ${it / 10}")
                )
            } else {
                val personItem = ChatItem(
                    id = it,
                    name = "Name $it",
                    message = "This is message",
                    date = "12:05",
                    background = if (it % 2 == 0) R.color.blue else R.color.green
                )
                list.add(personItem)
            }
        }

        list.toList()
    }
}