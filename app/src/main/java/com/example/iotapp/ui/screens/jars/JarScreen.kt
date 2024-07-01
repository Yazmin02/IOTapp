package com.example.iotapp.ui.screens.jars

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iotapp.data.models.Jar

@Composable
fun JarScreen(locationId: Int, jarViewModel: JarViewModel = viewModel()) {
    val state by jarViewModel.uiState.collectAsState()

    LaunchedEffect(locationId) {
        jarViewModel.readJarsByLocation(locationId)
    }

    var description by remember { mutableStateOf("") }
    var selectedJar by remember { mutableStateOf<Jar?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Botes en la ubicación $locationId", style = MaterialTheme.typography.h4)

        val jars = state.jars
        if (jars != null && jars.isNotEmpty()) {
            Column(modifier = Modifier.weight(1f)) {
                jars.forEach { jar ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Descripción: ${jar.description}")
                        Row {
                            Button(onClick = { selectedJar = jar; description = jar.description }) {
                                Text("Editar")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { jarViewModel.deleteJar(jar.id!!, locationId) }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        } else {
            Text(text = "No hay botes en esta ubicación.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (selectedJar != null) {
                jarViewModel.updateJar(selectedJar!!.id!!, locationId, description)
                selectedJar = null
            } else {
                jarViewModel.createJar(locationId, description)
            }
            description = ""
        }) {
            Text(if (selectedJar != null) "Actualizar Bote" else "Crear Bote")
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let {
            Text(text = it, color = MaterialTheme.colors.error)
        }
    }
}
