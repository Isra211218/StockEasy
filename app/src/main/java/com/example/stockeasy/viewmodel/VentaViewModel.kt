package com.example.stockeasy.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockeasy.data.StockEasyDatabase
import com.example.stockeasy.data.VentaEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VentaViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val db = StockEasyDatabase.getDatabase(application)
    private val ventaDao = db.ventaDao()
    private val productoDao = db.productoDao()
    private val listaDao = db.listaDao()

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
        val lista = listaDao.buscarPorNombre(nombreLista)
        if (lista != null) {
            val productos = productoDao.obtenerProductosDeLista(lista.id)
            _productosDeLista.value = productos.map { it.nombre }
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

        val lista = listaDao.buscarPorNombre(nombreLista)
        if (lista == null) {
            Toast.makeText(context, "Lista no encontrada", Toast.LENGTH_SHORT).show()
            return@launch
        }

        val productoEnLista = productoDao.buscarPorNombreEnLista(nombreProducto, lista.id)
        if (productoEnLista == null) {
            Toast.makeText(context, "Producto no encontrado", Toast.LENGTH_SHORT).show()
            return@launch
        }

        if (productoEnLista.cantidad < cantidadVendida) {
            Toast.makeText(context, "No hay existencias suficientes", Toast.LENGTH_SHORT).show()
            return@launch
        }

        // Actualizar stock
        val nuevoStock = productoEnLista.cantidad - cantidadVendida
        productoDao.actualizarProducto(productoEnLista.copy(cantidad = nuevoStock))

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
