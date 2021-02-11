package io.github.oiwane.alarmsample.alarm

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class AlarmProperty{
    @JsonProperty var id: String = UUID.randomUUID().toString()
    @JsonProperty var title: String
    @JsonProperty var hour: Int
    @JsonProperty var minute: Int
    @JsonProperty var hasSnoozed: Boolean
    @JsonProperty var snoozeTime: Int
    @JsonProperty var dow: DayOfWeek
    @JsonProperty var isSet: Boolean

    // Jsonの例外対策
    constructor(): this("", 0, 0, false, 0, DayOfWeek(), true)

    constructor(id: String?,
                title: String,
                hour: Int,
                minute: Int,
                hasSnoozed: Boolean,
                snoozeTime: Int,
                dow: DayOfWeek,
                isSet: Boolean
    ): this(title, hour, minute, hasSnoozed, snoozeTime, dow, isSet) {
        if (!id.isNullOrEmpty())
            this.id = id
    }

    constructor(title: String,
                hour: Int,
                minute: Int,
                hasSnoozed: Boolean,
                snoozeTime: Int,
                dow: DayOfWeek,
                isSet: Boolean
    ) {
        this.title = title
        this.hour = hour
        this.minute = minute
        this.hasSnoozed = hasSnoozed
        this.snoozeTime = snoozeTime
        this.dow = dow
        this.isSet = isSet
    }

    fun setProperty(newProperty: AlarmProperty) {
        title = newProperty.title
        hour = newProperty.hour
        minute = newProperty.minute
        hasSnoozed = newProperty.hasSnoozed
        snoozeTime = newProperty.snoozeTime
        dow = newProperty.dow
        isSet = newProperty.isSet
    }

    /**
     * アラームを鳴らす日時を計算する
     * @return アラームを鳴らす日時
     */
    fun calcTriggerCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val now = Calendar.getInstance()
        now.timeInMillis = System.currentTimeMillis()
        if (calendar <= now) {
            calendar.add(Calendar.DATE, 1)
        }
        if (dow.isSpecified()) {
            reflectRepeatSettings(calendar)
        }
        return calendar
    }

    /**
     * 繰り返し機能の設定を反映する
     * @param calendar アラームを鳴らす日時
     */
    private fun reflectRepeatSettings(calendar: Calendar) {
        val array = arrayOf(dow.sat, dow.sun, dow.mon, dow.tue, dow.wed, dow.thu, dow.fri)
        val currentDow = calendar.get(Calendar.DAY_OF_WEEK)
        for (i in 0..6) {
            val dayOfWeek = currentDow + i
            if (array[dayOfWeek % 7]) {
                val addition = (dayOfWeek - currentDow + 7) % 7
                calendar.add(Calendar.DAY_OF_WEEK, addition)
                break
            }
        }
    }

    override fun toString(): String {
        return "[id: $id, " +
                "title: $title, " +
                "hour: $hour, " +
                "minute: $minute, " +
                "hasSnoozed: $hasSnoozed, " +
                "snoozeTime: $snoozeTime, " +
                "dow: $dow, " +
                "isSet: $isSet]"
    }
}

class DayOfWeek(
    @JsonProperty var sun: Boolean,
    @JsonProperty var mon: Boolean,
    @JsonProperty var tue: Boolean,
    @JsonProperty var wed: Boolean,
    @JsonProperty var thu: Boolean,
    @JsonProperty var fri: Boolean,
    @JsonProperty var sat: Boolean
) {
    // Jsonの例外対策
    constructor(): this(false, false, false, false, false, false, false)

    override fun toString(): String {
        return "[sum: $sun, " +
                "mon: $mon, " +
                "tue: $tue, " +
                "wed: $wed, " +
                "thu: $thu, " +
                "fri: $fri, " +
                "sat: $sat]"
    }

    /**
     * 曜日指定しているか
     * @return 曜日指定しているか
     */
    @JsonIgnore
    fun isSpecified(): Boolean {
        return sun || mon || tue || wed || thu || fri || sat
    }
}