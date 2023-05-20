package org.d3if3008.annualsavings.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3008.annualsavings.db.SaveDao
import org.d3if3008.annualsavings.db.SaveEntity
import org.d3if3008.annualsavings.model.HasilSave
import org.d3if3008.annualsavings.model.KategoriSave
import org.d3if3008.annualsavings.model.hitungTabungan

class HitungViewModel(private val db: SaveDao) : ViewModel() {

    private val hasilSave = MutableLiveData<HasilSave?>()
    private val navigasi = MutableLiveData<KategoriSave?>()
    val data = db.getLastSavings()

    fun hitungSave(penghasilan: Double, tabungan: Double, isMahasiswa: Boolean) {
        val dataSave = SaveEntity(
            penghasilan = penghasilan,
            tabungan = tabungan,
            isMahasiswa = isMahasiswa
        )
        hasilSave.value = dataSave.hitungTabungan()

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataSave)
            }
        }
    }

    fun getHasilSave(): LiveData<HasilSave?> = hasilSave

    fun mulaiNavigasi() {
        navigasi.value = hasilSave.value?.kategori
    }

    fun selesaiNavigasi(){
        navigasi.value = null
    }

    fun getNavigasi() : LiveData<KategoriSave?> = navigasi
}