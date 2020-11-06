package io.github.oiwane.alarmsample.message

import android.content.Context
import android.widget.Toast

/**
 * エラーメッセージをトーストで出力するクラス
 */
class ErrorMessageToast(private val context: Context) {
    fun showErrorMessage(errorResId: Int) {
        val errorText = context.getText(errorResId)
        Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show()
    }
}