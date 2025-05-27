package com.example.stockeasy.data

import androidx.room.*

@Dao
interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarUsuario(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuario WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): UsuarioEntity?

    @Query("SELECT * FROM usuario WHERE correo = :correo AND contrasena = :contrasena LIMIT 1")
    suspend fun autenticar(correo: String, contrasena: String): UsuarioEntity?

    @Query("SELECT * FROM usuario WHERE activo = 1 LIMIT 1")
    suspend fun obtenerUsuarioActivo(): UsuarioEntity?

    @Query("UPDATE usuario SET activo = 0")
    suspend fun desactivarTodos()

    @Update
    suspend fun actualizarUsuario(usuario: UsuarioEntity)
}
