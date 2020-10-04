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
    @JsonProperty val hasRepeated: Boolean
    @JsonProperty val dow: DayOfWeek

    // Jsonの例外対策
    constructor(): this("", 0, 0, false, 0, false, DayOfWeek.DEFAULT)

    constructor(id: String,
                title: String,
                hour: Int,
                minute: Int,
                hasSnoozed: Boolean,
                snoozeTime: Int,
                hasRepeated: Boolean,
                dow: DayOfWeek
    ): this(title, hour, minute, hasSnoozed, snoozeTime, hasRepeated, dow) {
        this.id = id
    }

    constructor(title: String,
                hour: Int,
                minute: Int,
                hasSnoozed: Boolean,
                snoozeTime: Int,
                hasRepeated: Boolean,
                dow: DayOfWeek
    ) {
        this.title = title
        this.hour = hour
        this.minute = minute
        this.hasSnoozed = hasSnoozed
        this.snoozeTime = snoozeTime
        this.hasRepeated = hasRepeated
        this.dow = dow
    }

    companion object {
        val DEFAULT = AlarmProperty("", 0, 0, false, 0, false, DayOfWeek.DEFAULT)
    }

    override fun toString(): String {
        return "[id: $id, " +
                "title: $title, " +
                "hour: $hour, " +
                "minute: $minute, " +
                "hasSnoozed: $hasSnoozed, " +
                "snoozeTime: $snoozeTime, " +
                "hasRepeated: $hasRepeated, " +
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

    companion object {
        val DEFAULT = DayOfWeek(false, false, false, false, false, false, false)
    }

    override fun toString(): String {
        return "[sum: $sun, " +
                "mon: $mon, " +
                "tue: $tue, " +
                "wed: $wed, " +
                "thu: $thu, " +
                "fri: $fri, " +
                "sat: $sat]"
    }
}