package org.d3if3008.annualsavings.ui.hitung

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3008.annualsavings.db.SaveDao

class HitungViewModelFactory (private val db: SaveDao) : ViewModelProvider.Factory{
    @Suppress ("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HitungViewModel::class.java)) {
            return HitungViewModel(db) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}