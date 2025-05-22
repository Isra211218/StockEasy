package com.example.stockeasy.Navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stockeasy.screens.*




object Rutas {
    const val INICIO_SESION    = "inicio_sesion"
    const val REGISTRO         = "registro"
    const val MENU_PRINCIPAL   = "menu_principal"
    const val MENU_LISTAS      = "menu_listas"
    const val HISTORIAL_VENTAS = "historial_ventas"
    const val EDITAR_PERFIL    = "editar_perfil"
    const val LISTA_SELECCIONADA = "lista_seleccionada"
    const val AGREGAR_LISTA = "agregar_lista"
    const val AGREGAR_VENTA    = "agregar_venta"
    const val EDITAR_PRODUCTO = "editar_producto"
    const val NUEVO_PRODUCTO = "nuevo_producto"


}



@Composable
fun NavManager(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Rutas.INICIO_SESION
    ) {
        // Login
        composable(Rutas.INICIO_SESION) {
            InicioSesionPantalla(
                onLoginSuccess = {
                    navController.navigate(Rutas.MENU_PRINCIPAL) {
                        popUpTo(Rutas.INICIO_SESION) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Rutas.REGISTRO)
                }
            )
        }

        // Registro
        composable(Rutas.REGISTRO) {
            RegistroPantalla(
                onRegisterSuccess = {
                    navController.navigate(Rutas.MENU_PRINCIPAL) {
                        popUpTo(Rutas.INICIO_SESION) { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // ── Menú principal ────────────────────────────────────────────────────────
        composable(Rutas.MENU_PRINCIPAL) {
            MenuPrincipalPantalla(
                onNavigateToListas = { navController.navigate(Rutas.MENU_LISTAS) },
                onNavigateToHistorialVentas = { navController.navigate(Rutas.HISTORIAL_VENTAS) },
                onNavigateToEditarPerfil = {                       // ✔ IMPLEMENTADO
                    navController.navigate(Rutas.EDITAR_PERFIL)
                },
                onLogout = {
                    navController.navigate(Rutas.INICIO_SESION) {
                        popUpTo(Rutas.INICIO_SESION) { inclusive = true }
                    }
                }
            )
        }

        // ── Editar perfil ─────────────────────────────────────────────────────────
        composable(Rutas.EDITAR_PERFIL) {
            EditarPerfilPantalla(
                nombreUsuario = "",       // <-- usa tu ViewModel o estado real
                correoUsuario = "",
                onGuardarCambios = { navController.popBackStack() },
                onVolverAlMenu = {
                    navController.navigate(Rutas.MENU_PRINCIPAL) {
                        popUpTo(Rutas.MENU_PRINCIPAL) { inclusive = false }
                    }
                }
            )
        }


        // Menú Listas
        composable(Rutas.MENU_LISTAS) {
            MenuListasPantalla(
                listas = listOf(
                    Lista("Inventario General"),
                    Lista("Cocina"),
                    Lista("Oficina")
                ),
                onSeleccionarLista = { lista ->
                    val nombreCodificado = Uri.encode(lista.nombre)
                    navController.navigate("${Rutas.LISTA_SELECCIONADA}/$nombreCodificado")
                },
                onAgregarLista = {
                    navController.navigate(Rutas.AGREGAR_LISTA)
                },
                onVolverAlMenu = {
                    navController.popBackStack()
                }
            )
        }

        //Lista seleccionada
        composable("${Rutas.LISTA_SELECCIONADA}/{nombreLista}") { backStackEntry ->
            val nombreLista = backStackEntry.arguments?.getString("nombreLista") ?: ""
            ListaSeleccionadaPantalla(
                nombreLista = nombreLista,
                descripcionLista = "Descripción de $nombreLista", // puedes mejorar esto si tienes una descripción real
                productos = listOf(), // podrías usar ViewModel o argumentos para productos reales
                onVolver = { navController.popBackStack() },
                onIrAlInicio = {
                    navController.navigate(Rutas.MENU_PRINCIPAL) {
                        popUpTo(Rutas.MENU_PRINCIPAL) { inclusive = false }
                    }
                },
                onAgregarProducto = {
                    navController.navigate(Rutas.NUEVO_PRODUCTO) },
                onEditarProducto = {
                    navController.navigate(Rutas.EDITAR_PRODUCTO)
                }
            )
        }

        //Editar producto
        composable(Rutas.EDITAR_PRODUCTO) {
            EditarProductoPantalla(
                productoInicial = "", // ← puedes ajustar según datos reales
                cantidadInicial = "",
                onGuardarCambios = { nuevoNombre, nuevaCantidad ->
                    // Guardar cambios, mostrar mensaje, etc.
                    navController.popBackStack() // Vuelve a la pantalla anterior
                },
                onVolver = { navController.popBackStack() },
                onIrAlInicio = {
                    navController.navigate(Rutas.MENU_PRINCIPAL) {
                        popUpTo(Rutas.MENU_PRINCIPAL) { inclusive = false }
                    }
                }
            )
        }

//Nuevo Producto
        composable(Rutas.NUEVO_PRODUCTO) {
            NuevoProductoPantalla(
                onGuardarProducto = { nombre, cantidad ->
                    // Lógica para guardar o volver
                    navController.popBackStack()
                },
                onVolver = { navController.popBackStack() },
                onIrAlInicio = {
                    navController.navigate(Rutas.MENU_PRINCIPAL) {
                        popUpTo(Rutas.MENU_PRINCIPAL) { inclusive = false }
                    }
                }
            )
        }


// 🔹 Pantalla para agregar venta
        composable(Rutas.AGREGAR_VENTA) {
            AgregarVentaPantalla(
                onGuardarVenta = { producto, cantidad, fecha, lista ->
                    // Aquí puedes manejar el guardado real y volver atrás
                    navController.popBackStack() // o mostrar mensaje, etc.
                },
                onVolverAHistorial = {
                    navController.popBackStack()
                },
                onVolverAlMenu = {
                    navController.navigate(Rutas.MENU_PRINCIPAL) {
                        popUpTo(Rutas.MENU_PRINCIPAL) { inclusive = false }
                    }
                }
            )
        }


// 🔹 Pantalla para agregar lista
        composable(Rutas.AGREGAR_LISTA) {
            AgregarListaPantalla(
                onGuardarLista = { _, _ -> navController.popBackStack() },
                onVolver = { navController.popBackStack() },
                onIrAlInicio = {
                    navController.navigate(Rutas.MENU_PRINCIPAL) {
                        popUpTo(Rutas.MENU_PRINCIPAL) { inclusive = false }
                    }
                }
            )
        }



        // 🔹 NUEVA COMPOSABLE: Historial de Ventas
        composable(Rutas.HISTORIAL_VENTAS) {
            HistorialVentasPantalla(
                ventas = listOf(),
                onAgregarVenta = {
                    navController.navigate(Rutas.AGREGAR_VENTA)
                },
                onVolverAlMenu = {
                    navController.navigate(Rutas.MENU_PRINCIPAL) {
                        popUpTo(Rutas.MENU_PRINCIPAL) { inclusive = false }
                    }
                }
            )
        }
    }
}



