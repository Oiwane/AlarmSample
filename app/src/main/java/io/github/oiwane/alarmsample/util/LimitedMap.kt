package io.github.oiwane.alarmsample.util

import io.github.oiwane.alarmsample.alarm.AlarmList

class LimitedMap(private val max: Int): HashMap<String, Int>() {

    override fun put(key: String, value: Int): Int? {
        return if (size < max)
            super.put(key, value)
        else
            -1
    }

    /**
     * 使用していない値を削除する
     *
     * @param alarmList アラーム情報リスト
     */
    fun removeUnusedValue(alarmList: AlarmList) {
        val newMap = LimitedMap(max)
        alarmList.filter { keys.contains(it.id) }.forEach { newMap[it.id] = get(it.id)!! }
        clear()
        putAll(newMap)
    }
}