package com.conceptgang.coronanews.component.epoxy

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.conceptgang.coronanews.component.view.ZHViewData
import com.conceptgang.coronanews.utils.px

class BaseItemDecoration: RecyclerView.ItemDecoration(){
    var views: List<ZHViewData> = emptyList()

    val paint = Paint().apply {
        color = Color.parseColor("#EBEBF1")
        strokeWidth = 3f
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val position = parent.getChildAdapterPosition(view)

        if (position < 0) return

        val epoxyData = views[position].epoxyData

        outRect.set(
            epoxyData.paddingLeftDp.px,
            epoxyData.paddingTopDp.px,
            epoxyData.paddingRightDp.px,
            epoxyData.paddingBottomDp.px
        )
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        c.save()
        verticalDivider(c, parent)
        c.restore()
    }

    fun verticalDivider(
        c: Canvas,
        parent: RecyclerView
    ) {

        val childCount = parent.childCount

        for (i in 0 until childCount) {


            if(i >= views.size) break

            val child = parent.getChildAt(i)
            val j = parent.getChildAdapterPosition(child)
            if(j < 0 || j>= views.size) break
            val epoxyData = views[j].epoxyData


            val params = child.layoutParams as RecyclerView.LayoutParams

            val bottom = child.bottom + params.bottomMargin
            val top = child.top+params.topMargin

            val left = epoxyData.paddingLeftDp.px
            val right = parent.width - epoxyData.paddingRightDp.px

            if (epoxyData.hasDividerBottom) {

                c.drawLine(
                    left.toFloat(),
                    bottom.toFloat(),
                    right.toFloat(),
                    bottom.toFloat(),
                    paint
                )

            }

            if (epoxyData.hasDividerTop) {

                c.drawLine(
                    left.toFloat(),
                    top.toFloat(),
                    right.toFloat(),
                    top.toFloat(),
                    paint
                )

            }
        }
    }
}