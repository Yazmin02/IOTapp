package com.example.iotapp.ui.screens.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iotapp.data.models.Location

@Composable
fun LocationScreen(viewModel: LocationViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            LocationList(locations = state.locations)
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.error != null) {
            Text(text = state.error, color = MaterialTheme.colors.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        var name by remember { mutableStateOf("") }
        var details by remember { mutableStateOf("") }

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = details,
            onValueChange = { details = it },
            label = { Text("Details") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.createLocation(name, details) }) {
            Text("Add Location")
        }
    }
}

@Composable
fun LocationList(locations: List<Location>) {
    Column {
        locations.forEach { location ->
            Text(text = "ID: ${location.id} - Name: ${location.name} - Details: ${location.details}")
            Divider()
        }
    }
}
