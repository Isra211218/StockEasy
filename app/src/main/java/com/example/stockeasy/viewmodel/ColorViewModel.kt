package com.example.stockeasy.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color

class ColorViewModel : ViewModel() {
    // Color de fondo por defecto: blanco
    var backgroundColor = mutableStateOf(Color.White)

    fun setColor(color: Color) {
        backgroundColor.value = color
    }
}
