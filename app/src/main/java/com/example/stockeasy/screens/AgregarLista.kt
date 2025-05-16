package com.example.stockeasy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.stockeasy.R

@Composable
fun AgregarListaPantalla(
    onGuardarLista: (String, String) -> Unit,
    onVolver: () -> Unit,
    onIrAlInicio: () -> Unit
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
            .border(5.dp, Color(0xFF1976D2), RoundedCornerShape(16.dp))
    ) {
        // Botón regresar (izquierda)
        IconButton(
            onClick = onVolver,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.regreso),
                contentDescription = "Volver atrás",
                modifier = Modifier.size(28.dp)
            )
        }

        // Botón home (derecha)
        IconButton(
            onClick = onIrAlInicio,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Ir a inicio",
                modifier = Modifier.size(28.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(vertical = 16.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Agregar Lista",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF2E7D6D)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo: Nombre de la lista
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

            // Campo: Descripción de la lista
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

            // Botón: Guardar Lista
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

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AgregarListaPreview() {
    AgregarListaPantalla(
        onGuardarLista = { _, _ -> },
        onVolver = {},
        onIrAlInicio = {}
    )
}

