package com.example.iotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.iotapp.ui.screens.login.LoginScreen
import com.example.iotapp.ui.screens.locations.LocationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    val isAuthenticated = remember { mutableStateOf(false) }

    if (isAuthenticated.value) {
        LocationScreen()
    } else {
        LoginScreen(onLoginSuccess = { isAuthenticated.value = true })
    }
}
