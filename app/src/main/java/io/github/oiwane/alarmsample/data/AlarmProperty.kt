package io.github.oiwane.alarmsample.data

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class AlarmProperty{
    @JsonProperty var id: String = UUID.randomUUID().toString()
    @JsonProperty var title: String
    @JsonProperty var hour: Int
    @JsonProperty var minute: Int
    @JsonProperty var hasSnoozed: Boolean
    @JsonProperty var snoozeTime: Int
    @JsonProperty var hasRepeated: Boolean
    @JsonProperty var dow: DayOfWeek

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
    @JsonProperty var sun: Boolean,
    @JsonProperty var mon: Boolean,
    @JsonProperty var tue: Boolean,
    @JsonProperty var wed: Boolean,
    @JsonProperty var thu: Boolean,
    @JsonProperty var fri: Boolean,
    @JsonProperty var sat: Boolean
) {
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