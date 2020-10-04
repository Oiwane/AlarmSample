package io.github.oiwane.alarmsample.widget.toggleButton

import android.view.View
import android.widget.ToggleButton
import io.github.oiwane.alarmsample.R
import io.github.oiwane.alarmsample.data.DayOfWeek

class DOWToggleButtonGroup(view: View) {
    var sunToggleButton: ToggleButton = view.findViewById(R.id.sunToggleButton)
    var monToggleButton: ToggleButton = view.findViewById(R.id.monToggleButton)
    var tueToggleButton: ToggleButton = view.findViewById(R.id.tueToggleButton)
    var wedToggleButton: ToggleButton = view.findViewById(R.id.wedToggleButton)
    var thuToggleButton: ToggleButton = view.findViewById(R.id.thuToggleButton)
    var friToggleButton: ToggleButton = view.findViewById(R.id.friToggleButton)
    var satToggleButton: ToggleButton = view.findViewById(R.id.satToggleButton)

    fun setEnabledToAll(isEnabled: Boolean) {
        sunToggleButton.isEnabled = isEnabled
        monToggleButton.isEnabled = isEnabled
        tueToggleButton.isEnabled = isEnabled
        wedToggleButton.isEnabled = isEnabled
        thuToggleButton.isEnabled = isEnabled
        friToggleButton.isEnabled = isEnabled
        satToggleButton.isEnabled = isEnabled
    }

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