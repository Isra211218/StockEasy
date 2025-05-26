package com.example.stockeasy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockeasy.R

@Composable
fun AgregarListaPantalla(
    onGuardarLista: (String, String) -> Unit,
    onVolver: () -> Unit,
    onIrAlInicio: () -> Unit,
) {
    var nombreLista by remember { mutableStateOf("") }
    var descripcionLista by remember { mutableStateOf("") }

    var mostrarErrorNombre by remember { mutableStateOf(false) }
    var mostrarErrorDescripcion by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Contenido principal desplazable
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Agregar Lista",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF2E7D6D)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = nombreLista,
                onValueChange = {
                    nombreLista = it
                    mostrarErrorNombre = false
                },
                label = { Text("Nombre de la lista") },
                isError = mostrarErrorNombre,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (mostrarErrorNombre) {
                Text(
                    text = "El nombre de la lista es obligatorio",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = descripcionLista,
                onValueChange = {
                    descripcionLista = it
                    mostrarErrorDescripcion = false
                },
                label = { Text("Descripción") },
                isError = mostrarErrorDescripcion,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
            if (mostrarErrorDescripcion) {
                Text(
                    text = "La descripción es obligatoria",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val nombreValido = nombreLista.isNotBlank()
                    val descripcionValida = descripcionLista.isNotBlank()

                    mostrarErrorNombre = !nombreValido
                    mostrarErrorDescripcion = !descripcionValida

                    if (nombreValido && descripcionValida) {
                        onGuardarLista(nombreLista, descripcionLista)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D6D))
            ) {
                Text("Guardar Lista", color = Color.White)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }

        // Botón de volver
        IconButton(
            onClick = onVolver,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 8.dp, start = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.regreso),
                contentDescription = "Volver atrás",
                modifier = Modifier.size(28.dp)
            )
        }

        // Botón de inicio (Home)
        IconButton(
            onClick = onIrAlInicio,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Ir a inicio",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
