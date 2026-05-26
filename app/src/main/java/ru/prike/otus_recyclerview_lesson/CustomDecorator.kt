package ru.prike.otus_recyclerview_lesson

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class CustomDecorator : ItemDecoration() {

    private val bounds = Rect()
    private val paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 8f
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(36, 16, 16, 36)
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)

        val childCount = parent.childCount
        for (index in 0 until childCount) {
            val child = parent.getChildAt(index)
            parent.getDecoratedBoundsWithMargins(child, bounds)

            val positionCurrent = parent.getChildAdapterPosition(child)
            if (positionCurrent != RecyclerView.NO_POSITION) {
                val lastElementPosition = parent.adapter?.itemCount?.minus(1)
                if (positionCurrent != lastElementPosition) {
                    canvas.drawLine(
                        bounds.left.toFloat(),
                        bounds.bottom.toFloat(),
                        bounds.right.toFloat(),
                        bounds.bottom.toFloat(),
                        paint
                    )
                }
            }
        }
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount

        val adapter = parent.adapter
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)
            if (position != RecyclerView.NO_POSITION && position % 2 == 0) {
                if (adapter?.getItemViewType(position) == ChatDiffAdapter.ViewType.PERSON.id) {
                    parent.getDecoratedBoundsWithMargins(child, bounds)

                    canvas.drawLine(
                        bounds.left.toFloat() + 15f,
                        bounds.top.toFloat(),
                        bounds.right.toFloat() + 15f,
                        bounds.bottom.toFloat(),
                        paint
                    )
                    canvas.drawLine(
                        bounds.right.toFloat() - 15f,
                        bounds.top.toFloat(),
                        bounds.left.toFloat() - 15f,
                        bounds.bottom.toFloat(),
                        paint
                    )
                }
            }
        }
    }
}