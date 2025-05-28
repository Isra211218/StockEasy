package com.example.stockeasy.screens

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockeasy.R
import com.example.stockeasy.viewmodel.ListaViewModel

@Composable
fun AgregarListaPantalla(
    onVolver: () -> Unit,
    onIrAlInicio: () -> Unit,
    onGuardarLista: () -> Boolean
) {
    val context = LocalContext.current
    val viewModel: ListaViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.applicationContext as Application)
    )

    var nombreLista by remember { mutableStateOf("") }
    var descripcionLista by remember { mutableStateOf("") }

    var mostrarErrorNombre by remember { mutableStateOf(false) }
    var mostrarErrorDescripcion by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.lista),
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
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
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
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
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
                        viewModel.agregarLista(nombreLista, descripcionLista) {
                            onGuardarLista()
                        }
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
