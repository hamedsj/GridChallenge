package me.hamedsj.gridchallenge.utils

import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import java.lang.Integer.max

class HamidGridLayoutManager: RecyclerView.LayoutManager() {

    var verticalScrollOffset = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        fill(recycler = recycler)
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    override fun scrollVerticallyBy(dy: Int, recycler: Recycler?, state: RecyclerView.State?): Int {
        if (childCount == 0) return 0
        val lastScrollOffset = verticalScrollOffset

        var lastItem = getChildAt(itemCount - 1)
        if (((itemCount - 1) / 3) % 3 == 0) {
            lastItem = getChildAt(itemCount - 1 - ((itemCount - 1) % 3))
        }
        val lastItemEnd = lastItem?.run {
            getDecoratedBottom(lastItem) + (lastItem.layoutParams as MarginLayoutParams).bottomMargin
        } ?: 0

        if (dy > 0 && lastItemEnd <= height) {
            verticalScrollOffset = max(verticalScrollOffset + (lastItemEnd - height), 0)
            fill(recycler = recycler)
            return verticalScrollOffset - lastScrollOffset
        }
        verticalScrollOffset = max(verticalScrollOffset + dy, 0)
        fill(recycler = recycler)
        return verticalScrollOffset - lastScrollOffset
    }

    private fun fill(recycler: Recycler?){
        detachAndScrapAttachedViews(requireNotNull(recycler))
        for (index in 0 until itemCount) {
            val view = recycler.getViewForPosition(index)
            addView(view)
            val size = width / 3
            val lp = view.layoutParams as RecyclerView.LayoutParams
            lp.setMargins(2.toPx(view.context))
            lp.width = size
            lp.height = size
            view.layoutParams = lp

            val rowIndex = index / 3
            val numberOfTwiceSizeBefore = if (rowIndex % 3 == 0) (rowIndex / 3) else (rowIndex / 3) + 1

            var left: Int
            var right: Int
            var top = (numberOfTwiceSizeBefore * 2 * size) + ((rowIndex - numberOfTwiceSizeBefore) * size) - verticalScrollOffset
            var bottom: Int

            if (index % 18 == 0) {
                left = width - 2 * size
                right = width
                bottom = top + 2 * size
                val layoutParams = view.layoutParams as RecyclerView.LayoutParams
                layoutParams.width = 2 * size
                layoutParams.height = 2 * size
                view.layoutParams = layoutParams
            } else if (index % 18 == 1) {
                left = 0
                right = left + size
                bottom = top + size
            } else if (index % 18 == 2) {
                left = 0
                right = left + size
                top += size
                bottom = top + size
            } else if (index % 9 == 0) {
                left = 0
                right = size * 2
                bottom = top + 2 * size
                val layoutParams = view.layoutParams as RecyclerView.LayoutParams
                layoutParams.width = 2 * size
                layoutParams.height = 2 * size
                view.layoutParams = layoutParams
            } else if (index % 9 == 1) {
                left = width - size
                right = left + size
                bottom = top + size
            } else if (index % 9 == 2) {
                left = width - size
                right = left + size
                top += size
                bottom = top + size
            } else {
                left = width - size - (index % 3 * size)
                right = left + size
                bottom = top + size
            }

            measureChildWithMargins(view, size, size)
            layoutDecoratedWithMargins(view, left, top, right, bottom)
        }
        val scrapListCopy = recycler.scrapList.toList()
        scrapListCopy.forEach {
            recycler.recycleView(it.itemView)
        }
    }

}