package com.esplendor.cargoscan.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nfe")
data class NFe(
    @PrimaryKey val codigoBarras: String,
    val numeroNota: String,
    val serie: String,
    val dataEmissao: String,
    val valorTotal: Double,
    val destinatario: String,
    val status: String = "Pendente"
)
