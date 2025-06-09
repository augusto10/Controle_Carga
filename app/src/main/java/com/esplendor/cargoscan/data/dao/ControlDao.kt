package com.esplendor.cargoscan.data.dao

import androidx.room.*
import com.esplendor.cargoscan.data.entities.Control
import kotlinx.coroutines.flow.Flow

@Dao
interface ControlDao {
    @Query("SELECT * FROM control")
    fun listarTodosControles(): Flow<List<Control>>

    @Query("SELECT * FROM control WHERE status = :status")
    fun listarControlesPorStatus(status: String): Flow<List<Control>>

    @Insert
    suspend fun inserirControl(control: Control): Long

    @Update
    suspend fun atualizarControl(control: Control)

    @Delete
    suspend fun deletarControl(control: Control)
}
