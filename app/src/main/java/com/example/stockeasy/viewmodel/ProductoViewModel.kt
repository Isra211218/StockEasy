package com.example.stockeasy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockeasy.data.ProductoEntity
import com.example.stockeasy.data.StockEasyDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProductoViewModel(application: Application) : AndroidViewModel(application) {
    private val productoDao = StockEasyDatabase.getDatabase(application).productoDao()

    private val _productos = MutableStateFlow<List<ProductoEntity>>(emptyList())
    val productos: StateFlow<List<ProductoEntity>> = _productos

    fun cargarProductos(listaId: Int) {
        viewModelScope.launch {
            _productos.value = productoDao.obtenerProductosDeLista(listaId)
        }
    }

    fun agregarProducto(
        nombre: String,
        cantidad: Int,
        imagenBase64: String,
        listaId: Int,
        onFinish: () -> Unit
    ) {
        viewModelScope.launch {
            val nombreSanitizado = nombre.trim().lowercase()
            val nuevoProducto = ProductoEntity(
                nombre = nombreSanitizado,
                cantidad = cantidad,
                imagenBase64 = imagenBase64,
                listaId = listaId
            )
            productoDao.insertarProducto(nuevoProducto)
            cargarProductos(listaId)
            onFinish()
        }
    }

    fun actualizarProducto(
        id: Int,
        nombre: String,
        cantidad: Int,
        imagenBase64: String,
        listaId: Int,
        onFinish: () -> Unit
    ) {
        viewModelScope.launch {
            val nombreSanitizado = nombre.trim().lowercase()
            val productoActualizado = ProductoEntity(
                id = id,
                nombre = nombreSanitizado,
                cantidad = cantidad,
                imagenBase64 = imagenBase64,
                listaId = listaId
            )
            productoDao.actualizarProducto(productoActualizado)
            cargarProductos(listaId)
            onFinish()
        }
    }
}
