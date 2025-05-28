package com.example.stockeasy.data

import androidx.room.*

@Dao
interface ListaDao {
    @Query("SELECT * FROM listas")
    suspend fun obtenerListas(): List<ListaEntity>

    @Query("SELECT * FROM listas WHERE LOWER(nombre) = LOWER(:nombre) LIMIT 1")
    suspend fun buscarPorNombre(nombre: String): ListaEntity?


    @Insert
    suspend fun insertarLista(lista: ListaEntity): Long

    @Query("SELECT nombre FROM listas")
    suspend fun obtenerNombresListas(): List<String>

    @Query("DELETE FROM listas WHERE id = :listaId")
    suspend fun eliminarListaPorId(listaId: Int)

}