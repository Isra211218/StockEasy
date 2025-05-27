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

    fun agregarLista(nombre: String, descripcion: String, onFinish: () -> Unit) {
        viewModelScope.launch {
            listaDao.insertarLista(ListaEntity(nombre = nombre, descripcion = descripcion))
            cargarListas()
            onFinish()
        }
    }
}