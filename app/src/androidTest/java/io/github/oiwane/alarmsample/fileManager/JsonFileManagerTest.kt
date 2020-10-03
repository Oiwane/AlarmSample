package io.github.oiwane.alarmsample.fileManager

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oiwane.alarmsample.data.AlarmProperty
import io.github.oiwane.alarmsample.data.DayOfWeek
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class JsonFileManagerTest {
    private lateinit var file: File
    private val initPropertyStr: String = "" +
            "[{" +
            "\"dow\":{" +
                "\"sun\":false," +
                "\"mon\":false," +
                "\"tue\":false," +
                "\"wed\":false," +
                "\"thu\":false," +
                "\"fri\":false," +
                "\"sat\":false" +
            "}," +
            "\"hasRepeated\":false," +
            "\"hasSnoozed\":false," +
            "\"hour\":0," +
            "\"id\":\"dummy-property\"," +
            "\"minute\":0," +
            "\"snoozeTime\":0," +
            "\"title\":\"dummy-title\"}]"

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        file = File(context.filesDir, "alarm.json")
        file.writeText(initPropertyStr)
    }

    @Test
    fun write() {
        val propertyList = ArrayList<AlarmProperty>()
        val alarmProperty = AlarmProperty(
            "dummy-property",
            "dummy-title",
            0,
            0,
            false,
            0,
            false,
            DayOfWeek.DEFAULT
        )
        propertyList.add(alarmProperty)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        JsonFileManager(context).write(propertyList)
        val expected = jacksonObjectMapper().writeValueAsString(propertyList)
        val actual = file.readText()
        assertEquals(expected, actual)
    }

    @Test
    fun load() {
        val expected = jacksonObjectMapper().readValue<List<AlarmProperty>>(
            file.reader().use {
                it.readText()
            }
        )
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val actual = JsonFileManager(context).load()

        assertEquals(expected.size, actual.size)
        for (i in expected.indices)
            assertEquals(expected.toString(), actual.toString())
    }
}
// ,{"title":"alarm1","hour":0,"minute":0,"hasSetSnooze":false,"snoozeTime":0,"hasRepeated":false,"dow":{"sun":false,"mon":false,"tue":false,"wed":false,"thu":false,"fri":false,"sat":false},"id":"2aff6ac5-43ce-4efe-983d-4954149e21dc"},{"title":"alarm2","hour":0,"minute":0,"hasSetSnooze":false,"snoozeTime":0,"hasRepeated":false,"dow":{"sun":false,"mon":false,"tue":false,"wed":false,"thu":false,"fri":false,"sat":false},"id":"bf9630bf-a29e-40b1-9ad8-47056597a604"}