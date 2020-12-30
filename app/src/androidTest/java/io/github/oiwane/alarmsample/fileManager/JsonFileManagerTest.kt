package io.github.oiwane.alarmsample.fileManager

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oiwane.alarmsample.alarm.AlarmList
import io.github.oiwane.alarmsample.alarm.AlarmProperty
import io.github.oiwane.alarmsample.alarm.DayOfWeek
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileNotFoundException

@RunWith(AndroidJUnit4::class)
class JsonFileManagerTest {
    private lateinit var file: File
    private val initPropertyStr: String =
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
        val propertyList = AlarmList()
        val alarmProperty = AlarmProperty(
            "dummy-property", "dummy-title", 0, 0, false, 0, DayOfWeek(), true
        )
        propertyList.add(alarmProperty)
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val flag = JsonFileManager(context).write(propertyList)
        assertTrue(flag)
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
        assertNotNull(actual)

        assertEquals(expected.size, actual!!.size)
        for (i in expected.indices)
            assertEquals(expected.toString(), actual.toString())
    }

    @Test
    fun loadMismatch() {
        file.writer().use {
            it.write("")
        }
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val actual = JsonFileManager(context).load()
        assertNull(actual)
    }

    @Test
    fun loadNotFoundFile() {
        file.delete()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        try {
            JsonFileManager(context).load()
            assertTrue(false)
        } catch (e: FileNotFoundException) {
            assertTrue(true)
        }
    }
}
