package com.example.stockeasy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.stockeasy.R

@Composable
fun NuevoProductoPantalla(
    onGuardarProducto: (String, String) -> Unit,
    onVolver: () -> Unit,
    onIrAlInicio: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val camposValidos = nombre.isNotBlank() && cantidad.isNotBlank()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .border(5.dp, Color(0xFF1976D2), RoundedCornerShape(16.dp))
    ) {
        // Icono volver
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

        // Icono home
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Nuevo Producto",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D6D),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo: Nombre del producto con ícono dentro del campo
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
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

            // Campo: Cantidad con ícono dentro del campo
            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Existencias") },
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

            // Botón para subir imagen
            Button(
                onClick = { /* lógica para subir imagen */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.foto),
                        contentDescription = "Subir imagen",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Subir Imagen del Producto", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Guardar
            Button(
                onClick = {
                    if (camposValidos) {
                        onGuardarProducto(nombre, cantidad)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                enabled = camposValidos,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D6D)
                )
            ) {
                Text("Guardar Producto", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun NuevoProductoPreview() {
    NuevoProductoPantalla(
        onGuardarProducto = { _, _ -> },
        onVolver = {},
        onIrAlInicio = {}
    )
}
