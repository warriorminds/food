package com.warriorminds.lifesum.adapters

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.warriorminds.lifesum.R

abstract class SwipeToDeleteCallback(private val context: Context): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

    override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
        actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val isCancelled = dX == 0f && !isCurrentlyActive

        if (isCancelled) {
            canvas.drawRect(itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat(), clearPaint)
            super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        val background = ColorDrawable()
        background.color = ContextCompat.getColor(context, R.color.deleteBackground)
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(canvas)

        drawDeleteIcon(itemView, canvas)
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun drawDeleteIcon(itemView: View, canvas: Canvas) {
        val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete)
        deleteIcon?.let {deleteIcon ->
            val itemHeight = itemView.bottom - itemView.top
            val deleteIconTop = itemView.top + (itemHeight - deleteIcon.intrinsicHeight) / 2
            val deleteIconMargin = (itemHeight - deleteIcon.intrinsicHeight) / 2
            val deleteIconLeft = itemView.right - deleteIconMargin - deleteIcon.intrinsicWidth
            val deleteIconRight = itemView.right - deleteIconMargin
            val deleteIconBottom = deleteIconTop + deleteIcon.intrinsicHeight

            deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
            deleteIcon.draw(canvas)
        }
    }
}