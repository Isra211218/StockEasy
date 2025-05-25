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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import com.example.stockeasy.R

data class Lista(val nombre: String)

@Composable
fun MenuListasPantalla(
    listas: List<Lista>,
    onSeleccionarLista: (Lista) -> Unit,
    onAgregarLista: () -> Unit,
    onVolverAlMenu: () -> Unit
) {
    var busqueda by remember { mutableStateOf("") }
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
                    .height(140.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mis Listas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D6D),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de búsqueda
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                label = { Text("Buscar lista") },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.buscar),
                        contentDescription = "Buscar",
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Listas filtradas
            val listasFiltradas = if (busqueda.isBlank()) listas else listas.filter {
                it.nombre.contains(busqueda, ignoreCase = true)
            }

            if (listasFiltradas.isEmpty()) {
                Text("No se encontraron listas.", color = Color.Gray)
            } else {
                listasFiltradas.forEach { lista ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = lista.nombre,
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Medium
                            )
                            IconButton(onClick = { onSeleccionarLista(lista) }) {
                                Image(
                                    painter = painterResource(id = R.drawable.verlista),
                                    contentDescription = "Ver lista",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón Agregar Lista
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onAgregarLista,
                    modifier = Modifier
                        .width(180.dp)
                        .height(44.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D6D))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.agregar),
                            contentDescription = "Agregar lista",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Agregar Lista", color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Botón Home funcional superpuesto
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
        ) {
            IconButton(
                onClick = onVolverAlMenu,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 8.dp, end = 4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Volver al menú",
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
