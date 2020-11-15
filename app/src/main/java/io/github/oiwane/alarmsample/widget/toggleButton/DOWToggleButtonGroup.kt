package io.github.oiwane.alarmsample.widget.toggleButton

import android.view.View
import android.widget.ToggleButton
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.data.DayOfWeek

/**
 * 曜日のトグルボタンを管理するクラス
 * @param view トグルボタンの親
 */
class DOWToggleButtonGroup(view: View) {
    var sunToggleButton: ToggleButton = view.findViewById(R.id.sunToggleButton)
    var monToggleButton: ToggleButton = view.findViewById(R.id.monToggleButton)
    var tueToggleButton: ToggleButton = view.findViewById(R.id.tueToggleButton)
    var wedToggleButton: ToggleButton = view.findViewById(R.id.wedToggleButton)
    var thuToggleButton: ToggleButton = view.findViewById(R.id.thuToggleButton)
    var friToggleButton: ToggleButton = view.findViewById(R.id.friToggleButton)
    var satToggleButton: ToggleButton = view.findViewById(R.id.satToggleButton)

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
     * すべてのトグルボタンの有効/無効を設定する
     * @param isEnabled 有効か
     */
    fun setEnabledToAll(isEnabled: Boolean) {
        sunToggleButton.isEnabled = isEnabled
        monToggleButton.isEnabled = isEnabled
        tueToggleButton.isEnabled = isEnabled
        wedToggleButton.isEnabled = isEnabled
        thuToggleButton.isEnabled = isEnabled
        friToggleButton.isEnabled = isEnabled
        satToggleButton.isEnabled = isEnabled
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