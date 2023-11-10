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
    var isLoading by remember {
        mutableStateOf(true)
    }

    var isEmpty by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    val datas1 = remember {
        mutableStateListOf(
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
    }

    var datas2: SnapshotStateList<PieChartData>? = remember {
        mutableStateListOf(
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
    }

    LaunchedEffect(key1 = Unit) {
        delay(3000)
        isLoading = false
        isEmpty = true
        datas2 = null
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
            isLoading = isLoading,
            isEmpty = false,
            inputs = datas1,
            centerText = "Title Center",
            onReload = {

            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PieChart(
            modifier = Modifier
                .size(250.dp),
            isLoading = false,
            isEmpty = isEmpty,
            inputs = datas2,
            centerText = "Title Center" ,
            onReload = {
                isLoading = true
                isEmpty = false
                scope.launch {
                    delay(3000)
                    isLoading = false
                    datas2 = datas1
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PieChart(
            modifier = Modifier
                .size(250.dp),
            isLoading = isLoading,
            isEmpty = isEmpty,
            inputs = datas2,
            centerText = "Title Center",
            onReload = {
                isLoading = true
                isEmpty = false
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PieChartScreenScreenPreview() {
    PieChartScreenScreen()
}