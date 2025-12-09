package com.wahyuu.newpickme

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.wahyuu.newpickme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPref.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            Log.d(TAG, "❌ User belum login → pindah ke LoginActivity")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // Jika sudah login → tampilkan MainActivity
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userJson = sharedPref.getString("user", null)
        if (userJson != null) {
            val user = Gson().fromJson(userJson, User::class.java)
            binding.tvInfoNotif.text = "Hi, ${user.name}!"
        }


        Log.d(TAG, "🔵 MainActivity: halaman HOME ditampilkan")

        // Menyesuaikan padding untuk status bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val barNavigasi = BarNavigasi(this)
        barNavigasi.setupBar()
        barNavigasi.setActivePage(R.id.iconHome)

        binding.tvMauKemana.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}
