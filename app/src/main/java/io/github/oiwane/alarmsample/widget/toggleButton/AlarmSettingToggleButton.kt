package io.github.oiwane.alarmsample.widget.toggleButton

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import io.github.oiwane.alarmsample.R

class AlarmSettingToggleButton : androidx.appcompat.widget.AppCompatToggleButton {
    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        foreground =
            if (checked)
                ContextCompat.getDrawable(context, R.drawable.ic_alert_during_setting)
            else
                ContextCompat.getDrawable(context, R.drawable.ic_alert_during_stopping)
    }
}