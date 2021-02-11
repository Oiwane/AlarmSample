package io.github.oiwane.alarmsample.widget.spinner

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.widget.ArrayAdapter
import io.github.oiwane.alarmsample.R

class MySpinner : androidx.appcompat.widget.AppCompatSpinner {
    constructor(context: Context): super(context)
    constructor(context: Context, mode: Int): super(context, mode)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, mode: Int): super(context, attrs, defStyleAttr, mode)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, mode: Int, popupTheme: Resources.Theme): super(context, attrs, defStyleAttr, mode, popupTheme)

    fun initContent(range: IntRange, stepNum: Int = 1, format: String = "%02d") {
        val list = ArrayList<String>()
        for (i in range step stepNum)
            list.add(format.format(i))
        val adapter = ArrayAdapter(context, R.layout.spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        this.adapter = adapter
    }
}