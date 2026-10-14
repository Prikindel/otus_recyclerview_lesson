package ru.prike.otus_recyclerview_lesson

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
//    private val data: Data = Data
    private val callback: Callback
) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        callback.exchange(
            fromPosition = viewHolder.bindingAdapterPosition,
            toPosition = target.bindingAdapterPosition
        )
        return true
    }

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) {
//        data.removeAtPosition(viewHolder.bindingAdapterPosition)
//        viewHolder.adapter.submitList(data.items)
        callback.onRemoveItem(viewHolder.bindingAdapterPosition)
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.25f
    }

    private val RecyclerView.ViewHolder.adapter: ChatDiffAdapter
        get() = bindingAdapter as ChatDiffAdapter

    interface Callback {
        fun onRemoveItem(position: Int)
        fun exchange(
            fromPosition: Int,
            toPosition: Int
        )
    }
}