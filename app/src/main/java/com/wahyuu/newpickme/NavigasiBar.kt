package com.wahyuu.newpickme

import android.app.Activity
import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class BarNavigasi(private val activity: Activity) {

    fun setupBar() {
        val home = activity.findViewById<LinearLayout>(R.id.homePage)
        val history = activity.findViewById<LinearLayout>(R.id.historyPage)
        val profile = activity.findViewById<LinearLayout>(R.id.profilePage)

        home.setOnClickListener {
            if (activity !is MainActivity) {
                // Pindah ke MainActivity
                activity.startActivity(Intent(activity, MainActivity::class.java))
                // Menonaktifkan animasi transisi (enter dan exit)
                activity.overridePendingTransition(0, 0)
            }
        }

        history.setOnClickListener {
            if (activity !is HistoryActivity) {
                // Pindah ke HistoryActivity
                activity.startActivity(Intent(activity, HistoryActivity::class.java))
                // Menonaktifkan animasi transisi (enter dan exit)
                activity.overridePendingTransition(0, 0)
            }
        }

        profile.setOnClickListener {
            if (activity !is ProfileActivity) {
                // Pindah ke ProfileActivity
                activity.startActivity(Intent(activity, ProfileActivity::class.java))
                // Menonaktifkan animasi transisi (enter dan exit)
                activity.overridePendingTransition(0, 0)
            }
        }
    }

    // Fungsi setActivePage tidak diubah karena sudah benar
    fun setActivePage(activeId: Int) {
        val defaultColor = ContextCompat.getColor(activity, android.R.color.black)
        val activeColor = ContextCompat.getColor(activity, R.color.soft_orange)

        val icons = listOf(
            activity.findViewById<ImageView>(R.id.iconHome),
            activity.findViewById<ImageView>(R.id.iconHistory),
            activity.findViewById<ImageView>(R.id.iconProfile)
        )

        val texts = listOf(
            activity.findViewById<TextView>(R.id.textHome),
            activity.findViewById<TextView>(R.id.textHistory),
            activity.findViewById<TextView>(R.id.textProfile)
        )

        // Reset warna
        icons.forEach { it.setColorFilter(defaultColor) }
        texts.forEach { it.setTextColor(defaultColor) }

        // Warna aktif
        when (activeId) {
            R.id.iconHome -> {
                activity.findViewById<ImageView>(R.id.iconHome).setColorFilter(activeColor)
                activity.findViewById<TextView>(R.id.textHome).setTextColor(activeColor)
            }
            R.id.iconHistory -> {
                activity.findViewById<ImageView>(R.id.iconHistory).setColorFilter(activeColor)
                activity.findViewById<TextView>(R.id.textHistory).setTextColor(activeColor)
            }
            R.id.iconProfile -> {
                activity.findViewById<ImageView>(R.id.iconProfile).setColorFilter(activeColor)
                activity.findViewById<TextView>(R.id.textProfile).setTextColor(activeColor)
            }
        }
    }
}