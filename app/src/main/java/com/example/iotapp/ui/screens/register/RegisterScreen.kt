package com.example.iotapp.ui.screens.register

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
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel()
) {
    // Obteniendo el estado de la UI desde el ViewModel
    val state by registerViewModel.uiState.collectAsState()

    // Contenedor Column para organizar los elementos verticalmente
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la pantalla
        Text(text = "Registro de Usuario", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto para el nombre
        TextField(
            value = state.name,
            onValueChange = { registerViewModel.onNameChange(it) },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para el correo electrónico
        TextField(
            value = state.email,
            onValueChange = { registerViewModel.onEmailChange(it) },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto para la contraseña con transformación de visualización
        TextField(
            value = state.password,
            onValueChange = { registerViewModel.onPasswordChange(it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para registrar un nuevo usuario
        PrimaryButton(text = "Registrar", onClick = { registerViewModel.registerUser() })

        // Mostrar un indicador de progreso si el registro está en proceso
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        // Mostrar mensaje de error si hay algún error
        if (state.error.isNotEmpty()) {
            Text(text = state.error, color = MaterialTheme.colors.error)
        }

        // Efecto lanzado cuando el estado de registro cambia
        LaunchedEffect(state.isRegistered) {
            if (state.isRegistered) {
                onRegisterSuccess()
            }
        }
    }
}
