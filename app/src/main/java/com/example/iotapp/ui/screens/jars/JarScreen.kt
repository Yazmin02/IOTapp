package com.example.iotapp.ui.screens.jars

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iotapp.R
import com.example.iotapp.data.models.Jar

@Composable
fun JarScreen(
    locationId: Int,
    jarViewModel: JarViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToStatistics: (Int) -> Unit // Nueva función de navegación
) {
    val state by jarViewModel.uiState.collectAsState()

    LaunchedEffect(locationId) {
        jarViewModel.readJarsByLocation(locationId)
    }

    var description by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(0) }
    var selectedJar by remember { mutableStateOf<Jar?>(null) }

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
                        text = "Botes en la ubicación $locationId",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            state.jars?.let { jars ->
                items(jars.size) { index ->
                    val jar = jars[index]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.8f)) // Fondo blanco con transparencia
                            .padding(16.dp)
                    ) {
                        Text(text = "Descripción: ${jar.description}")
                        Text(text = "Status: ${if (jar.status == 1) "LLENO" else "VACIO"}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    selectedJar = jar
                                    description = jar.description
                                    status = jar.status
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00E5FF)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Editar")
                            }
                            Button(
                                onClick = {
                                    jarViewModel.deleteJar(jar.id!!, locationId)
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF5252)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Eliminar")
                            }
                            Button(
                                onClick = {
                                    onNavigateToStatistics(jar.id!!) // Navegar a la pantalla de estadísticas
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00E5FF)),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Ver Estadísticas")
                            }
                        }
                    }
                }
            } ?: item {
                Text(text = "No hay botes en esta ubicación.")
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = status.toString(),
                    onValueChange = { status = it.toIntOrNull() ?: 0 },
                    label = { Text("Status") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (selectedJar != null) {
                            jarViewModel.updateJar(selectedJar!!.id!!, locationId, description, status)
                            selectedJar = null
                        } else {
                            jarViewModel.createJar(locationId, description, status)
                        }
                        description = ""
                        status = 0
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00E5FF)),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text(if (selectedJar != null) "Actualizar Bote" else "Crear Bote")
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (state.isLoading) {
                    CircularProgressIndicator()
                }

                state.error?.let {
                    Text(text = it, color = MaterialTheme.colors.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón "Regresar" más pequeño
                Button(
                    onClick = onNavigateBack,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00E5FF)),
                    modifier = Modifier
                        .padding(8.dp)
                        .width(120.dp) // Ancho fijo para el botón
                        .height(40.dp) // Alto fijo para el botón
                ) {
                    Text("Regresar")
                }
            }
        }
    }
}
