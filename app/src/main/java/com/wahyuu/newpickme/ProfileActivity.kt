package com.wahyuu.newpickme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wahyuu.newpickme.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data user dari SharedPreferences
        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val userJson = sharedPref.getString("user", null)

        // Setup Bottom Navigation
        val barNavigasi = BarNavigasi(this)
        barNavigasi.setupBar()
        barNavigasi.setActivePage(R.id.iconProfile)

        // ============================================
        // LOGOUT BUTTON
        // ============================================
        binding.btnLogOut.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        val shared = getSharedPreferences("user_data", MODE_PRIVATE)

        // Hapus SEMUA data user: user + isLoggedIn + userName
        shared.edit().clear().apply()

        // Pindah ke LoginActivity dan hapus semua history
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
