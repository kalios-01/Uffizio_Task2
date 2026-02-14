package com.kaliostech.uffizio_task2.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaliostech.uffizio_task2.data.VehicleData
import com.kaliostech.uffizio_task2.data.VehicleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Loading : UiState()
    data class Success(val vehicles: List<VehicleData>) : UiState()
    data class Error(val message: String) : UiState()
}

class VehicleViewModel(
    private val repository: VehicleRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    // Store all vehicles for filtering
    private var allVehicles: List<VehicleData> = emptyList()
    
    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    // API parameters - can be updated via methods
    private var currentProjectId = 37
    private var currentUserId = 72821
    private var currentVehicleIds = "484731,610258,610249,610266,610244"
    private var currentFromDate = "12-12-2025 00:00:00"
    private var currentToDate = "12-12-2025 15:04:59"
    
    init {
        // Load data on initialization
        loadVehicles()
    }
    
    fun loadVehicles() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            
            val result = repository.getTravelSummary(
                projectId = currentProjectId,
                fromDate = currentFromDate,
                toDate = currentToDate,
                userId = currentUserId,
                vehicleId = currentVehicleIds
            )
            
            result.fold(
                onSuccess = { vehicles ->
                    allVehicles = vehicles
                    applySearchFilter()
                },
                onFailure = { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "Unknown error occurred")
                }
            )
        }
    }
    
    fun searchVehicles(query: String) {
        _searchQuery.value = query
        applySearchFilter()
    }
    
    private fun applySearchFilter() {
        val query = _searchQuery.value.trim()
        val filteredVehicles = if (query.isEmpty()) {
            allVehicles
        } else {
            allVehicles.filter { vehicle ->
                vehicle.vehicleNumber.contains(query, ignoreCase = true)
            }
        }
        _uiState.value = UiState.Success(filteredVehicles)
    }
    
    fun updateDateRange(fromDate: String, toDate: String) {
        currentFromDate = fromDate
        currentToDate = toDate
        loadVehicles()
    }
    
    fun updateVehicleIds(vehicleIds: String) {
        currentVehicleIds = vehicleIds
        loadVehicles()
    }
    
    fun retry() {
        loadVehicles()
    }
}
