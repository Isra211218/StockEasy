package com.example.stockeasy.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ListaEntity::class, ProductoEntity::class, VentaEntity::class],
    version = 1
)
abstract class RoomDatabaseRealForTest : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun listaDao(): ListaDao
    abstract fun ventaDao(): VentaDao
}
