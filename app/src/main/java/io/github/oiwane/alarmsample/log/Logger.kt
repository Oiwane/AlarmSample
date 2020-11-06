package io.github.oiwane.alarmsample.log

import android.content.Context
import android.util.Log

/**
 * ログを出力するクラス
 */
class Logger {
    companion object {
        fun write(type: LogType, message: String) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            val methodName = stackTraceElement.className + "." + stackTraceElement.methodName
            when (type) {
                LogType.INFO -> Log.i(methodName, message)
                LogType.ERROR -> Log.e(methodName, message)
                LogType.DEBUG -> Log.d(methodName, message)
            }
        }

        fun write(type: LogType, context: Context, messageId: Int) {
            val stackTraceElement = Thread.currentThread().stackTrace[3]
            val methodName = stackTraceElement.className + "." + stackTraceElement.methodName
            val message = context.getString(messageId)
            when (type) {
                LogType.INFO -> Log.i(methodName, message)
                LogType.ERROR -> Log.e(methodName, message)
                LogType.DEBUG -> Log.d(methodName, message)
            }
        }
    }
}

enum class LogType {
    INFO,
    ERROR,
    DEBUG
}
