package com.example.iotapp.ui.screens.jars

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iotapp.ui.screens.statistics.StatisticsViewModel
import com.example.iotapp.ui.screens.statistics.StatisticsState
import com.example.iotapp.ui.screens.statistics.StatisticsScreen

@Composable
fun JarScreen(locationId: Int, jarViewModel: JarViewModel = viewModel()) {
    val state by jarViewModel.uiState.collectAsState()
    val statisticsViewModel: StatisticsViewModel = viewModel()
    val statisticsState by statisticsViewModel.uiState.collectAsState()

    LaunchedEffect(locationId) {
        jarViewModel.readJarsByLocation(locationId)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Botes en la ubicación $locationId", style = MaterialTheme.typography.h4)

        state.jars?.let { jars ->
            jars.forEach { jar ->
                Text(text = "Descripción: ${jar.description}")
            }
        }

        var description by remember { mutableStateOf("") }

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Descripción") }
        )

        Button(onClick = {
            jarViewModel.createJar(locationId, description)
        }) {
            Text("Crear Bote")
        }

        Button(onClick = {
            statisticsViewModel.fetchStatistics(locationId, "one_week")
        }) {
            Text("Ver Estadísticas")
        }

        if (statisticsState.isLoading) {
            CircularProgressIndicator()
        }

        statisticsState.fillLevelTrend?.let { trend ->
            Text(text = "Tendencia de Nivel de Llenado")
            trend.forEach { data ->
                Text(text = "Fecha: ${data.Timestamp}, Nivel de Llenado: ${data.FillLevel}")
            }
        }

        statisticsState.fillFrequency?.let { frequency ->
            Text(text = "Frecuencia de Llenado")
            frequency.forEach { data ->
                Text(text = "Nivel de Llenado: ${data.FillLevel}, Frecuencia: ${data.Count}")
            }
        }

        statisticsState.error?.let {
            Text(text = it, color = MaterialTheme.colors.error)
        }
    }
}
