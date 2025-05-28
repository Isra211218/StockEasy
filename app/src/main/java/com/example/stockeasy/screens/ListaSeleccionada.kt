package com.example.stockeasy.screens

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockeasy.R
import com.example.stockeasy.viewmodel.ProductoViewModel
import com.example.stockeasy.viewmodel.ListaViewModel

@Composable
fun ListaSeleccionadaPantalla(
    nombreLista: String,
    descripcionLista: String,
    listaId: Int,
    onVolver: () -> Unit,
    onIrAlInicio: () -> Unit,
    onAgregarProducto: () -> Unit,
    onEditarProducto: (Int) -> Unit,
) {
    val productoViewModel: ProductoViewModel = viewModel()
    val listaViewModel: ListaViewModel = viewModel()
    val productos by productoViewModel.productos.collectAsState()

    var mostrarDialogoConfirmacion by remember { mutableStateOf(false) }

    LaunchedEffect(listaId) {
        productoViewModel.cargarProductos(listaId)
    }

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
                painter = painterResource(id = R.drawable.lista),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(vertical = 16.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = nombreLista,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D6D),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = descripcionLista,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (productos.isEmpty()) {
                Text(
                    text = "No hay productos en esta lista.",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            } else {
                productos.forEach { producto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { onEditarProducto(producto.id) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val imageBitmap = remember(producto.imagenBase64) {
                                    try {
                                        if (!producto.imagenBase64.isNullOrBlank()) {
                                            val bytes = Base64.decode(producto.imagenBase64, Base64.DEFAULT)
                                            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)?.asImageBitmap()
                                        } else null
                                    } catch (e: Exception) {
                                        null
                                    }
                                }

                                if (imageBitmap != null) {
                                    Image(
                                        bitmap = imageBitmap,
                                        contentDescription = producto.nombre,
                                        modifier = Modifier.size(50.dp)
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.no_photos),
                                        contentDescription = "Imagen no disponible",
                                        modifier = Modifier.size(50.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = "Producto: ${producto.nombre}",
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Existencias: ${producto.cantidad}",
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = onAgregarProducto,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D6D))
                ) {
                    Text("Añadir Producto", color = Color.White)
                }

                Button(
                    onClick = { mostrarDialogoConfirmacion = true },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Eliminar Lista", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (mostrarDialogoConfirmacion) {
            AlertDialog(
                onDismissRequest = { mostrarDialogoConfirmacion = false },
                title = { Text("Confirmar eliminación") },
                text = { Text("¿Estás seguro de que deseas eliminar esta lista? Esta acción no se puede deshacer.") },
                confirmButton = {
                    TextButton(onClick = {
                        listaViewModel.eliminarLista(listaId) {
                            mostrarDialogoConfirmacion = false
                            onIrAlInicio()
                        }
                    }) {
                        Text("Eliminar", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogoConfirmacion = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }

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
