package com.example.stockeasy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockeasy.data.ListaEntity
import com.example.stockeasy.data.StockEasyDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListaViewModel(application: Application) : AndroidViewModel(application) {
    private val listaDao = StockEasyDatabase.getDatabase(application).listaDao()

    private val _listas = MutableStateFlow<List<ListaEntity>>(emptyList())
    val listas: StateFlow<List<ListaEntity>> = _listas

    init {
        cargarListas()
    }

    fun cargarListas() {
        viewModelScope.launch {
            _listas.value = listaDao.obtenerListas()
        }
    }

    fun agregarLista(nombre: String, descripcion: String, onFinish: (Int) -> Unit) {
        viewModelScope.launch {
            val listaId = listaDao.insertarLista(ListaEntity(nombre = nombre, descripcion = descripcion)).toInt()
            cargarListas()
            onFinish(listaId)
        }
    }

    fun eliminarLista(listaId: Int, onFinish: () -> Unit) {
        viewModelScope.launch {
            listaDao.eliminarListaPorId(listaId)
            cargarListas()
            onFinish()
        }
    }

}