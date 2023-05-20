package org.d3if3008.annualsavings.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Savings")
data class SaveEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var penghasilan: Double,
    var tabungan: Double,
    var isMahasiswa: Boolean
)