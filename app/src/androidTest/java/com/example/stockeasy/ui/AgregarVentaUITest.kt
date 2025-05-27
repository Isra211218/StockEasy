package com.example.stockeasy.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.assertTextContains
import com.example.stockeasy.screens.AgregarVentaPantalla
import org.junit.Rule
import org.junit.Test

class AgregarVentaUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun escribirEnCampoProducto() {
        composeTestRule.setContent {
            AgregarVentaPantalla(
                listaId = 1,
                onGuardarVenta = { _, _, _, _, _ -> },
                onVolverAHistorial = {},
                onVolverAlMenu = {}
            )
        }

        // Escribir "coca" en el campo "Producto"
        composeTestRule.onNodeWithText("Producto").performTextInput("coca")

        // Verificar que el campo contiene el texto
        composeTestRule.onNodeWithText("Producto").assertTextContains("coca")
    }
}
