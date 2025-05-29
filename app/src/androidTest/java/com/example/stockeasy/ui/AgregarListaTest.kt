package com.example.stockeasy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import androidx.compose.ui.platform.testTag
import org.junit.Rule
import org.junit.Test

class AgregarListaTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private var nombreCapturado: String? = null
    private var descripcionCapturada: String? = null

    @Test
    fun ingresarDatosYGuardarLista_correctamenteEjecutaCallback() {
        composeTestRule.setContent {
            AgregarListaPantallaTestable(
                onGuardarLista = { _, nombre, descripcion ->
                    nombreCapturado = nombre
                    descripcionCapturada = descripcion
                }
            )
        }

        // Ingresar nombre
        composeTestRule.onNodeWithTag("CampoNombre").performTextInput("Mi Lista Test")

        // Ingresar descripción
        composeTestRule.onNodeWithTag("CampoDescripcion").performTextInput("Descripción de prueba")

        // Presionar el botón
        composeTestRule.onNodeWithTag("BotonGuardar").performClick()

        // Verificar que se ejecutó el callback con los datos correctos
        assertEquals("Mi Lista Test", nombreCapturado)
        assertEquals("Descripción de prueba", descripcionCapturada)
    }
}

@Composable
fun AgregarListaPantallaTestable(
    onGuardarLista: (Int, String, String) -> Unit
) {
    var nombreLista by remember { mutableStateOf("") }
    var descripcionLista by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = nombreLista,
            onValueChange = { nombreLista = it },
            label = { Text("Nombre de la lista") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("CampoNombre")
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcionLista,
            onValueChange = { descripcionLista = it },
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("CampoDescripcion")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (nombreLista.isNotBlank() && descripcionLista.isNotBlank()) {
                    onGuardarLista(1, nombreLista, descripcionLista)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("BotonGuardar")
        ) {
            Text("Guardar Lista")
        }
    }
}
