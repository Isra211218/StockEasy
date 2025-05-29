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
    fun escribirCantidadCorrectamente() {
        // Carga la pantalla
        composeTestRule.setContent {
            AgregarVentaPantalla(
                listaId = 1,
                onGuardarVenta = { _, _, _, _, _ -> },
                onVolverAHistorial = {},
                onVolverAlMenu = {}
            )
        }

        // Escribe "5" en el campo "Cantidad"
        composeTestRule.onNodeWithText("Cantidad").performTextInput("5")

        // Verifica que el campo contiene "5"
        composeTestRule.onNodeWithText("Cantidad").assertTextContains("5")
    }
}
