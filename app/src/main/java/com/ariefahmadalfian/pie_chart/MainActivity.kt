package com.ariefahmadalfian.pie_chart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ariefahmadalfian.pie_chart.model.PieChartData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PieChartScreenScreen()
        }
    }
}

@Composable
fun PieChartScreenScreen() {
    var isLoading1 by remember { mutableStateOf(true) }
    var isLoading2 by remember { mutableStateOf(true) }
    var isLoading3 by remember { mutableStateOf(true) }

    var isEmpty1 by remember { mutableStateOf(false) }
    var isEmpty2 by remember { mutableStateOf(false) }
    var isEmpty3 by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val datas by remember {
        mutableStateOf(
            mutableListOf(
                PieChartData(
                    color = Color.Blue,
                    value = 29.0,
                    description = "Blue"
                ),
                PieChartData(
                    color = Color.DarkGray,
                    value = 21.0,
                    description = "DarkGray"
                ),
                PieChartData(
                    color = Color.Red,
                    value = 32.0,
                    description = "Red"
                ),
                PieChartData(
                    color = Color.Magenta,
                    value = 12.0,
                    description = "Magenta"
                ),
                PieChartData(
                    color = Color.Cyan,
                    value = 38.0,
                    description = "Cyan"
                ),
            )
        )
    }

    var datas1: MutableList<PieChartData>? by remember {
        mutableStateOf(null)
    }

    var datas2: MutableList<PieChartData>? by remember {
        mutableStateOf(null)
    }

    var datas3: MutableList<PieChartData>? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = Unit) {
        delay(3000)
        datas1 = datas

        isLoading1 = false
        isLoading2 = false
        isLoading3 = false
        isEmpty1 = false
        isEmpty2 = true
        isEmpty3 = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PieChart(
            modifier = Modifier
                .size(250.dp),
            isLoading = isLoading1,
            isEmpty = isEmpty1,
            inputs = datas1,
            centerText = "Title Center",
            onReload = {

            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PieChart(
            modifier = Modifier
                .size(250.dp),
            isLoading = isLoading2,
            isEmpty = isEmpty2,
            inputs = datas2,
            centerText = "Value 10.000.000" ,
            onReload = {
                isLoading2 = true
                isEmpty2 = false
                scope.launch {
                    delay(3000)
                    isLoading2 = false
                    datas2 = datas
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PieChart(
            modifier = Modifier
                .size(250.dp),
            isLoading = isLoading3,
            isEmpty = isEmpty3,
            inputs = datas3,
            centerText = "Title Center",
            onReload = {
                isLoading3 = true
                isEmpty3 = false
                scope.launch {
                    delay(3000)
                    isLoading3 = false
                    isEmpty3 = true
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PieChartScreenScreenPreview() {
    PieChartScreenScreen()
}