package com.kaliostech.uffizio_task2.data

data class VehicleData(
    val vehicleNumber: String,
    val distance: Int,
    val runningTime: String,
    val stopTime: String,
    val idleTime: String,
    val inactiveTime: String,
    val startNumber: String,
    val startLocation: String,
    val endNumber: String,
    val endLocation: String,
    val avgSpeed: Int,
    val maxSpeed: Int,
    val driverName: String
)