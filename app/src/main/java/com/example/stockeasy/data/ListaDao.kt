package com.example.stockeasy.data

import androidx.room.*

@Dao
interface ListaDao {
    @Query("SELECT * FROM listas")
    suspend fun obtenerListas(): List<ListaEntity>

    @Insert
    suspend fun insertarLista(lista: ListaEntity): Long

}