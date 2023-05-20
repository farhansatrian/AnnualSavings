package org.d3if3008.annualsavings.ui.histori

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if3008.annualsavings.R
import org.d3if3008.annualsavings.databinding.ItemHistoriBinding
import org.d3if3008.annualsavings.db.SaveEntity
import org.d3if3008.annualsavings.model.KategoriSave
import org.d3if3008.annualsavings.model.hitungTabungan
import java.text.SimpleDateFormat
import java.util.*

class HistoriAdapter : ListAdapter<SaveEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<SaveEntity>() {
                override fun areItemsTheSame(
                    oldData: SaveEntity, newData: SaveEntity
                ): Boolean {
                    return oldData.id == newData.id
                }
                override fun areContentsTheSame(
                    oldData: SaveEntity, newData: SaveEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )
        fun bind(item: SaveEntity) = with(binding) {
            val hasilTabungan = item.hitungTabungan()
            kategoriTextView.text = hasilTabungan.kategori.toString().substring(0, 1)
            val colorRes = when(hasilTabungan.kategori) {
                KategoriSave.TINGKATKAN -> R.color.tingkatkan
                KategoriSave.CUKUP -> R.color.cukup
                else -> R.color.berlebihan
            }
            20
            val circleBg = kategoriTextView.background as GradientDrawable
            circleBg.setColor(ContextCompat.getColor(root.context, colorRes))
            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            saveTextView.text = root.context.getString(R.string.hasil_x,
                hasilTabungan.save, hasilTabungan.kategori.toString())
            val gender = root.context.getString(
                if (item.isMahasiswa) R.string.mahasiswa else R.string.bekerja)
            dataTextView.text = root.context.getString(R.string.data_x,
                item.penghasilan, item.tabungan, gender)
        }
    }
}