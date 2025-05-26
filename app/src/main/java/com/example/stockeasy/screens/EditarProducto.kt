package com.example.stockeasy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockeasy.R

@Composable
fun EditarProductoPantalla(
    productoInicial: String = "",
    cantidadInicial: String = "",
    onGuardarCambios: (String, String) -> Unit,
    onVolver: () -> Unit,
    onIrAlInicio: () -> Unit
) {
    var nombre by remember { mutableStateOf(productoInicial) }
    var cantidad by remember { mutableStateOf(cantidadInicial) }
    var imagenCambiada by remember { mutableStateOf(false) }

    // Detecta si hay cambios para habilitar botón guardar
    val cambiosHechos = (nombre.isNotBlank() && nombre != productoInicial) ||
            (cantidad.isNotBlank() && cantidad != cantidadInicial) ||
            imagenCambiada

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
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
                    .height(160.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Editar Producto",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D6D),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Nombre actual: $productoInicial",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Existencia actual: $cantidadInicial",
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nuevo nombre del producto") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.producto),
                        contentDescription = "Producto",
                        modifier = Modifier.size(30.dp)
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Nueva cantidad de existencias") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.cantidad),
                        contentDescription = "Cantidad",
                        modifier = Modifier.size(30.dp)
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botón para cambiar imagen (placeholder)
            Button(
                onClick = {
                    imagenCambiada = true // Aquí la lógica real para cambiar imagen
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.foto),
                        contentDescription = "Cambiar imagen",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cambiar Imagen del Producto", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Guardar cambios, habilitado sólo si hay cambios
            Button(
                onClick = {
                    onGuardarCambios(nombre, cantidad)
                },
                enabled = cambiosHechos,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D6D))
            ) {
                Text("Guardar Cambios", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Botón Volver (esquina superior izquierda)
        IconButton(
            onClick = onVolver,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.regreso),
                contentDescription = "Volver",
                modifier = Modifier.size(28.dp)
            )
        }

        // Botón Inicio (esquina superior derecha)
        IconButton(
            onClick = onIrAlInicio,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Inicio",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
