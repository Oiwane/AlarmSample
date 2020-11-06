package io.github.oiwane.alarmsample.data

class AlarmList: ArrayList<AlarmProperty>() {
    fun remove(id: String) {
        for (property in this) {
            if (property.id == id)
                remove(property)
        }
    }

    fun findById(id: String): AlarmProperty? {
        for (property in this) {
            if (property.id == id)
                return property
        }
        return null
    }
}