package com.example.stockeasy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ventas")
data class VentaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val producto: String,
    val cantidad: String,
    val fecha: String,
    val lista: String
)