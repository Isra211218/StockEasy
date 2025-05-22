package com.example.stockeasy.screens

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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockeasy.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InicioSesionPantalla(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var mostrarContrasena by remember { mutableStateOf(false) }
    var mostrarError by remember { mutableStateOf(false) }

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

        /* ---------- Correo ---------- */
        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                if (mostrarError) mostrarError = false
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

        /* ---------- Contraseña ---------- */
        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                if (mostrarError) mostrarError = false
            },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation =
                if (mostrarContrasena) VisualTransformation.None else PasswordVisualTransformation(),
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
                        contentDescription =
                            if (mostrarContrasena) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        /* ---------- Mensaje de error ---------- */
        if (mostrarError) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Ingresa un correo y contraseña",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                textAlign = TextAlign.Start
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        /* ---------- Botón Iniciar sesión ---------- */
        Button(
            onClick = {
                if (correo.isBlank() || contrasena.isBlank()) {
                    mostrarError = true
                } else {
                    mostrarError = false
                    onLoginSuccess()
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

        /* ---------- Link a registro ---------- */
        Text(
            text = "¿No tienes cuenta? REGÍSTRATE",
            color = Color(0xFF2E7D6D),
            modifier = Modifier.clickable { onNavigateToRegister() }
        )
    }
}

/* --- Preview opcional ---
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun InicioSesionPantallaPreview() {
    InicioSesionPantalla(onLoginSuccess = {}, onNavigateToRegister = {})
}
*/

