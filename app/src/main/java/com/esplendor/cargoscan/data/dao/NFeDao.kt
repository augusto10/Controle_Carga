package com.esplendor.cargoscan.data.dao

import androidx.room.*
import com.esplendor.cargoscan.data.entities.NFe
import kotlinx.coroutines.flow.Flow

@Dao
interface NFeDao {
    @Query("SELECT * FROM nfe")
    fun listarTodasNFes(): Flow<List<NFe>>

    @Query("SELECT * FROM nfe WHERE status = :status")
    fun listarNFesPorStatus(status: String): Flow<List<NFe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirNFe(nfe: NFe)

    @Update
    suspend fun atualizarNFe(nfe: NFe)

    @Delete
    suspend fun deletarNFe(nfe: NFe)
}
