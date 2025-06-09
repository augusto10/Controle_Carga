package com.esplendor.cargoscan.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.esplendor.cargoscan.data.dao.ControlDao
import com.esplendor.cargoscan.data.dao.NFeDao
import com.esplendor.cargoscan.data.entities.Control
import com.esplendor.cargoscan.data.entities.NFe

@Database(entities = [NFe::class, Control::class], version = 1, exportSchema = false)
abstract class CargoScanDatabase : RoomDatabase() {
    abstract fun nfeDao(): NFeDao
    abstract fun controlDao(): ControlDao

    companion object {
        @Volatile
        private var INSTANCE: CargoScanDatabase? = null

        fun getDatabase(context: Context): CargoScanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CargoScanDatabase::class.java,
                    "cargo_scan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
