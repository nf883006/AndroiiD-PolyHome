package com.farikou.polyhome

data class House(val houseId: Int, val owner: Boolean)
data class Device(val id: String, val type: String, val availableCommands: List<String>, val opening: Int? = null, val power: Int? = null)
data class DeviceResponse(val devices: List<Device>)
data class AuthResponse(val token: String)
