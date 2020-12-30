package io.github.oiwane.alarmsample.alarm

class AlarmList: ArrayList<AlarmProperty>() {
    fun set(newProperty: AlarmProperty): Boolean {
        for (property in this) {
            if (property.id == newProperty.id) {
                property.setProperty(newProperty)
                return true
            }
        }
        return false
    }

    fun remove(id: String): Int? {
        for (property in this) {
            if (property.id == id) {
                this.remove(property)
                return indexOf(property)
            }
        }
        return null
    }

    fun findById(id: String): AlarmProperty? {
        for (property in this) {
            if (property.id == id)
                return property
        }
        return null
    }
}