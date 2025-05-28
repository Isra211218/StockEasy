package com.example.stockeasy.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.ComponentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockeasy.R
import com.example.stockeasy.viewmodel.ColorViewModel
import com.example.stockeasy.viewmodel.UsuarioViewModel

@SuppressLint("ContextCastToActivity")
@Composable
fun EditarPerfilPantalla(
    onGuardarCambios: () -> Unit,
    onVolverAlMenu: () -> Unit
) {
    val viewModel: UsuarioViewModel = viewModel()
    val activity = LocalContext.current as ComponentActivity
    val colorViewModel: ColorViewModel = viewModel(activity)
    val usuario by viewModel.usuario.collectAsState()

    var nuevaContrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    var nuevaContrasenaVisible by remember { mutableStateOf(false) }
    var confirmarContrasenaVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    var expanded by remember { mutableStateOf(false) }
    val opcionesColor = listOf("Blanco", "Gris claro", "Azul celeste")


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 64.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.profile_user),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Editar Perfil",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color(0xFF2E7D6D)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = usuario?.nombre ?: "",
                onValueChange = {},
                label = { Text("Nombre") },
                singleLine = true,
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = usuario?.correo ?: "",
                onValueChange = {},
                label = { Text("Correo electrónico") },
                singleLine = true,
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = nuevaContrasena,
                onValueChange = { nuevaContrasena = it },
                label = { Text("Insertar nueva contraseña") },
                singleLine = true,
                visualTransformation = if (nuevaContrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val icon = if (nuevaContrasenaVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { nuevaContrasenaVisible = !nuevaContrasenaVisible }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmarContrasena,
                onValueChange = { confirmarContrasena = it },
                label = { Text("Confirmar nueva contraseña") },
                singleLine = true,
                visualTransformation = if (confirmarContrasenaVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val icon = if (confirmarContrasenaVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { confirmarContrasenaVisible = !confirmarContrasenaVisible }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Selector de color de fondo
            Text("Color de fondo:", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))

            Box {
                Button(onClick = { expanded = true }) {
                    Text("Seleccionar color")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opcionesColor.forEach { color ->
                        DropdownMenuItem(
                            text = { Text(color) },
                            onClick = {
                                colorViewModel.backgroundColor.value = when (color) {
                                    "Gris claro" -> Color(0xFFCFCFCF)
                                    "Azul celeste" -> Color(0xFFCAE7FF)
                                    else -> Color.White
                                }
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (nuevaContrasena == confirmarContrasena && nuevaContrasena.isNotBlank()) {
                        mensajeError = ""
                        viewModel.actualizarContrasena(nuevaContrasena)
                        onGuardarCambios()
                    } else {
                        mensajeError = "La contraseña no coincide"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D6D))
            ) {
                Text("Guardar Cambios", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

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
