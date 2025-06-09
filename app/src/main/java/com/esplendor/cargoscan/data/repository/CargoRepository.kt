package com.esplendor.cargoscan.data.repository

import com.esplendor.cargoscan.data.dao.ControlDao
import com.esplendor.cargoscan.data.dao.NFeDao
import com.esplendor.cargoscan.data.entities.Control
import com.esplendor.cargoscan.data.entities.NFe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CargoRepository @Inject constructor(
    private val nfeDao: NFeDao,
    private val controlDao: ControlDao
) {
    // Notas Fiscais
    fun getAllNFe(): Flow<List<NFe>> = nfeDao.listarTodasNFes()
    
    fun getNFeByStatus(status: String): Flow<List<NFe>> = nfeDao.listarNFesPorStatus(status)
    
    suspend fun insertNFe(nfe: NFe) = nfeDao.inserirNFe(nfe)
    
    suspend fun updateNFe(nfe: NFe) = nfeDao.atualizarNFe(nfe)
    
    suspend fun deleteNFe(nfe: NFe) = nfeDao.deletarNFe(nfe)

    // Controles
    fun getAllControls(): Flow<List<Control>> = controlDao.listarTodosControles()
    
    fun getControlsByStatus(status: String): Flow<List<Control>> = controlDao.listarControlesPorStatus(status)
    
    suspend fun insertControl(control: Control): Long = controlDao.inserirControl(control)
    
    suspend fun updateControl(control: Control) = controlDao.atualizarControl(control)
    
    suspend fun deleteControl(control: Control) = controlDao.deletarControl(control)

    // Relacionamento entre NFe e Control
    suspend fun associateNFeWithControl(nfeCodigo: String, controlId: Long) {
        val nfe = nfeDao.listarTodasNFes().firstOrNull { it.codigoBarras == nfeCodigo }
        val control = controlDao.listarTodosControles().firstOrNull { it.id == controlId }
        
        if (nfe != null && control != null) {
            nfe.status = "Associada"
            updateNFe(nfe)
        }
    }

    fun getNFesByControl(controlId: Long): Flow<List<NFe>> =
        getAllNFe().map { nfes ->
            nfes.filter { it.status == "Associada" }
        }
}
