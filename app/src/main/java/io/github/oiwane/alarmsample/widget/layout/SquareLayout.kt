package io.github.oiwane.alarmsample.widget.layout

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * 正方形のFrameLayout
 *
 * heightのサイズに合わせて正方形になる
 */
class SquareLayout : FrameLayout {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec)
    }
}