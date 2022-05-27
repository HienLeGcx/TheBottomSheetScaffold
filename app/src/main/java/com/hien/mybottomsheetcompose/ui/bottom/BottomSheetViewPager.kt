package com.hien.mybottomsheetcompose.ui.bottom

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.max

/**
 * A specialized ViewPager wrapper to be used in bottom sheets.
 * It it very opinionated how to size it's children so it is not a general replacement for ViewPager2.
 *
 * The magic happens in [onMeasure]
 */
class BottomSheetViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, 0) {

    var offscreenPageLimit: Int
        set(value) {
            if (value > 0) {
                viewPager.offscreenPageLimit = value
            }
        }
        get() = viewPager.offscreenPageLimit

    var adapter: Adapter<*>?
        set(value) {
            viewPager.adapter = value
        }
        get() = viewPager.adapter

    val viewPager = ViewPager2(context, attrs, defStyleAttr)

    init {
        viewPager.layoutParams =
            ViewGroup.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        addView(viewPager)
    }

    fun selectTab(pos: Int) {
        viewPager.setCurrentItem(pos, false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var height = 0
        val recyclerView = (viewPager as ViewGroup).getChildAt(0) as ViewGroup
        for (i in 0 until recyclerView.childCount) {
            val child = recyclerView.getChildAt(i)
            // re-measure the "pages"
            // keep the width and have it's height max the height of the pager itself
            child.measure(
                MeasureSpec.makeMeasureSpec(
                    child.measuredWidth,
                    MeasureSpec.EXACTLY
                ),
                MeasureSpec.makeMeasureSpec(
                    measuredHeight,
                    MeasureSpec.AT_MOST
                )
            )

            // calculate the tallest "page"
            height = max(height, child.measuredHeight)
        }

        setMeasuredDimension(
            measuredWidth,
            height
        )
    }
}
