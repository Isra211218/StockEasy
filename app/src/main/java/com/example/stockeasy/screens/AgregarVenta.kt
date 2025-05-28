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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockeasy.R
import com.example.stockeasy.viewmodel.VentaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarVentaPantalla(
    listaId: Int,
    onGuardarVenta: (String, String, String, String, Int) -> Unit,
    onVolverAHistorial: () -> Unit,
    onVolverAlMenu: () -> Unit
) {
    val viewModel: VentaViewModel = viewModel()
    val listasDisponibles by viewModel.nombresListas.collectAsState()
    val productosDisponibles by viewModel.productosDeLista.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarNombresListas()
    }
    var listaIdSeleccionado by remember { mutableStateOf(listaId) }
    var producto by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var lista by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

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
                painter = painterResource(id = R.drawable.ventas),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Agregar Venta", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D6D))

            Spacer(modifier = Modifier.height(24.dp))

            // Primero: Lista
            var expandedLista by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expandedLista,
                onExpandedChange = { expandedLista = !expandedLista }
            ) {
                OutlinedTextField(
                    value = lista,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Lista") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLista) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedLista,
                    onDismissRequest = { expandedLista = false }
                ) {
                    listasDisponibles.forEach { nombreLista ->
                        DropdownMenuItem(
                            text = { Text(nombreLista) },
                            onClick = {
                                lista = nombreLista
                                expandedLista = false
                                viewModel.cargarProductosDeLista(nombreLista) { id ->
                                    listaIdSeleccionado = id
                                    producto = "" // Limpiar producto después de cargar los nuevos productos
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Segundo: Producto
            var expandedProducto by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expandedProducto,
                onExpandedChange = { expandedProducto = !expandedProducto }
            ) {
                OutlinedTextField(
                    value = producto,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Producto") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedProducto) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandedProducto,
                    onDismissRequest = { expandedProducto = false }
                ) {
                    productosDisponibles.forEach { nombreProducto ->
                        DropdownMenuItem(
                            text = { Text(nombreProducto) },
                            onClick = {
                                producto = nombreProducto
                                expandedProducto = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.agregarVenta(producto, cantidad, fecha, lista, listaIdSeleccionado) {
                        onGuardarVenta(producto, cantidad, fecha, lista, listaIdSeleccionado)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D6D))
            ) {
                Text("Guardar Venta", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        IconButton(
            onClick = onVolverAHistorial,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.regreso),
                contentDescription = "Volver",
                modifier = Modifier.size(28.dp)
            )
        }

        IconButton(
            onClick = onVolverAlMenu,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Inicio",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
