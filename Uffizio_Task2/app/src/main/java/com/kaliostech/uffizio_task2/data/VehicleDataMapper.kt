package com.kaliostech.uffizio_task2.data

import com.kaliostech.uffizio_task2.data.VehicleData
import kotlin.math.roundToInt

object VehicleDataMapper {

    fun mapToVehicleData(apiData: VehicleApiData): VehicleData {
        return VehicleData(
            vehicleNumber = apiData.vehicleNumber,
            distance = apiData.runningDistance.roundToInt(),
            runningTime = formatTime(apiData.runningTime),
            stopTime = formatTime(apiData.stopTime),
            idleTime = formatTime(apiData.idleTime),
            inactiveTime = formatTime(apiData.inactiveTime),
            startNumber = formatOdometer(apiData.startOdometer),
            startLocation = formatLocation(apiData.startLocation),
            endNumber = formatOdometer(apiData.endOdometer),
            endLocation = formatLocation(apiData.endLocation),
            avgSpeed = extractSpeed(apiData.avgSpeed),
            maxSpeed = extractSpeed(apiData.maxSpeed),
            driverName = formatDriverName(apiData.driver)
        )
    }

    fun mapToVehicleDataList(response: TravelSummaryResponse): List<VehicleData> {
        return response.data.map { mapToVehicleData(it) }
    }

    private fun formatTime(time: String): String {
        // API returns format like "02:20:45", convert to "02:20 hrs"
        if (time.isEmpty() || time == "00:00:00") return "00:00 hrs"

        val parts = time.split(":")
        if (parts.size >= 2) {
            return "${parts[0]}:${parts[1]} hrs"
        }
        return time
    }

    private fun formatOdometer(odometer: Double): String {
        // Convert 5241.74 to "5 2 4 1 . 7" format with spaces
        val odometerStr = String.format("%.1f", odometer)
        return odometerStr.map { it.toString() }.joinToString(" ")
    }

    private fun formatLocation(location: String): String {
        // Clean up location string, handle "--" or empty cases
        return when {
            location.isEmpty() || location == "--" -> "Location not available"
            location.length > 60 -> location.take(60) + "..."
            else -> location
        }
    }

    private fun extractSpeed(speedStr: String): Int {
        // Extract number from "10 km/h" format
        return try {
            speedStr.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun formatDriverName(driver: String): String {
        return if (driver.isEmpty() || driver == "No Driver Found") {
            "No Driver"
        } else {
            driver
        }
    }
}