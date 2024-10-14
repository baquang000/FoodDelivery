package com.example.fooddelivery.components.charts

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooddelivery.data.model.ChartCount
import com.example.fooddelivery.ui.theme.Purple200
import com.example.fooddelivery.ui.theme.Purple500
import com.example.fooddelivery.ui.theme.Purple700
import com.example.fooddelivery.ui.theme.Teal200

@Composable
fun PieChart(
    data: ChartCount,
    radiusOuter: Dp = 90.dp,
    chartBarWidth: Dp = 20.dp,
    animDuration: Int = 1000
) {
    val totalSum = data.series.sum()
    val floatValue = mutableListOf<Float>()

    data.series.forEachIndexed { index, values ->
        floatValue.add(index, 360 * values.toFloat() / totalSum.toFloat())
    }

    val colors = listOf(
        Purple200,
        Purple500,
        Teal200,
        Purple700,
        Blue
    )

    var animationPlayed by remember { mutableStateOf(false) }

    var lastValue = 0f
    val animateSize by animateFloatAsState(
        targetValue = if (animationPlayed) radiusOuter.value * 2f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )
    val animateRotation by animateFloatAsState(
        targetValue = if (animationPlayed) 90f * 11f else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Pie Chart using Canvas Arc
        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .offset { IntOffset.Zero }
                    .size(radiusOuter * 2f)
                    .rotate(animateRotation)
            ) {
                // draw each Arc for each data entry in Pie Chart
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index],
                        lastValue,
                        value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }

        // To see the data in more structured way
        // Compose Function in which Items are showing data
        DetailsPieChart(
            data = data,
            colors = colors
        )

    }
}

@Composable
fun DetailsPieChart(
    data: ChartCount,
    colors: List<Color>
) {
    Column(
        modifier = Modifier
            .padding(top = 80.dp)
            .fillMaxWidth()
    ) {
        // create the data items
        data.series.forEachIndexed { index, value ->
            DetailsPieChartItem(
                data = Pair(data.labels[index], value),
                color = colors[index]
            )
        }

    }
}

@Composable
fun DetailsPieChartItem(
    data: Pair<String, Int>,
    height: Dp = 24.dp,
    color: Color
) {

    Surface(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 40.dp),
        color = Color.Transparent
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = color,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(height)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.first,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = data.second.toString(),
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

        }

    }

}