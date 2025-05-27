package com.example.stockeasy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockeasy.data.StockEasyDatabase
import com.example.stockeasy.data.UsuarioEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {
    private val usuarioDao = StockEasyDatabase.getDatabase(application).usuarioDao()

    private val _usuario = MutableStateFlow<UsuarioEntity?>(null)
    val usuario: StateFlow<UsuarioEntity?> = _usuario

    init {
        cargarUsuarioActual()
    }

    private fun cargarUsuarioActual() {
        viewModelScope.launch {
            _usuario.value = usuarioDao.obtenerUsuarioActivo()
        }
    }

    fun actualizarContrasena(nuevaContrasena: String) {
        viewModelScope.launch {
            _usuario.value?.let {
                val actualizado = it.copy(contrasena = nuevaContrasena)
                usuarioDao.actualizarUsuario(actualizado)
                _usuario.value = actualizado
            }
        }
    }

    fun registrarUsuario(
        nombre: String,
        correo: String,
        contrasena: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val existente = usuarioDao.buscarPorCorreo(correo)
            if (existente == null) {
                val nuevo = UsuarioEntity(nombre = nombre, correo = correo, contrasena = contrasena, activo = true)
                usuarioDao.insertarUsuario(nuevo)
                _usuario.value = nuevo
                onSuccess()
            } else {
                onError("Este correo ya está registrado.")
            }
        }
    }

    fun iniciarSesion(
        correo: String,
        contrasena: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            val usuario = usuarioDao.autenticar(correo, contrasena)
            if (usuario != null) {
                usuarioDao.desactivarTodos()
                usuarioDao.actualizarUsuario(usuario.copy(activo = true))
                _usuario.value = usuario.copy(activo = true)
                onSuccess()
            } else {
                onError("Correo o contraseña incorrectos.")
            }
        }
    }
}
