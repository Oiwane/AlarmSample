package io.github.oiwane.alarmsample.fileManager

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oiwane.alarmsample.data.AlarmList
import java.io.File
import java.io.FileNotFoundException

class JsonFileManager(context: Context) {
    private val file = File(context.filesDir, "alarm.json")

    /**
     * Jsonファイルにアラーム情報リストを書き込む
     * @param alarmList 書き込むアラーム情報リスト
     */
    fun write(alarmList: AlarmList): Boolean {
        val jsonString = jacksonObjectMapper().writeValueAsString(alarmList)
        return try {
            file.writer().use {
                it.write(jsonString)
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Jsonファイルからアラーム情報リストを読み取る
     * @return Jsonファイルから読み込んだアラーム情報リスト。読み取りに失敗した場合、nullを返す。
     * @throws FileNotFoundException ファイルが見つからない（インストール時は必ずファイルが無い）
     */
    fun load(): AlarmList? {
        try {
            return jacksonObjectMapper().readValue(read()) as AlarmList
        } catch (e: UnrecognizedPropertyException) {
            Log.e("JsonFileManager", "convert failed.")
        } catch (e: MismatchedInputException) {
            Log.e("JsonFileManager", "mismatch property in file.")
        }
        return null
    }

    /**
     * Jsonファイルからアラーム情報リストを文字列として取得する
     * @return Json形式の文字列
     */
    private fun read(): String {
        return file.reader().use {
            it.readText()
        }
    }
}