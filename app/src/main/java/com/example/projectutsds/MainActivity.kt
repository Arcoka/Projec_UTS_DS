package com.example.projectutsds

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.projectutsds.adapters.DoaAdapter
import com.example.projectutsds.models.DoaItem
import com.example.projectutsds.models.DoaResponse
import com.example.projectutsds.network.ApiClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var searchView: SearchView
    private lateinit var layoutLoading: LinearLayout
    private lateinit var layoutError: LinearLayout
    private lateinit var textError: TextView
    private lateinit var buttonRetry: Button

    private lateinit var doaAdapter: DoaAdapter
    private var originalDoaList = mutableListOf<DoaItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Tambahkan baris ini
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupSearchView()
        setupSwipeRefresh()
        setupRetryButton()

        loadDoaData()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        searchView = findViewById(R.id.searchView)
        layoutLoading = findViewById(R.id.layoutLoading)
        layoutError = findViewById(R.id.layoutError)
        textError = findViewById(R.id.textError)
        buttonRetry = findViewById(R.id.buttonRetry)
    }

    private fun setupRecyclerView() {
        doaAdapter = DoaAdapter(originalDoaList) { doaItem ->
            val intent = Intent(this, DetailDoaActivity::class.java).apply {
                putExtra("doa_id", doaItem.id)
                putExtra("doa_name", doaItem.doa)
                putExtra("doa_arabic", doaItem.ayat)
                putExtra("doa_latin", doaItem.latin)
                putExtra("doa_translation", doaItem.artinya)
            }
            startActivity(intent)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = doaAdapter
        }
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterDoa(newText ?: "")
                return true
            }
        })
    }

    private fun setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            loadDoaData()
        }
    }

    private fun setupRetryButton() {
        buttonRetry.setOnClickListener {
            loadDoaData()
        }
    }

    private fun loadDoaData() {
        showLoading()

        lifecycleScope.launch {
            try {
                val response = ApiClient.apiService.getAllDoa()
                if (response.isSuccessful && response.body() != null) {
                    val doaList = response.body()!!
                    if (doaList.isNotEmpty()) {
                        originalDoaList.clear()
                        originalDoaList.addAll(doaList)
                        doaAdapter.updateData(originalDoaList)
                        showContent()
                    } else {
                        showError("Data tidak ditemukan")
                    }
                } else {
                    showError("Gagal memuat data: ${response.code()}")
                }

            } catch (e: Exception) {
                showError("Error: ${e.message}")
            }

            swipeRefresh.isRefreshing = false
        }
    }

    private fun filterDoa(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalDoaList
        } else {
            originalDoaList.filter { doa ->
                doa.doa.contains(query, ignoreCase = true)
            }
        }
        doaAdapter.updateData(filteredList)
    }

    private fun showLoading() {
        layoutLoading.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        layoutError.visibility = View.GONE
    }

    private fun showContent() {
        layoutLoading.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        layoutError.visibility = View.GONE
    }

    private fun showError(message: String) {
        layoutLoading.visibility = View.GONE
        recyclerView.visibility = View.GONE
        layoutError.visibility = View.VISIBLE
        textError.text = message

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}