package com.wahyuu.newpickme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.wahyuu.newpickme.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- Navigasi Bar
        val barNavigasi = BarNavigasi(this)
        barNavigasi.setupBar()
        barNavigasi.setActivePage(R.id.iconHistory)

        // --- Data History
        val historyList = listOf(
            History(R.drawable.malioboro, "Jl. Malioboro, Yogyakarta", "Senin, 6 Nov 2025", "Selesai"),
            History(R.drawable.tomoro, "Jl. Kaliurang KM 5", "Selasa, 7 Nov 2025", "Dibatalkan"),
            History(R.drawable.janjijiwa, "Jl. Gejayan", "Rabu, 8 Nov 2025", "Berlangsung")
        )

        // --- Adapter
        val adapter = HistoryAdapter(historyList)
        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerHistory.adapter = adapter
    }
}
