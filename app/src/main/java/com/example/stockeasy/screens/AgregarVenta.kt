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
fun AgregarVentaPantalla(
    onGuardarVenta: (producto: String, cantidad: String, fecha: String, lista: String) -> Unit,
    onVolverAHistorial: () -> Unit,
    onVolverAlMenu: () -> Unit
) {
    var producto by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var lista by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .border(5.dp, Color(0xFF1976D2), RoundedCornerShape(16.dp))
    ) {
        // Botón regresar (izquierda)
        IconButton(
            onClick = onVolverAHistorial,
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

        // Botón home (derecha)
        IconButton(
            onClick = onVolverAlMenu,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Menú principal",
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
                text = "Agregar Venta",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF2E7D6D)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo: Producto
            CampoConIcono(
                valor = producto,
                onValorChange = { producto = it },
                label = "Producto",
                iconRes = R.drawable.producto
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Cantidad
            CampoConIcono(
                valor = cantidad,
                onValorChange = { cantidad = it },
                label = "Cantidad",
                iconRes = R.drawable.cantidad
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Fecha
            CampoConIcono(
                valor = fecha,
                onValorChange = { fecha = it },
                label = "Fecha",
                iconRes = R.drawable.fecha
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Lista
            CampoConIcono(
                valor = lista,
                onValorChange = { lista = it },
                label = "Lista",
                iconRes = R.drawable.lista
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (producto.isNotBlank() && cantidad.isNotBlank() &&
                        fecha.isNotBlank() && lista.isNotBlank()
                    ) {
                        onGuardarVenta(producto, cantidad, fecha, lista)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D6D))
            ) {
                Text("Guardar Venta", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CampoConIcono(
    valor: String,
    onValorChange: (String) -> Unit,
    label: String,
    iconRes: Int
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = { Text(label) },
        singleLine = true,
        leadingIcon = {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(24.dp)
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgregarVentaPreview() {
    AgregarVentaPantalla(
        onGuardarVenta = { _, _, _, _ -> },
        onVolverAHistorial = {},
        onVolverAlMenu = {}
    )
}
