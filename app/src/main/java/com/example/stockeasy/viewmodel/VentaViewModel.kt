package com.example.stockeasy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockeasy.data.StockEasyDatabase
import com.example.stockeasy.data.VentaEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VentaViewModel(application: Application) : AndroidViewModel(application) {
    private val db = StockEasyDatabase.getDatabase(application)
    private val ventaDao = db.ventaDao()
    private val productoDao = db.productoDao()

    private val _ventas = MutableStateFlow<List<VentaEntity>>(emptyList())
    val ventas: StateFlow<List<VentaEntity>> = _ventas
    private val listaDao = db.listaDao() // Asegúrate de tener esta línea

    private val _nombresListas = MutableStateFlow<List<String>>(emptyList())
    val nombresListas: StateFlow<List<String>> = _nombresListas

    fun cargarNombresListas() {
        viewModelScope.launch {
            _nombresListas.value = listaDao.obtenerNombresListas()
        }
    }

    fun cargarVentas() {
        viewModelScope.launch {
            _ventas.value = ventaDao.obtenerTodas()
        }
    }

    fun agregarVenta(
        producto: String,
        cantidad: String,
        fecha: String,
        nombreLista: String,
        onFinish: () -> Unit
    ) {
        viewModelScope.launch {
            val nombreProducto = producto.trim().lowercase()
            val nombreListaSanitizado = nombreLista.trim().lowercase()
            val cantidadVendida = cantidad.toIntOrNull() ?: return@launch

            if (cantidadVendida <= 0) return@launch

            // Buscar lista por nombre
            val listaConNombre = db.listaDao().buscarPorNombre(nombreListaSanitizado)

            if (listaConNombre != null) {
                val productoExistente = productoDao.buscarPorNombreEnLista(nombreProducto, listaConNombre.id)

                if (productoExistente != null) {
                    val stockActual = productoExistente.cantidad
                    val nuevoStock = (stockActual - cantidadVendida).coerceAtLeast(0)
                    val productoActualizado = productoExistente.copy(cantidad = nuevoStock)
                    productoDao.actualizarProducto(productoActualizado)
                }
            }

            // Registrar la venta con el nombre de la lista
            ventaDao.insertar(
                VentaEntity(
                    producto = nombreProducto,
                    cantidad = cantidad,
                    fecha = fecha,
                    lista = nombreListaSanitizado
                )
            )

            cargarVentas()
            onFinish()
        }
    }

}