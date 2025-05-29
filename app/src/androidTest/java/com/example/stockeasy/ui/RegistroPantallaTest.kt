package com.example.stockeasy.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import com.example.stockeasy.screens.RegistroPantalla
import org.junit.Rule
import org.junit.Test

class RegistroPantallaTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun registrarUsuario_conContrasenasDistintas_muestraError() {
        // Cargar RegistroPantalla directamente
        composeTestRule.setContent {
            RegistroPantalla(
                onRegisterSuccess = {},
                onNavigateToLogin = {}
            )
        }

        // Ingresar datos en los campos de texto
        composeTestRule.onNodeWithText("Nombre completo").performTextInput("Juan Pérez")
        composeTestRule.onNodeWithText("Correo electrónico").performTextInput("juan@ejemplo.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("123456")
        composeTestRule.onNodeWithText("Confirmar contraseña").performTextInput("abcdef")

        // Hacer clic en el botón REGISTRARSE
        composeTestRule.onNodeWithText("REGISTRARSE").performClick()

        // Verificar que se muestra el mensaje de error por contraseñas distintas
        composeTestRule.onNodeWithText("Las contraseñas no coinciden").assertExists()
    }
}
