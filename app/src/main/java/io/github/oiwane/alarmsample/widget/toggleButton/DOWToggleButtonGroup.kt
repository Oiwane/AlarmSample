package io.github.oiwane.alarmsample.widget.toggleButton

import android.view.View
import android.widget.ToggleButton
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.alarm.DayOfWeek

/**
 * 曜日のトグルボタンを管理するクラス
 * @param view トグルボタンの親
 */
class DOWToggleButtonGroup(view: View) {
    private val sunToggleButton: ToggleButton = view.findViewById(R.id.sunToggleButton)
    private val monToggleButton: ToggleButton = view.findViewById(R.id.monToggleButton)
    private val tueToggleButton: ToggleButton = view.findViewById(R.id.tueToggleButton)
    private val wedToggleButton: ToggleButton = view.findViewById(R.id.wedToggleButton)
    private val thuToggleButton: ToggleButton = view.findViewById(R.id.thuToggleButton)
    private val friToggleButton: ToggleButton = view.findViewById(R.id.friToggleButton)
    private val satToggleButton: ToggleButton = view.findViewById(R.id.satToggleButton)

    fun setChecked(dow: DayOfWeek) {
        sunToggleButton.isChecked = dow.sun
        monToggleButton.isChecked = dow.mon
        tueToggleButton.isChecked = dow.tue
        wedToggleButton.isChecked = dow.wed
        thuToggleButton.isChecked = dow.thu
        friToggleButton.isChecked = dow.fri
        satToggleButton.isChecked = dow.sat
    }

    /**
     * 曜日の繰り返し設定を取得する
     * @return 曜日の繰り返し設定
     */
    fun getDayOfWeek(): DayOfWeek {
        return DayOfWeek(
            sunToggleButton.isChecked,
            monToggleButton.isChecked,
            tueToggleButton.isChecked,
            wedToggleButton.isChecked,
            thuToggleButton.isChecked,
            friToggleButton.isChecked,
            satToggleButton.isChecked
        )
    }
}