package com.wahyuu.newpickme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.wahyuu.newpickme.databinding.ItemHistoryBinding

class HistoryAdapter(
    private val historyList: List<History>,
    private val onItemClick: ((Int, View) -> Unit)? = null
) : RecyclerView.Adapter<HistoryAdapter.HistoriViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriViewHolder {
        return HistoriViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HistoriViewHolder, position: Int) {
        val data = historyList[position]
        holder.bind(data)
        holder.itemView.setOnClickListener { view ->
            onItemClick?.invoke(position, view)
        }
    }

    override fun getItemCount(): Int = historyList.size

    class HistoriViewHolder private constructor(private val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: History) {
            binding.tvInfoLokasiHistory.text = data.lokasi
            binding.tvInfoWaktuHistory.text = data.waktu
            binding.ivPicthHistory.setImageResource(data.gambarRes)
            binding.tvKetHistory.text = data.keterangan

            val color = getKeteranganColor(itemView.context, data.keterangan)

            binding.tvKetHistory.backgroundTintList = android.content.res.ColorStateList.valueOf(color)
            binding.tvKetHistory.setTextColor(ContextCompat.getColor(itemView.context, android.R.color.white))
        }

        private fun getKeteranganColor(context: Context, keterangan: String): Int {
            return when (keterangan.lowercase()) {
                "selesai" -> ContextCompat.getColor(context, android.R.color.holo_green_dark)
                "berlangsung" -> ContextCompat.getColor(context, android.R.color.holo_orange_light)
                "dibatalkan" -> ContextCompat.getColor(context, android.R.color.holo_red_dark)
                else -> ContextCompat.getColor(context, android.R.color.darker_gray)
            }
        }

        companion object {
            fun from(parent: ViewGroup): HistoriViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHistoryBinding.inflate(layoutInflater, parent, false)
                return HistoriViewHolder(binding)
            }
        }
    }
}
