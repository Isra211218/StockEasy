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
    private val ventaDao   = db.ventaDao()
    private val productoDao = db.productoDao()
    private val listaDao    = db.listaDao()

    private val _productosDeLista = MutableStateFlow<List<String>>(emptyList())
    val productosDeLista: StateFlow<List<String>> = _productosDeLista

    private val _ventas = MutableStateFlow<List<VentaEntity>>(emptyList())
    val ventas: StateFlow<List<VentaEntity>> = _ventas

    private val _nombresListas = MutableStateFlow<List<String>>(emptyList())
    val nombresListas: StateFlow<List<String>> = _nombresListas

    fun cargarNombresListas() = viewModelScope.launch {
        _nombresListas.value = listaDao.obtenerNombresListas()
    }

    fun cargarVentas() = viewModelScope.launch {
        _ventas.value = ventaDao.obtenerTodas()
    }

    fun cargarProductosDeLista(nombreLista: String, onListaIdObtenido: (Int) -> Unit) = viewModelScope.launch {
        val lista = listaDao.buscarPorNombre(nombreLista.lowercase().trim())
        if (lista != null) {
            _productosDeLista.value = productoDao.obtenerProductosDeLista(lista.id).map { it.nombre }
            onListaIdObtenido(lista.id)
        } else {
            _productosDeLista.value = emptyList()
            onListaIdObtenido(0)
        }
    }

    fun agregarVenta(
        producto: String,
        cantidad: String,
        fecha: String,
        nombreLista: String,
        listaId: Int,
        onFinish: () -> Unit
    ) = viewModelScope.launch {
        val nombreProducto = producto.trim().lowercase()
        val nombreListaSanitizado = nombreLista.trim().lowercase()
        val cantidadVendida = cantidad.toIntOrNull() ?: return@launch
        if (cantidadVendida <= 0) return@launch

        // Actualizar stock del producto
        listaDao.buscarPorNombre(nombreListaSanitizado)?.let { lista ->
            productoDao.buscarPorNombreEnLista(nombreProducto, lista.id)?.let { prod ->
                val nuevoStock = (prod.cantidad - cantidadVendida).coerceAtLeast(0)
                productoDao.actualizarProducto(prod.copy(cantidad = nuevoStock))
            }
        }

        // Insertar venta
        ventaDao.insertar(
            VentaEntity(
                producto = nombreProducto,
                cantidad = cantidadVendida.toString(),
                fecha = fecha,
                lista = nombreListaSanitizado,
                listaId = listaId
            )
        )

        cargarVentas()
        onFinish()
    }
}
