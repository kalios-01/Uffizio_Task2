package com.kaliostech.uffizio_task2.data

import com.kaliostech.uffizio_task2.data.VehicleData
import com.kaliostech.uffizio_task2.data.RetrofitClient
import com.kaliostech.uffizio_task2.data.VehicleDataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VehicleRepository {
    
    private val apiService = RetrofitClient.apiService
    
    suspend fun getTravelSummary(
        projectId: Int,
        fromDate: String,
        toDate: String,
        userId: Int,
        vehicleId: String
    ): Result<List<VehicleData>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getTravelSummary(
                projectId = projectId,
                projectIdAlt = projectId,
                fromDate = fromDate,
                toDate = toDate,
                userId = userId,
                userIdAlt = userId,
                vehicleId = vehicleId
            )
            
            if (response.result == "SUCCESS") {
                val vehicles = VehicleDataMapper.mapToVehicleDataList(response)
                Result.success(vehicles)
            } else {
                Result.failure(Exception("API returned non-success result: ${response.result}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
