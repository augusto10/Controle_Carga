package com.esplendor.cargoscan.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "control")
data class Control(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dataCriacao: Date = Date(),
    val motoristaNome: String,
    val motoristaCpf: String,
    val responsavelNome: String,
    val responsavelCpf: String,
    val status: String = "Aberto"
)
