package com.example.stockeasy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.stockeasy.Navigation.NavManager
import com.example.stockeasy.ui.theme.StockEasyTheme
import com.example.stockeasy.viewmodel.ColorViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // Aquí obtenemos el mismo ViewModel que se compartirá en toda la app
            val colorViewModel: ColorViewModel = viewModel(this)
            val backgroundColor = colorViewModel.backgroundColor.value

            StockEasyTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = backgroundColor // Aplicamos el color de fondo dinámicamente
                ) {
                    NavManager(navController = navController)
                }
            }
        }
    }
}
