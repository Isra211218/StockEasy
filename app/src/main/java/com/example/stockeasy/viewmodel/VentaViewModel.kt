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

    fun cargarVentas() {
        viewModelScope.launch {
            _ventas.value = ventaDao.obtenerTodas()
        }
    }

    fun agregarVenta(
        producto: String,
        cantidad: String,
        fecha: String,
        lista: String,
        listaId: Int,
        onFinish: () -> Unit
    ) {
        viewModelScope.launch {
            val nombreSanitizado = producto.trim().lowercase()
            val cantidadVendida = cantidad.toIntOrNull() ?: return@launch

            // Verificar cantidad válida
            if (cantidadVendida <= 0) return@launch

            // Buscar el producto correspondiente en la lista
            val productoExistente = productoDao.buscarPorNombreEnLista(nombreSanitizado, listaId)

            if (productoExistente != null) {
                // Validar si hay suficiente stock
                val stockActual = productoExistente.cantidad
                val nuevoStock = (stockActual - cantidadVendida).coerceAtLeast(0)

                val productoActualizado = productoExistente.copy(cantidad = nuevoStock)
                productoDao.actualizarProducto(productoActualizado)
            }

            // Registrar la venta en la tabla de ventas
            ventaDao.insertar(
                VentaEntity(
                    producto = nombreSanitizado,
                    cantidad = cantidad,
                    fecha = fecha,
                    lista = lista
                )
            )

            cargarVentas()
            onFinish()
        }
    }
}