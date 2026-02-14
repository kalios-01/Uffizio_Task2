package com.kaliostech.uffizio_task2.data

import retrofit2.http.GET
import retrofit2.http.Query

interface TrakzeeApiService {

    @GET("mobileservice")
    suspend fun getTravelSummary(
        @Query("Action") action: String = "Filter",
        @Query("EntityId") entityId: Int = 0,
        @Query("ProjectId") projectId: Int,
        @Query("ScreenId") screenId: Int = 1228,
        @Query("ScreenType") screenType: String = "Overview",
        @Query("SubAction") subAction: String = "",
        @Query("distance_filter_condition") distanceFilterCondition: Int = 2,
        @Query("distance_filter_value") distanceFilterValue: String = "",
        @Query("fromDate") fromDate: String,
        @Query("method") method: String = "getTravelSummary",
        @Query("project_id") projectIdAlt: Int,
        @Query("toDate") toDate: String,
        @Query("userId") userId: Int,
        @Query("user_id") userIdAlt: Int,
        @Query("vehicleId") vehicleId: String
    ): TravelSummaryResponse
}