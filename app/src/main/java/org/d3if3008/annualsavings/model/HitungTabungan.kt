package org.d3if3008.annualsavings.model

import org.d3if3008.annualsavings.db.SaveEntity

fun SaveEntity.hitungTabungan(): HasilSave {
    val penghasilan = penghasilan
    val tabunganSetahun = tabungan*12
    val kategoriTabungan = if(isMahasiswa) {
        when {
            tabungan < penghasilan * 0.1 -> KategoriSave.TINGKATKAN
            tabungan > penghasilan * 0.3 -> KategoriSave.BERLEBIHAN
            else -> KategoriSave.CUKUP
        }
    } else {
        when {
            tabungan < penghasilan*0.2 -> KategoriSave.TINGKATKAN
            tabungan > penghasilan*0.4 -> KategoriSave.BERLEBIHAN
            else -> KategoriSave.CUKUP
        }
    }
    return HasilSave(tabunganSetahun,kategoriTabungan)
}