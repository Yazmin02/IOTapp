package com.example.iotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.iotapp.ui.screens.login.LoginScreen

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
    LoginScreen(onLoginSuccess = {
        // Navegación a la pantalla de locaciones
    })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
