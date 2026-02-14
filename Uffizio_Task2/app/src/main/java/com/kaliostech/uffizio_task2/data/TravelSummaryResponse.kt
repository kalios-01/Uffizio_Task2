package com.kaliostech.uffizio_task2.data

import com.google.gson.annotations.SerializedName

data class TravelSummaryResponse(
    @SerializedName("DATA")
    val data: List<VehicleApiData>,
    @SerializedName("RESULT")
    val result: String
)

data class VehicleApiData(
    @SerializedName("VEHICLE_NUMBER")
    val vehicleNumber: String,
    @SerializedName("VEHICLE_ID")
    val vehicleId: String,
    @SerializedName("RUNNINGDISTANCE")
    val runningDistance: Double,
    @SerializedName("RUNNINGTIME")
    val runningTime: String,
    @SerializedName("STOPTIME")
    val stopTime: String,
    @SerializedName("IDELTIME")
    val idleTime: String,
    @SerializedName("INACTIVETIME")
    val inactiveTime: String,
    @SerializedName("start_odometer")
    val startOdometer: Double,
    @SerializedName("end_odometer")
    val endOdometer: Double,
    @SerializedName("start_location")
    val startLocation: String,
    @SerializedName("end_location")
    val endLocation: String,
    @SerializedName("AVGSPEED")
    val avgSpeed: String,
    @SerializedName("MAXSPEED")
    val maxSpeed: String,
    @SerializedName("driver")
    val driver: String,
    @SerializedName("SPEEDUNIT")
    val speedUnit: String? = "km/h",
    @SerializedName("distance_unit")
    val distanceUnit: String? = "km"
)
