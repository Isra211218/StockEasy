package com.example.stockeasy.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.stockeasy.data.VentaEntity

@Dao
interface VentaDao {
    @Insert
    suspend fun insertar(venta: VentaEntity)

    @Query("SELECT * FROM ventas ORDER BY fecha DESC")
    suspend fun obtenerTodas(): List<VentaEntity>

}
