package com.example.stockeasy.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockeasy.R
import com.example.stockeasy.viewmodel.UsuarioViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InicioSesionPantalla(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewModel: UsuarioViewModel = viewModel()
    val context = LocalContext.current

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .clickable { keyboardController?.hide() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Fondo",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Bienvenido a StockEasy",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "Inicia sesión.",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                mensajeError = null
            },
            label = { Text("Correo electrónico") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { keyboardController?.hide() }),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                mensajeError = null
            },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = if (mostrarContrasena) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
            trailingIcon = {
                IconButton(onClick = { mostrarContrasena = !mostrarContrasena }) {
                    Icon(
                        imageVector = if (mostrarContrasena)
                            Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (mostrarContrasena) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        mensajeError?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (correo.isBlank() || contrasena.isBlank()) {
                    mensajeError = "Ingresa un correo y contraseña"
                } else {
                    viewModel.iniciarSesion(
                        correo = correo,
                        contrasena = contrasena,
                        onSuccess = {
                            val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                            with (sharedPref.edit()) {
                                putBoolean("is_logged_in", true)
                                apply()
                            }
                            mensajeError = null
                            onLoginSuccess()
                        },
                        onError = { mensaje ->
                            mensajeError = mensaje
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D6D))
        ) {
            Text("INICIAR SESIÓN", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¿No tienes cuenta? REGÍSTRATE",
            color = Color(0xFF2E7D6D),
            modifier = Modifier.clickable { onNavigateToRegister() }
        )
    }
}
