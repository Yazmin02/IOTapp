package com.example.iotapp.ui.screens.locations

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iotapp.R

@Composable
fun LocationScreen(
    locationViewModel: LocationViewModel = viewModel(),
    onLocationSelected: (Int) -> Unit
) {
    val state by locationViewModel.uiState.collectAsState()
    var selectedLocationId by remember { mutableStateOf<Int?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.fondo2), // Reemplaza con tu imagen
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Ubicaciones",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            state.locations?.let { locations ->
                items(locations.size) { index ->
                    val location = locations[index]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.8f)) // Fondo blanco con transparencia
                            .padding(16.dp)
                    ) {
                        Text(text = "Nombre: ${location.name ?: "N/A"}", fontWeight = FontWeight.Bold)
                        Text(text = "Detalles: ${location.details ?: "N/A"}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    selectedLocationId = location.id
                                    name = location.name
                                    details = location.details
                                    isEditing = true
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00E5FF)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Editar")
                            }
                            Button(
                                onClick = {
                                    selectedLocationId = location.id
                                    onLocationSelected(location.id!!)
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00E5FF)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Ver Botes")
                            }
                            Button(
                                onClick = {
                                    locationViewModel.deleteLocation(location.id!!)
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5252)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isEditing) "Editar Ubicación" else "Crear Nueva Ubicación",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    label = { Text("Detalles") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                Button(
                    onClick = {
                        if (isEditing && selectedLocationId != null) {
                            locationViewModel.updateLocation(selectedLocationId!!, name, details)
                            isEditing = false
                            selectedLocationId = null
                        } else {
                            locationViewModel.createLocation(name, details)
                        }
                        name = ""
                        details = ""
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00E5FF)),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(if (isEditing) "Actualizar Ubicación" else "Crear Ubicación")
                }

                if (state.error != null) {
                    Text(text = "Error: ${state.error}", color = MaterialTheme.colors.error)
                }
            }
        }
    }
}
