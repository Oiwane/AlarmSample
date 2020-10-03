package io.github.oiwane.alarmsample.data

import java.util.*

class AlarmProperty{
    private var id: String = UUID.randomUUID().toString()
        get
    private var title: String
        get
    private var hour: Int
        get
    private var minute: Int
        get
    private var hasSnoozed: Boolean
        get
    private var snoozeTime: Int
        get
    private var hasRepeated: Boolean
        get
    private var dow: DayOfWeek
        get

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
    sun: Boolean,
    mon: Boolean,
    tue: Boolean,
    wed: Boolean,
    thu: Boolean,
    fri: Boolean,
    sat: Boolean
) {
    private var sun: Boolean = sun
        get
    private var mon: Boolean = mon
        get
    private var tue: Boolean = tue
        get
    private var wed: Boolean = wed
        get
    private var thu: Boolean = thu
        get
    private var fri: Boolean = fri
        get
    private var sat: Boolean = sat
        get

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