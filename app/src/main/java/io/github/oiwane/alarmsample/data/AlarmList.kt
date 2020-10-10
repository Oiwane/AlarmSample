package io.github.oiwane.alarmsample.data

class AlarmList: ArrayList<AlarmProperty>() {
    fun remove(id: Int) {
        for (property in this) {
            if (property.id == id)
                remove(property)
        }
    }

    fun findById(id: Int): AlarmProperty? {
        for (property in this) {
            if (property.id == id)
                return property
        }
        return null
    }
}