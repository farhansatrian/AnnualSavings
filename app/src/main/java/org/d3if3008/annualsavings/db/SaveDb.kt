package org.d3if3008.annualsavings.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SaveEntity::class], version = 1, exportSchema = false)
abstract class SaveDb : RoomDatabase() {
    abstract val dao: SaveDao

    companion object {
        @Volatile
        private var INSTANCE: SaveDb? = null

        fun getInstance(context: Context): SaveDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SaveDb::class.java,
                        "save.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}