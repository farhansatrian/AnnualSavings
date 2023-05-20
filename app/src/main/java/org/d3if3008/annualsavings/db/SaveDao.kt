package org.d3if3008.annualsavings.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SaveDao {

    @Insert
    fun insert(savings: SaveEntity)

    @Query("SELECT * FROM savings ORDER BY id DESC")
    fun getLastSavings(): LiveData<List<SaveEntity>>

    @Query("DELETE FROM savings")
    fun clearData()
}