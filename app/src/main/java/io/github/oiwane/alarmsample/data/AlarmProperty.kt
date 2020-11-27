package io.github.oiwane.alarmsample.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class AlarmProperty{
    @JsonProperty var id: String = UUID.randomUUID().toString()
    @JsonProperty val title: String
    @JsonProperty val hour: Int
    @JsonProperty val minute: Int
    @JsonProperty val hasSnoozed: Boolean
    @JsonProperty val snoozeTime: Int
    @JsonProperty val dow: DayOfWeek

    // Jsonの例外対策
    constructor(): this("", 0, 0, false, 0, DayOfWeek())

    constructor(id: String,
                title: String,
                hour: Int,
                minute: Int,
                hasSnoozed: Boolean,
                snoozeTime: Int,
                dow: DayOfWeek
    ): this(title, hour, minute, hasSnoozed, snoozeTime, dow) {
        this.id = id
    }

    constructor(title: String,
                hour: Int,
                minute: Int,
                hasSnoozed: Boolean,
                snoozeTime: Int,
                dow: DayOfWeek
    ) {
        this.title = title
        this.hour = hour
        this.minute = minute
        this.hasSnoozed = hasSnoozed
        this.snoozeTime = snoozeTime
        this.dow = dow
    }

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
        return calendar
    }

    override fun toString(): String {
        return "[id: $id, " +
                "title: $title, " +
                "hour: $hour, " +
                "minute: $minute, " +
                "hasSnoozed: $hasSnoozed, " +
                "snoozeTime: $snoozeTime, " +
                "dow: $dow]"
    }
}

class DayOfWeek(
    @JsonProperty val sun: Boolean,
    @JsonProperty val mon: Boolean,
    @JsonProperty val tue: Boolean,
    @JsonProperty val wed: Boolean,
    @JsonProperty val thu: Boolean,
    @JsonProperty val fri: Boolean,
    @JsonProperty val sat: Boolean
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
    fun isSpecified(): Boolean {
        return sun || mon || tue || wed || thu || fri || sat
    }
}