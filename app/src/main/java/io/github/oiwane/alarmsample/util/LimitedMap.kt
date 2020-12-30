package io.github.oiwane.alarmsample.util

class LimitedMap(private val max: Int): HashMap<String, Int>() {

    override fun put(key: String, value: Int): Int? {
        return if (size < max)
            super.put(key, value)
        else
            -1
    }
}