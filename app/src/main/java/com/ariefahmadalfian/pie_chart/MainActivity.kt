package com.ariefahmadalfian.pie_chart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ariefahmadalfian.pie_chart.model.PieChartData
import com.ariefahmadalfian.pie_chart.ui.theme.PiechartTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PiechartTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PieChartScreenScreen()
                }
            }
        }
    }
}

@Composable
fun PieChartScreenScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PieChart(
            modifier = Modifier
                .size(250.dp),
            isLoading = false,
            isEmpty = false,
            input = listOf(
                PieChartData(
                    color = Color.Blue,
                    value = 29.0,
                    description = "Python"
                ),
                PieChartData(
                    color = Color.DarkGray,
                    value = 21.0,
                    description = "Swift"
                ),
                PieChartData(
                    color = Color.Red,
                    value = 32.0,
                    description = "JavaScript"
                ),
                PieChartData(
                    color = Color.Yellow,
                    value = 18.0,
                    description = "Java"
                ),
                PieChartData(
                    color = Color.Magenta,
                    value = 12.0,
                    description = "Ruby"
                ),
                PieChartData(
                    color = Color.Cyan,
                    value = 38.0,
                    description = "Kotlin"
                ),
            ),
            centerText = "150 persons were asked"
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PieChartScreenScreenPreview() {
    PieChartScreenScreen()
}