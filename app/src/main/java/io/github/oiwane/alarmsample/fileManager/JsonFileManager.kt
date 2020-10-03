package io.github.oiwane.alarmsample.fileManager

import android.content.Context
import android.util.Log
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.oiwane.alarmsample.data.AlarmProperty
import java.io.File
import java.io.FileNotFoundException

class JsonFileManager(context: Context) {
    private val file = File(context.filesDir, "alarm.json")

    /**
     * Jsonファイルにアラーム情報リストを書き込む
     * @param propertyList 書き込むアラーム情報リスト
     */
    fun write(propertyList: List<AlarmProperty>) {
        val jsonString = jacksonObjectMapper().writeValueAsString(propertyList)
        file.writer().use {
            it.write(jsonString)
        }
    }

    /**
     * Jsonファイルからアラーム情報リストを読み取る
     * @return Jsonファイルから読み込んだアラーム情報リスト。読み取りに失敗した場合、空のリストを返す。
     */
    fun load(): List<AlarmProperty> {
        try {
            return jacksonObjectMapper().readValue(read()) as ArrayList<AlarmProperty>
        } catch (e: UnrecognizedPropertyException) {
            Log.e("JsonFileManager", "convert failed.")
        } catch (e: MismatchedInputException) {
            Log.e("JsonFileManager", "")
        }
        return ArrayList()
    }

    /**
     * Jsonファイルからアラーム情報リストを文字列として取得する
     * @return Json形式の文字列。読み取りに失敗した場合、アラーム情報が空の状態の文字列を返す。
     */
    private fun read(): String {
        try {
            return file.reader().use {
                it.readText()
            }
        } catch (e: FileNotFoundException) {
            Log.d("JsonFileManager", "not found file.")
        }
        return "[]"
    }
}