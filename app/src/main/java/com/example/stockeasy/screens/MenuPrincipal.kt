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

@Composable
fun MenuPrincipalPantalla(
    onNavigateToListas: () -> Unit,
    onNavigateToHistorialVentas: () -> Unit,
    onNavigateToEditarPerfil: () -> Unit,
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo de la app",
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(vertical = 16.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Diseñada para facilitar tu vida al tener control de tus espacios personales.",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            color = Color(0xFF424242),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        MenuOption(
            iconResId = R.drawable.lista,
            text = "Listas",
            onClick = onNavigateToListas
        )

        Spacer(modifier = Modifier.height(15.dp))

        MenuOption(
            iconResId = R.drawable.historial,
            text = "Historial de Ventas",
            onClick = onNavigateToHistorialVentas
        )

        Spacer(modifier = Modifier.weight(1f))

        // Nuevo botón "Editar Perfil" estilo SmallButton
        SmallButton(
            iconResId = R.drawable.usuario,
            text = "Editar Perfil",
            color = Color(0xFFB0BEC5),
            onClick = onNavigateToEditarPerfil,
            alignStart = false
        )

        Spacer(modifier = Modifier.height(12.dp))

        SmallButton(
            iconResId = R.drawable.exit,
            text = "Cerrar sesión",
            color = Color(0xFFB0BEC5),
            onClick = onLogout,
            alignStart = false
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}


@Composable
fun MenuOption(
    iconResId: Int,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(280.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D6D)),
        contentPadding = PaddingValues(start = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color.White)
        }
    }
}

@Composable
fun SmallButton(
    iconResId: Int,
    text: String,
    color: Color,
    onClick: () -> Unit,
    alignStart: Boolean
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(280.dp)
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        contentPadding = PaddingValues(start = if (alignStart) 16.dp else 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (alignStart) Arrangement.Start else Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = text, color = Color.White, fontSize = 14.sp)
        }
    }
}
