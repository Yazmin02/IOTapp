package com.example.iotapp.ui.screens.statistics

import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.iotapp.data.models.FillLevelTrend
import com.example.iotapp.ui.theme.LightBlue
import com.example.iotapp.ui.theme.White
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import androidx.compose.ui.graphics.toArgb
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun StatisticScreen(
    jarId: Int,
    viewModel: StatisticsViewModel = viewModel()
) {
    val state by viewModel.state.observeAsState(StatisticState())

    LaunchedEffect(jarId) {
        viewModel.loadFillLevelTrend(jarId, "one_week")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.error != null) {
            Text("Error: ${state.error}", modifier = Modifier.align(Alignment.Center))
        } else {
            LineChartView(state.fillLevelTrend)
        }
    }
}

@Composable
fun LineChartView(data: List<FillLevelTrend>) {
    val context = LocalContext.current

    AndroidView(factory = { context ->
        LineChart(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val entries = data.map {
                val date = dateFormat.parse(it.timestamp)
                Entry(date.time.toFloat(), it.fillLevel)
            }
            val dataSet = LineDataSet(entries, "Fill Level Trend").apply {
                color = LightBlue.toArgb()
                valueTextColor = White.toArgb()
            }

            this.data = LineData(dataSet)

            // Formato de los ejes
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = object : ValueFormatter() {
                    private val dateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())
                    override fun getFormattedValue(value: Float): String {
                        return dateFormat.format(Date(value.toLong()))
                    }
                }
            }

            axisLeft.apply {
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return "$value cm"
                    }
                }
            }

            axisRight.isEnabled = false

            invalidate()
        }
    }, modifier = Modifier.fillMaxSize())
}
