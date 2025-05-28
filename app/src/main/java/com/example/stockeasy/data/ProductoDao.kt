package com.example.stockeasy.data

import androidx.room.*

@Dao
interface ProductoDao {
    @Query("SELECT * FROM productos WHERE listaId = :listaId")
    suspend fun obtenerProductosDeLista(listaId: Int): List<ProductoEntity>

    @Insert
    suspend fun insertarProducto(producto: ProductoEntity): Long

    @Update
    suspend fun actualizarProducto(producto: ProductoEntity)

    @Delete
    suspend fun eliminarProducto(producto: ProductoEntity)

    @Query("SELECT * FROM productos WHERE LOWER(nombre) = LOWER(:nombre) AND listaId = :listaId LIMIT 1")
    suspend fun buscarPorNombreEnLista(nombre: String, listaId: Int): ProductoEntity?

    @Query("SELECT * FROM productos WHERE id = :id LIMIT 1")
    suspend fun obtenerProductoPorId(id: Int): ProductoEntity?

}