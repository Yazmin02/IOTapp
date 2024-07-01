package com.example.iotapp.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iotapp.R
import com.example.iotapp.ui.components.PrimaryButton

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {
    val state by loginViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCDFFD8)), // Color de fondo
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp), // Padding alrededor de la columna
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(350.dp) // Tamaño de la imagen
                    .clip(RoundedCornerShape(16.dp)), // Bordes redondeados
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre elementos
            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.h4.copy(fontSize = 40.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.email,
                onValueChange = { loginViewModel.onEmailChange(it) },
                label = { Text("Correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent, RoundedCornerShape(8.dp)) // Fondo blanco y bordes redondeados
                    .padding(horizontal = 16.dp, vertical = 8.dp) // Padding interno
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = state.password,
                onValueChange = { loginViewModel.onPasswordChange(it) },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { loginViewModel.authenticateUser() },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)), // Bordes redondeados para el botón
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00C6FB)) // Color del botón
            ) {
                Text(text = "Iniciar sesión", color = Color.White)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Regístrate")
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.error.isNotEmpty()) {
                Text(text = state.error, color = MaterialTheme.colors.error)
            }
            LaunchedEffect(state.isAuthenticated) {
                if (state.isAuthenticated) {
                    onLoginSuccess()
                }
            }
        }
    }
}
