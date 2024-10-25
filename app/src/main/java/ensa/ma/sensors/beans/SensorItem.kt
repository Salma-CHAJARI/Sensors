package ensa.ma.sensors.beans

class SensorItem(
    @JvmField val id: String,
    @JvmField val name: String,
    @JvmField val type: String,
    @JvmField val vendor: String,
    @JvmField val version: String
) {
    override fun toString(): String {
        return name
    }
}
