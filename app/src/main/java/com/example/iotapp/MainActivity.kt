package com.example.ioapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.example.iotapp.ui.screens.login.LoginScreen
import com.example.iotapp.ui.screens.locations.LocationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    // Puedes cambiar esto a LoginScreen() para probar la pantalla de inicio de sesión
                    LocationScreen()
                }
            }
        }
    }
}
