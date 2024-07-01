package com.example.iotapp.ui.screens.locations

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iotapp.ui.screens.jars.JarScreen

@Composable
fun LocationScreen(
    locationViewModel: LocationViewModel = viewModel(),
    onLocationSelected: (Int) -> Unit
) {
    val state by locationViewModel.uiState.collectAsState()
    var selectedLocationId by remember { mutableStateOf<Int?>(null) }

    if (selectedLocationId == null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Ubicaciones", style = MaterialTheme.typography.h4)

            state.locations?.let { locations ->
                Log.d("LocationScreen", "Locations: $locations")
                locations.forEach { location ->
                    Text(text = "Nombre: ${location.name ?: "N/A"}, Detalles: ${location.details ?: "N/A"}")
                    Button(onClick = { onLocationSelected(location.id ?: 0) }) {
                        Text("Ver Botes")
                    }
                }
            }

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

            if (state.error != null) {
                Text(text = "Error: ${state.error}", color = MaterialTheme.colors.error)
            }
        }
    } else {
        JarScreen(locationId = selectedLocationId!!)
    }
}
