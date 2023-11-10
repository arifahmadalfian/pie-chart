package com.ariefahmadalfian.pie_chart

import android.graphics.Paint
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ariefahmadalfian.pie_chart.model.PieChartData
import com.ariefahmadalfian.pie_chart.model.PieChartValue
import com.ariefahmadalfian.pie_chart.utils.formatNumber

@Composable
fun PieChart(
    modifier: Modifier,
    isLoading: Boolean,
    isEmpty: Boolean,
    inputs: List<PieChartData>? = null,
    colorBackground: Color = Color.White,
    titleCenterColor: Color = Color.Black,
    centerText: String = "",
    fontFamily: FontFamily = FontFamily.Default,
    onReload: () -> Unit
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var parentInnerRadius by remember {
        mutableStateOf(0f)
    }

    var values: MutableList<PieChartValue> = remember {
        mutableStateListOf()
    }

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            )
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier) {
            val width = size.width
            val height = size.height
            val innerRadius = width / 4f
            parentInnerRadius = innerRadius
            val radius = innerRadius * 2f
            val transparentWidth = innerRadius / 4f

            circleCenter = Offset(x = width / 2f, y = height / 2f)

            when {
                isLoading -> {
                    drawArc(
                        brush = brush,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = true,
                        size = Size(
                            width = radius * 2f,
                            height = radius * 2f
                        ),
                        topLeft = Offset(
                            (width - radius * 2f) / 2f,
                            (height - radius * 2f) / 2f
                        )
                    )
                }

                isEmpty -> {
                    drawArc(
                        color = Color.LightGray,
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = true,
                        size = Size(
                            width = radius * 2f,
                            height = radius * 2f
                        ),
                        topLeft = Offset(
                            (width - radius * 2f) / 2f,
                            (height - radius * 2f) / 2f
                        )
                    )
                }

                !inputs.isNullOrEmpty() -> {
                    val totalValue = inputs.sumOf {
                        it.value
                    }
                    val anglePerValue = 360f / totalValue
                    var currentStartAngle = 0.0

                    inputs.forEachIndexed { index, pieChartInput ->
                        val scale = 1.0f
                        val angleToDraw = pieChartInput.value * anglePerValue
                        scale(scale) {
                            drawArc(
                                color = pieChartInput.color,
                                startAngle = currentStartAngle.toFloat(),
                                sweepAngle = angleToDraw.toFloat(),
                                useCenter = true,
                                size = Size(
                                    width = radius * 2f,
                                    height = radius * 2f
                                ),
                                topLeft = Offset(
                                    (width - radius * 2f) / 2f,
                                    (height - radius * 2f) / 2f
                                )
                            )
                            currentStartAngle += angleToDraw
                        }
                        var rotateAngle = currentStartAngle - angleToDraw / 2f - 90f
                        var factor = 1f
                        if (rotateAngle > 90f) {
                            rotateAngle = (rotateAngle + 180).mod(360f)
                            factor = -0.92f
                        }

                        val percentage = (pieChartInput.value / totalValue * 100.0).formatNumber()

                        if (index == 0) {
                            values = mutableListOf()
                        }

                        values.add(
                            PieChartValue(
                                text = "$percentage %",
                                x = circleCenter.x,
                                y = circleCenter.y + (radius - (radius - innerRadius) / 2f) * factor,
                                rotate = rotateAngle.toFloat()
                            )
                        )

                    }
                }
            }

            values.forEach {
                drawContext.canvas.nativeCanvas.apply {
                    rotate(it.rotate) {
                        if (it.text != "0.0 %") {
                            drawText(
                                it.text,
                                it.x,
                                it.y,
                                Paint().apply {
                                    textSize = 16.sp.toPx()
                                    textAlign = Paint.Align.CENTER
                                    color = Color.White.toArgb()
                                }
                            )
                        }
                    }
                }
            }

            drawContext.canvas.nativeCanvas.apply {
                drawCircle(
                    circleCenter.x,
                    circleCenter.y,
                    innerRadius,
                    Paint().apply {
                        color = colorBackground.toArgb()
                        setShadowLayer(10f, 0f, 0f, Color.White.toArgb())
                    }
                )
            }

            drawCircle(
                color = colorBackground.copy(0.25f),
                radius = innerRadius + transparentWidth / 2f
            )
        }

        when {
            isEmpty -> {
                Column(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            onReload()
                        }
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.reload),
                        contentDescription = "Error",
                        tint = titleCenterColor,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = "Reload",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = fontFamily,
                        color = titleCenterColor
                    )
                }

            }

            !inputs.isNullOrEmpty() -> {
                Text(
                    text = centerText,
                    modifier = Modifier
                        .width(Dp(parentInnerRadius / 1.5f))
                        .padding(12.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    color = titleCenterColor
                )
            }
        }
    }
}