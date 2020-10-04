package io.github.oiwane.alarmsample.widget.spinner

import android.content.Context
import android.R.layout
import android.widget.ArrayAdapter
import android.widget.Spinner
import io.github.oiwane.alarmsample.R

class SpinnerContentInitializer(private val context: Context) {
    fun initSpinnerContent(spinner: Spinner, range: IntRange, stepNum: Int = 1, format: String = "%02d") {
        val list = ArrayList<String>()
        for (i in range step stepNum)
            list.add(String.format(format, i))
        val adapter = ArrayAdapter(context, R.layout.spinner_item, list)
        adapter.setDropDownViewResource(layout.select_dialog_singlechoice)
        spinner.adapter = adapter
    }
}
