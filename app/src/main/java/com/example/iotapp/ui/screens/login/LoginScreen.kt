package com.example.iotapp.ui.screens.login

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iotapp.ui.components.PrimaryButton

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    // Obteniendo el estado de la UI desde el ViewModel
    val state by loginViewModel.uiState.collectAsState()

    // Contenedor Column para organizar los elementos verticalmente
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(text = "Bienvenido", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para el correo electrónico
        TextField(
            value = state.email,
            onValueChange = { loginViewModel.onEmailChange(it) },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para la contraseña con transformación de visualización
        TextField(
            value = state.password,
            onValueChange = { loginViewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para iniciar sesión
        PrimaryButton(text = "Iniciar sesión", onClick = { loginViewModel.authenticateUser() })

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para navegar a la pantalla de registro
        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes cuenta? Regístrate")
        }

        // Mostrar un indicador de progreso si la autenticación está en proceso
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        // Mostrar mensaje de error si hay algún error
        if (state.error.isNotEmpty()) {
            Text(text = state.error, color = MaterialTheme.colors.error)
        }

        // Efecto lanzado cuando el estado de autenticación cambia
        LaunchedEffect(state.isAuthenticated) {
            Log.d("LoginScreen", "isAuthenticated: ${state.isAuthenticated}")
            if (state.isAuthenticated) {
                Log.d("LoginScreen", "Navigating to locations screen")
                onLoginSuccess() // Llama al callback para cambiar de pantalla
            }
        }
    }
}
