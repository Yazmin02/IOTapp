package com.example.iotapp.ui.screens.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LocationScreen(locationViewModel: LocationViewModel = viewModel()) {
    val state by locationViewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Ubicaciones", style = MaterialTheme.typography.h4)

        // Mostrar lista de ubicaciones
        state.locations?.let { locations ->
            locations.forEach { location ->
                Text(text = "Nombre: ${location.name}, Detalles: ${location.details}")
            }
        }

        // Formularios para crear/actualizar/eliminar ubicaciones
        // Ejemplo de formulario de creación
        var name by remember { mutableStateOf("") }
        var details by remember { mutableStateOf("") }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") }
        )
        OutlinedTextField(
            value = details,
            onValueChange = { details = it },
            label = { Text("Detalles") }
        )
        Button(onClick = {
            locationViewModel.createLocation(name, details)
        }) {
            Text("Crear Ubicación")
        }
    }
}
