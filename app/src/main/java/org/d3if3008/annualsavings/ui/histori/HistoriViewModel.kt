package org.d3if3008.annualsavings.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3008.annualsavings.db.SaveDao

class HistoriViewModel(private val db: SaveDao) : ViewModel() {
    val data = db.getLastSavings()

    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }
}