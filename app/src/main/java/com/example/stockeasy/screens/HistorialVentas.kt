package com.example.stockeasy.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

/* ---------- DATOS ---------- */
data class Venta(
    val fecha: String,
    val producto: String,
    val lista: String,
    val cantidad: String
)

/* ---------- PANTALLA ---------- */
@Composable
fun HistorialVentasPantalla(
    ventas: List<Venta>,
    onAgregarVenta: () -> Unit,
    onVolverAlMenu: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        /* Botón volver al menú (icono) */
        IconButton(
            onClick = onVolverAlMenu,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Volver al menú",
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

            /* Logo */
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(vertical = 16.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Historial de Ventas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF2E7D6D)
            )

            Spacer(modifier = Modifier.height(24.dp))

            /* Lista de ventas */
            if (ventas.isEmpty()) {
                Text(
                    text = "No hay ventas registradas.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            } else {
                ventas.forEach { venta ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            /* Producto - primero, negritas, negro */
                            Text(
                                text = "Producto: ${venta.producto}",
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            /* Cantidad - segundo, sin negritas, negro */
                            Text(
                                text = "Cantidad: ${venta.cantidad}",
                                color = Color.Black
                            )
                            /* Fecha - tercero, sin negritas, negro */
                            Text(
                                text = "Fecha: ${venta.fecha}",
                                color = Color.Black
                            )
                            /* Lista - cuarto, color verde/azulado, sin negritas */
                            Text(
                                text = "Lista: ${venta.lista}",
                                color = Color(0xFF2E7D6D)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            /* Botón AGREGAR VENTA */
            Button(
                onClick = onAgregarVenta,
                modifier = Modifier
                    .width(280.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D6D))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.venta), // ícono que colocarás tú
                        contentDescription = "Agregar venta",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Agregar venta", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


