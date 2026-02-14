package com.kaliostech.uffizio_task2.ui.screens

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kaliostech.uffizio_task2.R
import com.kaliostech.uffizio_task2.data.VehicleRepository
import com.kaliostech.uffizio_task2.ui.adapters.VehicleCardAdapter
import com.kaliostech.uffizio_task2.ui.viewmodels.UiState
import com.kaliostech.uffizio_task2.ui.viewmodels.VehicleViewModel
import com.kaliostech.uffizio_task2.ui.viewmodels.VehicleViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VehicleCardAdapter
    private lateinit var progressBar: View
    
    private val viewModel: VehicleViewModel by viewModels {
        VehicleViewModelFactory(VehicleRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.Companion.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupToolbar()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewVehicles)
        recyclerView.layoutManager = LinearLayoutManager(this)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupToolbar() {
        val btnSearch = findViewById<ImageButton>(R.id.btnSearch)
        val btnCloseSearch = findViewById<ImageButton>(R.id.btnCloseSearch)
        val etSearchQuery = findViewById<EditText>(R.id.etSearchQuery)
        val tvTitle = findViewById<TextView>(R.id.tvTitle)
        val btnFilter = findViewById<ImageButton>(R.id.btnFilter)
        val btnMenu = findViewById<ImageButton>(R.id.btnMenu)

        btnSearch.setOnClickListener {
            tvTitle.visibility = View.GONE
            btnSearch.visibility = View.GONE
            btnFilter.visibility = View.GONE
            btnMenu.visibility = View.GONE

            etSearchQuery.visibility = View.VISIBLE
            btnCloseSearch.visibility = View.VISIBLE

            etSearchQuery.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(etSearchQuery, InputMethodManager.SHOW_IMPLICIT)
        }

        btnCloseSearch.setOnClickListener {
            if (etSearchQuery.text.isNotEmpty()) {
                etSearchQuery.text.clear()
            } else {
                tvTitle.visibility = View.VISIBLE
                btnSearch.visibility = View.VISIBLE
                btnFilter.visibility = View.VISIBLE
                btnMenu.visibility = View.VISIBLE

                etSearchQuery.visibility = View.GONE
                btnCloseSearch.visibility = View.GONE

                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(etSearchQuery.windowToken, 0)
                
                // Clear search when closing
                viewModel.searchVehicles("")
            }
        }
        
        // Add text change listener for real-time search
        etSearchQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchVehicles(s?.toString() ?: "")
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            // Show loading indicator
                            progressBar.visibility = View.VISIBLE
                            recyclerView.visibility = View.GONE
                        }
                        
                        is UiState.Success -> {
                            // Hide loading indicator
                            progressBar.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                            adapter = VehicleCardAdapter(state.vehicles)
                            recyclerView.adapter = adapter
                        }
                        
                        is UiState.Error -> {
                            // Hide loading indicator
                            progressBar.visibility = View.GONE
                            recyclerView.visibility = View.VISIBLE
                            Toast.makeText(
                                this@MainActivity,
                                "Error: ${state.message}",
                                Toast.LENGTH_LONG
                            ).show()
                            
                            // Show empty state or retry button
                            adapter = VehicleCardAdapter(emptyList())
                            recyclerView.adapter = adapter
                        }
                    }
                }
            }
        }
    }
}