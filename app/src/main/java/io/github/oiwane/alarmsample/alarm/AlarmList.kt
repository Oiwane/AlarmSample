package io.github.oiwane.alarmsample.alarm

class AlarmList: ArrayList<AlarmProperty>() {
    fun set(newProperty: AlarmProperty): Boolean {
        val property = find { it.id == newProperty.id } ?: return false
        property.setProperty(newProperty)
        return true
    }

    fun remove(id: String): Int? {
        val property = find { it.id == id } ?: return null
        val index = indexOf(property)
        super.remove(property)
        return index
    }
}