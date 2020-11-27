package io.github.oiwane.alarmsample.widget.spinner

import android.content.Context
import android.R.layout
import android.widget.ArrayAdapter
import android.widget.Spinner
import io.github.oiwane.alarmsample.R

/**
 * スピナーの中身を設定するクラス
 * @property context コンテキスト
 */
class SpinnerContentInitializer(private val context: Context) {

    /**
     * スピナーの中身を設定する
     * @param spinner 設定するスピナー
     * @param range 整数の範囲
     * @param stepNum ステップ数。デフォルトは1
     * @param format 表示文字列のフォーマット。デフォルトは0埋め2桁
     */
    fun initSpinnerContent(spinner: Spinner, range: IntRange, stepNum: Int = 1, format: String = "%02d") {
        val list = ArrayList<String>()
        for (i in range step stepNum)
            list.add(format.format(i))
        val adapter = ArrayAdapter(context, R.layout.spinner_item, list)
        adapter.setDropDownViewResource(layout.select_dialog_singlechoice)
        spinner.adapter = adapter
    }
}
