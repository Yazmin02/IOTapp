package com.example.iotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.iotapp.ui.screens.locations.LocationScreen
import com.example.iotapp.ui.screens.login.LoginScreen
import com.example.iotapp.ui.screens.register.RegisterScreen
import com.example.iotapp.ui.screens.jars.JarScreen
import com.example.iotapp.ui.screens.statistics.StatisticScreen
import com.example.iotapp.ui.screens.statistics.StatisticsViewModel
import com.example.iotapp.data.repository.StatisticsRepository
import com.example.iotapp.data.network.ApiClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    var currentScreen by remember { mutableStateOf("login") }
    var selectedLocationId by remember { mutableStateOf<Int?>(null) }
    var selectedJarId by remember { mutableStateOf<Int?>(null) }
    val statisticsRepository = StatisticsRepository(ApiClient.apiService)
    val statisticsViewModel = StatisticsViewModel(statisticsRepository)

    when (currentScreen) {
        "login" -> LoginScreen(
            onLoginSuccess = { currentScreen = "locations" },
            onNavigateToRegister = { currentScreen = "register" }
        )
        "register" -> RegisterScreen(
            onRegisterSuccess = { currentScreen = "login" }
        )
        "locations" -> LocationScreen(
            onLocationSelected = { locationId ->
                selectedLocationId = locationId
                currentScreen = "jars"
            }
        )
        "jars" -> JarScreen(
            locationId = selectedLocationId!!,
            onNavigateBack = { currentScreen = "locations" },
            onNavigateToStatistics = { jarId ->
                selectedJarId = jarId
                currentScreen = "statistics"
            }
        )
        "statistics" -> selectedJarId?.let { jarId ->
            StatisticScreen(jarId = jarId, viewModel = statisticsViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
