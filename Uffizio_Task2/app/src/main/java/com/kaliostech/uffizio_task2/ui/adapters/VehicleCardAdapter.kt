package com.kaliostech.uffizio_task2.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kaliostech.uffizio_task2.R
import com.kaliostech.uffizio_task2.data.VehicleData

class VehicleCardAdapter(
    private val vehicles: List<VehicleData>
) : RecyclerView.Adapter<VehicleCardAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Vehicle header
        val tvVehicleNumber: TextView = view.findViewById(R.id.tvVehicleNumber)
        val tvDistance: TextView = view.findViewById(R.id.tvDistance)

        // Status indicators
        val tvStatusRunningTime: TextView = view.findViewById(R.id.tvStatusRunningTime)
        val tvStatusStopTime: TextView = view.findViewById(R.id.tvStatusStopTime)
        val tvStatusIdleTime: TextView = view.findViewById(R.id.tvStatusIdleTime)
        val tvStatusInactiveTime: TextView = view.findViewById(R.id.tvStatusInactiveTime)

        // Location
        val tvStartNumber: TextView = view.findViewById(R.id.tvStartNumber)
        val tvStartLocation: TextView = view.findViewById(R.id.tvStartLocation)
        val tvEndNumber: TextView = view.findViewById(R.id.tvEndNumber)
        val tvEndLocation: TextView = view.findViewById(R.id.tvEndLocation)

        // Speed and driver
        val tvAvgSpeed: TextView = view.findViewById(R.id.tvAvgSpeed)
        val tvMaxSpeed: TextView = view.findViewById(R.id.tvMaxSpeed)
        val tvDriverName: TextView = view.findViewById(R.id.tvDriverName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vehicle_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val vehicle = vehicles[position]

        holder.tvVehicleNumber.text = vehicle.vehicleNumber
        holder.tvDistance.text = "${vehicle.distance} km"

        holder.tvStatusRunningTime.text = vehicle.runningTime
        holder.tvStatusStopTime.text = vehicle.stopTime
        holder.tvStatusIdleTime.text = vehicle.idleTime
        holder.tvStatusInactiveTime.text = vehicle.inactiveTime

        holder.tvStartNumber.text = vehicle.startNumber
        holder.tvStartLocation.text = vehicle.startLocation
        holder.tvEndNumber.text = vehicle.endNumber
        holder.tvEndLocation.text = vehicle.endLocation

        holder.tvAvgSpeed.text = "${vehicle.avgSpeed} km/h"
        holder.tvMaxSpeed.text = "${vehicle.maxSpeed} km/h"
        holder.tvDriverName.text = vehicle.driverName
    }

    override fun getItemCount() = vehicles.size
}