package com.example.stockeasy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UsuarioEntity::class, ListaEntity::class, ProductoEntity::class, VentaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StockEasyDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun listaDao(): ListaDao
    abstract fun productoDao(): ProductoDao
    abstract fun ventaDao(): VentaDao

    companion object {
        @Volatile
        private var INSTANCE: StockEasyDatabase? = null

        fun getDatabase(context: Context): StockEasyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StockEasyDatabase::class.java,
                    "stockeasy_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
