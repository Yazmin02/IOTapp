package com.example.iotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.iotapp.ui.screens.locations.LocationScreen
import com.example.iotapp.ui.screens.login.LoginScreen
import com.example.iotapp.ui.screens.register.RegisterScreen

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
    when (currentScreen) {
        "login" -> LoginScreen(
            onLoginSuccess = { currentScreen = "locations" },
            onNavigateToRegister = { currentScreen = "register" }
        )
        "register" -> RegisterScreen(onRegisterSuccess = { currentScreen = "login" })
        "locations" -> LocationScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
